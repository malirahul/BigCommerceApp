package com.simply.otp.stripe.dto;

import java.util.List;
import java.util.Map;

import lombok.Data;

@Data
public class WebhookObjectData {

	private String id;
	private String object;
	private String after_expiration;
	private Boolean allow_promotion_codes;
	private Long amount_subtotal;
	private Long amount_total;
	private Object automatic_tax;
	private String billing_address_collection;
	private String cancel_url;
	private String client_reference_id;
	private String consent;
	private String consent_collection;
	private Long created;
	private String currency;
	private Object currency_conversion;
	private List<Object> custom_fields;
	private Object custom_text;
	private String customer;
	private String customer_creation;
	private Object customer_details;
	private String customer_email;
	private Long expires_at;
	private String invoice;
	private Object invoice_creation;
	private Boolean livemode;
	private String locale;
	private Map<String, Object> metadata;
	private String mode;
	private Object payment_intent;
	private String payment_link;
	private String payment_method_collection;
	private Object payment_method_options;
	private List<String> payment_method_types;
	private String payment_status;
	private Object phone_number_collection;
	private String recovered_from;
	private Object setup_intent;
	private Object shipping_address_collection;
	private Object shipping_cost;
	private Object shipping_details;
	private List<Object> shipping_options;
	private String status;
	private String submit_type;
	private String subscription;
	private String success_url;
	private Object total_details;
	private Object url;

}
