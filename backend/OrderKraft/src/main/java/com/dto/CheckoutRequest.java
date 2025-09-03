package com.dto;

public class CheckoutRequest {
	private long amountInCents;
	private String currency;
	private String connectedAccountId;
	private String successUrl;
	private String cancelUrl;

	public CheckoutRequest(long amountInCents, String currency, String connectedAccountId, String successUrl,
			String cancelUrl) {
		super();
		this.amountInCents = amountInCents;
		this.currency = currency;
		this.connectedAccountId = connectedAccountId;
		this.successUrl = successUrl;
		this.cancelUrl = cancelUrl;
	}

	// getters & setters
	public long getAmountInCents() {
		return amountInCents;
	}

	public void setAmountInCents(long amountInCents) {
		this.amountInCents = amountInCents;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getConnectedAccountId() {
		return connectedAccountId;
	}

	public void setConnectedAccountId(String connectedAccountId) {
		this.connectedAccountId = connectedAccountId;
	}

	public String getSuccessUrl() {
		return successUrl;
	}

	public void setSuccessUrl(String successUrl) {
		this.successUrl = successUrl;
	}

	public String getCancelUrl() {
		return cancelUrl;
	}

	public void setCancelUrl(String cancelUrl) {
		this.cancelUrl = cancelUrl;
	}
}
