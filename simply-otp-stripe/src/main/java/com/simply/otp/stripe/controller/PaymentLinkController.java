package com.simply.otp.stripe.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentLink;
import com.stripe.param.PaymentLinkCreateParams;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/payment")
@Slf4j
public class PaymentLinkController {

	String priceId;

	public PaymentLinkController() {
		Stripe.apiKey = "sk_test_51Ms2pLEiFP9ITGKsJ1pIvz6xTp1IGMK5ovFHflpR1NfxypTyx3T7K8XmO4eJG2Cv7L3l6HckoEGF1LlkHBecZ5eF00WHQHI3uN";
		priceId = "price_1Mt90mEiFP9ITGKsjnSlD4Zt";
	}

	@GetMapping("/generate")
	public RedirectView generateLink() {
		// Set your secret key. Remember to switch to your live secret key in
		// production.
		// See your keys here: https://dashboard.stripe.com/apikeys

		PaymentLinkCreateParams params = PaymentLinkCreateParams.builder()
				.addLineItem(PaymentLinkCreateParams.LineItem.builder().setPrice(priceId).setQuantity(1L).build())
				.build();

		try {
			PaymentLink paymentLink = PaymentLink.create(params);
			log.info("Payment Link :" + paymentLink);
			return new RedirectView(paymentLink.getUrl());
		} catch (StripeException e) {
			log.error("Exception occured in PaymentLinkController : generateLink() with MSG : "
					+ e.getLocalizedMessage());
		}
		return null;
	}

	@GetMapping("/track")
	public String getPaymentStatus() {

		PaymentLinkCreateParams params = PaymentLinkCreateParams.builder()
				.addLineItem(PaymentLinkCreateParams.LineItem.builder().setPrice(priceId).setQuantity(1L).build())
				.setAfterCompletion(PaymentLinkCreateParams.AfterCompletion.builder()
						.setType(PaymentLinkCreateParams.AfterCompletion.Type.REDIRECT)
						.setRedirect(PaymentLinkCreateParams.AfterCompletion.Redirect.builder()
								.setUrl("https://example.com").build())
						.build())
				.build();

		try {
			PaymentLink paymentLink = PaymentLink.create(params);
			log.info("Payment Link :" + paymentLink);
		} catch (StripeException e) {
			log.error("Exception occured in PaymentLinkController : getPaymentStatus() with MSG : "
					+ e.getLocalizedMessage());
		}
		return null;
	}

}
