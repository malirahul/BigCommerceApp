package com.simply.otp.stripe.dto;

import lombok.Data;

@Data
public class WebhookDto {

	private String id;
	private String object;
	private String api_version;
	private String created;
	private String livemode;
	private String pending_webhooks;
	private Object request;
	private String type;
	WebhookObjectDto data;
}
