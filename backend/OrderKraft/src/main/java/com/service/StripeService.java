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

	// stripe -API
	// -> productName , amount , quantity , currency
	// -> return sessionId and url

	public StripeResponse checkoutProducts(ProductRequest productRequest) {
		Stripe.apiKey = secretKey;

		productRequest.setQuantity(1L);
		System.out.println("test print of product request" + productRequest.getName() + " " + productRequest.getAmount()
				+ " " + productRequest.getQuantity());
		SessionCreateParams.LineItem.PriceData.ProductData productData = SessionCreateParams.LineItem.PriceData.ProductData
				.builder().setName(productRequest.getName()).build();

		SessionCreateParams.LineItem.PriceData priceData = SessionCreateParams.LineItem.PriceData.builder()
				.setCurrency(productRequest.getCurrency() != null ? productRequest.getCurrency() : "usd")
				.setUnitAmount(productRequest.getAmount()) // must be in cents
				.setProductData(productData).build();

		SessionCreateParams.LineItem lineItem = SessionCreateParams.LineItem.builder()
				.setQuantity(productRequest.getQuantity()).setPriceData(priceData).build();

		SessionCreateParams params = SessionCreateParams.builder().setMode(SessionCreateParams.Mode.PAYMENT)
				.setSuccessUrl("http://localhost:8081/success").setCancelUrl("http://localhost:8081/cancel")
				.addLineItem(lineItem).build();

		try {
			Session session = Session.create(params);
			return StripeResponse.builder().status("SUCCESS").message("Payment session created")
					.sessionId(session.getId()).sessionUrl(session.getUrl()).build();
		} catch (StripeException e) {
			return StripeResponse.builder().status("FAILED").message("Error creating Stripe session: " + e.getMessage())
					.build();
		}
	}

	// ✅ New: Check payment status by session_id
	public Map<String, Object> checkPaymentStatus(String sessionId) {
		Stripe.apiKey = secretKey;
		Map<String, Object> response = new HashMap<>();

		try {
			// 1. Retrieve session
			Session session = Session.retrieve(sessionId);

			// 2. Retrieve payment intent
			String paymentIntentId = session.getPaymentIntent();
			PaymentIntent paymentIntent = PaymentIntent.retrieve(paymentIntentId);
			System.out.println(paymentIntent);
			// 3. Fill response
			response.put("sessionId", sessionId);
			response.put("paymentIntentId", paymentIntentId);
			response.put("amount", paymentIntent.getAmount());
			response.put("currency", paymentIntent.getCurrency());
			response.put("status", paymentIntent.getStatus());

		} catch (Exception e) {
			response.put("error", "Error verifying payment: " + e.getMessage());
		}

		return response;
	}

	// Refund using session id
	public Refund refundPaymentBySession(String sessionId) throws Exception {
		// Retrieve session object
		Stripe.apiKey = secretKey;
		Session session = Session.retrieve(sessionId);

		// Get PaymentIntend ID from session
		String paymentIntetnId = session.getPaymentIntent();

		// check if exist
		if (paymentIntetnId == null) {
			throw new IllegalArgumentException("No payment found  for this order");
		}

		// create refund
		RefundCreateParams params = RefundCreateParams.builder().setPaymentIntent(paymentIntetnId).build();

		return Refund.create(params);
	}

	// function to get PI for new payment to connected account on Stripe
	public PaymentIntent createPaymentToConnectedAccount(long amountInCents, String currency, String connectedAccountId)
			throws StripeException {
		Stripe.apiKey = secretKey;

		PaymentIntentCreateParams params = PaymentIntentCreateParams.builder().setAmount(amountInCents)
				.setCurrency(currency).addPaymentMethodType("card") // for test payments
				.setTransferData(
						PaymentIntentCreateParams.TransferData.builder().setDestination(connectedAccountId).build())
				.build();

		return PaymentIntent.create(params);
	}
	
	
	
	//this function returns session url to complete the payment just created

	public Map<String, String> createCheckoutSession(long amountInCents, String currency, String connectedAccountId,
			String successUrl, String cancelUrl) throws StripeException {

// First create PI
		PaymentIntent pi = createPaymentToConnectedAccount(amountInCents, currency, connectedAccountId);

// Create Checkout Session using PI details
		SessionCreateParams params = SessionCreateParams.builder().setMode(SessionCreateParams.Mode.PAYMENT)
				.setSuccessUrl(successUrl + "?session_id={CHECKOUT_SESSION_ID}").setCancelUrl(cancelUrl)
				.addLineItem(
						SessionCreateParams.LineItem.builder().setQuantity(1L)
								.setPriceData(SessionCreateParams.LineItem.PriceData.builder().setCurrency(currency)
										.setUnitAmount(amountInCents)
										.setProductData(SessionCreateParams.LineItem.PriceData.ProductData.builder()
												.setName("Order Payment").build())
										.build())
								.build())
				.setPaymentIntentData(SessionCreateParams.PaymentIntentData.builder()
						.setTransferData(SessionCreateParams.PaymentIntentData.TransferData.builder()
								.setDestination(connectedAccountId).build())
						.build())
				.build();

		Session session = Session.create(params);
		Map<String, String> result = new HashMap<>();
		result.put("sessionUrl", session.getUrl());
		result.put("sessionId", session.getId());
		return result; // return hosted checkout page URL
	}
}
