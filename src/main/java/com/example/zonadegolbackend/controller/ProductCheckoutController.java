package com.example.zonadegolbackend.controller;


import com.example.zonadegolbackend.dtos.StripeResponse;
import com.example.zonadegolbackend.services.StripeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/stripe")
public class ProductCheckoutController  {

    private  StripeService stripeService;

    public ProductCheckoutController(StripeService stripeService) {
        this.stripeService = stripeService;
    }

    @PostMapping("/checkout")
    public ResponseEntity<StripeResponse> checkoutProducts() {
        StripeResponse stripeResponse = stripeService.checkoutProducts();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(stripeResponse);
    }
}
