package com.simply.otp.stripe.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.simply.otp.stripe.dto.WebhookDto;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.exception.StripeException;
import com.stripe.model.Event;
import com.stripe.model.EventDataObjectDeserializer;
import com.stripe.model.PaymentLink;
import com.stripe.model.StripeObject;
import com.stripe.net.Webhook;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class WebhookController<T> {

	private Gson gson = new GsonBuilder().create();

	@Value("${stripe.webhook.secret}")
	String webhookSecret;

	@PostMapping("/webhook")
	public ResponseEntity<Object> getWebHook(@RequestBody String payload, @RequestHeader Map<String, Object> header) {

		Event event = null;
		String paymentLinkId = null;

		try {
			WebhookDto webhookPayload = gson.fromJson(payload, WebhookDto.class);
//			log.info("Headers: " + header);
//			log.info("Stripe-signature: " + header.get("stripe-signature"));
			String sigHeader = header.get("stripe-signature").toString();
			paymentLinkId = webhookPayload.getData().getObject().getPayment_link();
			log.info("payload" + payload);
			log.info("WebhookSecret: "+ webhookSecret);
//			log.info("Payment Link Id {}", paymentLinkId);
			event = Webhook.constructEvent(payload, sigHeader, webhookSecret);

			log.info(sigHeader);
		} catch (SignatureVerificationException e) {
			// Invalid signature
			log.info("Exception in Signature verification : " + e.getLocalizedMessage());
			return ResponseEntity.badRequest().build();
		} catch (Exception e) {
			log.info("Exception in WebhookController: " + e.getLocalizedMessage());
			return ResponseEntity.badRequest().build();
		} // end of try-catch

		// Deserialize the nested object inside the event
		EventDataObjectDeserializer dataObjectDeserializer = event.getDataObjectDeserializer();
		StripeObject stripeObject = null;
		try {
		if (dataObjectDeserializer.getObject().isPresent()) {
			stripeObject = dataObjectDeserializer.getObject().get();
		} else {
			// Deserialization failed, probably due to an API version mismatch.
			// Refer to the Javadoc documentation on `EventDataObjectDeserializer` for
			// instructions on how to handle this case, or return an error here.
		}
		}catch(NoSuchElementException e) {
			e.printStackTrace();
		}

		// Handle the event
		switch (event.getType()) {

		case "checkout.session.async_payment_failed": {
			// Then define and call a function to handle the event
			// checkout.session.async_payment_failed
			break;
		}
		case "checkout.session.async_payment_succeeded": {
			// Then define and call a function to handle the event
			// checkout.session.async_payment_succeeded
			break;
		}
		case "checkout.session.completed": {
			// Deactivating Payment Link
			log.info("Deactivating Payment Link");
			try {
				PaymentLink paymentLink = PaymentLink.retrieve(paymentLinkId);
				Map<String, Object> params = new HashMap<>();
				params.put("active", false);
				paymentLink = paymentLink.update(params);

			} catch (StripeException e) {
				log.info("Exception in WebhookController: " + e.getLocalizedMessage());
			}

		}
		case "checkout.session.expired": {
			// Then define and call a function to handle the event checkout.session.expired
			break;
		}
		// ... handle other event types
		default:
			log.info("Unhandled event type: " + event.getType());
		}

		return ResponseEntity.ok().build();
	}// end of method

}// end of class
