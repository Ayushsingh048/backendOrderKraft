package com.service;

import com.dto.ProductRequest;
import com.dto.StripeResponse;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.model.Refund;
import com.stripe.model.checkout.Session;
import com.stripe.param.PaymentIntentCreateParams;
import com.stripe.param.RefundCreateParams;
import com.stripe.param.checkout.SessionCreateParams;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class StripeService {

	@Value("${stripe.secretKey}")
	private String secretKey;

	/**
	 * Creates a Stripe Checkout session for the given product request.
	 * Sets up the product details, price, and quantity, then creates a payment session.
	 * 
	 * @param productRequest The product details including name, amount, currency, and quantity.
	 * @return StripeResponse containing session ID and URL if successful, or error message if failed.
	 */
	public StripeResponse checkoutProducts(ProductRequest productRequest) {
		Stripe.apiKey = secretKey;

		// Set quantity to 1 by default
		productRequest.setQuantity(1L);
		System.out.println("test print of product request" + productRequest.getName() + " " + productRequest.getAmount()
				+ " " + productRequest.getQuantity());

		// Build product data for Stripe session
		SessionCreateParams.LineItem.PriceData.ProductData productData = SessionCreateParams.LineItem.PriceData.ProductData
				.builder().setName(productRequest.getName()).build();

		// Build price data including currency and amount (in cents)
		SessionCreateParams.LineItem.PriceData priceData = SessionCreateParams.LineItem.PriceData.builder()
				.setCurrency(productRequest.getCurrency() != null ? productRequest.getCurrency() : "usd")
				.setUnitAmount(productRequest.getAmount()) // amount must be in cents
				.setProductData(productData).build();

		// Create line item with quantity and price data
		SessionCreateParams.LineItem lineItem = SessionCreateParams.LineItem.builder()
				.setQuantity(productRequest.getQuantity()).setPriceData(priceData).build();

		// Build session parameters with success and cancel URLs
		SessionCreateParams params = SessionCreateParams.builder().setMode(SessionCreateParams.Mode.PAYMENT)
				.setSuccessUrl("http://localhost:8081/success").setCancelUrl("http://localhost:8081/cancel")
				.addLineItem(lineItem).build();

		try {
			// Create Stripe checkout session
			Session session = Session.create(params);
			// Return success response with session details
			return StripeResponse.builder().status("SUCCESS").message("Payment session created")
					.sessionId(session.getId()).sessionUrl(session.getUrl()).build();
		} catch (StripeException e) {
			// Return failure response with error message
			return StripeResponse.builder().status("FAILED").message("Error creating Stripe session: " + e.getMessage())
					.build();
		}
	}

	/**
	 * Checks the payment status of a Stripe session by session ID.
	 * Retrieves the session and associated payment intent to get payment details.
	 * 
	 * @param sessionId The Stripe session ID to check.
	 * @return A map containing session ID, payment intent ID, amount, currency, status, or error message.
	 */
	public Map<String, Object> checkPaymentStatus(String sessionId) {
		Stripe.apiKey = secretKey;
		Map<String, Object> response = new HashMap<>();

		try {
			// Retrieve the Stripe session by ID
			Session session = Session.retrieve(sessionId);

			// Retrieve the payment intent associated with the session
			String paymentIntentId = session.getPaymentIntent();
			PaymentIntent paymentIntent = PaymentIntent.retrieve(paymentIntentId);
			System.out.println(paymentIntent);

			// Populate response with payment details
			response.put("sessionId", sessionId);
			response.put("paymentIntentId", paymentIntentId);
			response.put("amount", paymentIntent.getAmount());
			response.put("currency", paymentIntent.getCurrency());
			response.put("status", paymentIntent.getStatus());

		} catch (Exception e) {
			// Handle errors and add error message to response
			response.put("error", "Error verifying payment: " + e.getMessage());
		}

		return response;
	}

	/**
	 * Refunds a payment using the Stripe session ID.
	 * Retrieves the session and payment intent, then creates a refund.
	 * 
	 * @param sessionId The Stripe session ID for the payment to refund.
	 * @return The created Refund object.
	 * @throws Exception if no payment intent is found or refund creation fails.
	 */
	public Refund refundPaymentBySession(String sessionId) throws Exception {
		// Set Stripe API key
		Stripe.apiKey = secretKey;

		// Retrieve the session object from Stripe
		Session session = Session.retrieve(sessionId);

		// Get the PaymentIntent ID from the session
		String paymentIntetnId = session.getPaymentIntent();

		// Check if payment intent exists
		if (paymentIntetnId == null) {
			throw new IllegalArgumentException("No payment found  for this order");
		}

		// Create refund parameters with the payment intent ID
		RefundCreateParams params = RefundCreateParams.builder().setPaymentIntent(paymentIntetnId).build();

		// Create and return the refund
		return Refund.create(params);
	}

	/**
	 * Creates a PaymentIntent for a new payment to a connected Stripe account.
	 * Sets amount, currency, payment method, and transfer destination.
	 * 
	 * @param amountInCents The payment amount in cents.
	 * @param currency The currency code (e.g., "usd").
	 * @param connectedAccountId The Stripe connected account ID to receive the payment.
	 * @return The created PaymentIntent object.
	 * @throws StripeException if creation fails.
	 */
	public PaymentIntent createPaymentToConnectedAccount(long amountInCents, String currency, String connectedAccountId)
			throws StripeException {
		// Set Stripe API key
		Stripe.apiKey = secretKey;

		// Build PaymentIntent creation parameters
		PaymentIntentCreateParams params = PaymentIntentCreateParams.builder().setAmount(amountInCents)
				.setCurrency(currency).addPaymentMethodType("card") // for test payments
				.setTransferData(
						PaymentIntentCreateParams.TransferData.builder().setDestination(connectedAccountId).build())
				.build();

		// Create and return the PaymentIntent
		return PaymentIntent.create(params);
	}

	/**Currently using this for payment 
	 * Creates a Stripe Checkout session URL to complete a payment just created.
	 * First creates a PaymentIntent, then creates a checkout session using that PaymentIntent.
	 * 
	 * @param amountInCents The payment amount in cents.
	 * @param currency The currency code (e.g., "usd").
	 * @param connectedAccountId The Stripe connected account ID to receive the payment.
	 * @param successUrl The URL to redirect to on successful payment.
	 * @param cancelUrl The URL to redirect to if payment is cancelled.
	 * @param orderName The name of the order/product.
	 * @return A map containing the session URL and session ID for the hosted checkout page.
	 * @throws StripeException if session creation fails.
	 */
	public Map<String, String> createCheckoutSession(long amountInCents, String currency, String connectedAccountId,
			String successUrl, String cancelUrl, String orderName) throws StripeException {

		// First create a PaymentIntent for the connected account
		PaymentIntent pi = createPaymentToConnectedAccount(amountInCents, currency, connectedAccountId);

		// Build checkout session parameters using the PaymentIntent details
		SessionCreateParams params = SessionCreateParams.builder().setMode(SessionCreateParams.Mode.PAYMENT)
				.setSuccessUrl("http://localhost:4200/procurement-officer").setCancelUrl(cancelUrl)
				.addLineItem(
						SessionCreateParams.LineItem.builder().setQuantity(1L)
								.setPriceData(SessionCreateParams.LineItem.PriceData.builder().setCurrency(currency)
										.setUnitAmount(amountInCents)
										.setProductData(SessionCreateParams.LineItem.PriceData.ProductData.builder()
												.setName(orderName).build())
										.build())
								.build())
				.setPaymentIntentData(SessionCreateParams.PaymentIntentData.builder()
						.setTransferData(SessionCreateParams.PaymentIntentData.TransferData.builder()
								.setDestination(connectedAccountId).build())
						.build())
				.build();

		// Create the Stripe checkout session
		Session session = Session.create(params);

		// Prepare result map with session URL and ID
		Map<String, String> result = new HashMap<>();
		result.put("sessionUrl", session.getUrl());
		result.put("sessionId", session.getId());

		// Return the hosted checkout page URL and session ID
		return result;
	}
	
	public String gatewayStatus() {
		Stripe.getConnectTimeout();
		return "s";
	}
}
