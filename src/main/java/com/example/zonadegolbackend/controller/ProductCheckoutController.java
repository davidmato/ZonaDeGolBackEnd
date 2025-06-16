package com.example.zonadegolbackend.controller;


import com.example.zonadegolbackend.dtos.StripeResponse;
import com.example.zonadegolbackend.entity.Usuario;
import com.example.zonadegolbackend.repository.UsuarioRepository;
import com.example.zonadegolbackend.services.StripeService;
import com.stripe.model.Event;
import com.stripe.net.Webhook;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.stripe.model.checkout.Session;
import com.fasterxml.jackson.databind.*;
import java.io.IOException;

@RestController
@RequestMapping("/api/stripe")
public class ProductCheckoutController  {

    private final StripeService stripeService;

    private final UsuarioRepository usuarioRepository;

    @Value("${stripe.webhookSecret}")
    private String endpointSecret;


    public ProductCheckoutController(StripeService stripeService, UsuarioRepository usuarioRepository) {
        this.stripeService = stripeService;
        this.usuarioRepository = usuarioRepository;
    }

    @PostMapping("/checkout")
    public ResponseEntity<StripeResponse> checkoutProducts() {
        StripeResponse stripeResponse = stripeService.checkoutProducts();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(stripeResponse);
    }

    @PostMapping("/webhook")
    public ResponseEntity<String> stripeWebhook(HttpServletRequest request) throws IOException {
        System.out.println("Webhook recibido");
        byte[] payloadBytes = request.getInputStream().readAllBytes();
        String payload = new String(payloadBytes, java.nio.charset.StandardCharsets.UTF_8);
        String sigHeader = request.getHeader("Stripe-Signature");
        System.out.println("Payload recibido: " + payload);

        try {
            Event event = Webhook.constructEvent(payload, sigHeader, endpointSecret);
            System.out.println("Evento recibido: " + event.getType());

            if ("checkout.session.completed".equals(event.getType())) {
                var deserializer = event.getDataObjectDeserializer();
                String email = null;
                if (deserializer.getObject().isPresent()) {
                    Session session = (Session) deserializer.getObject().get();
                    email = session.getCustomerEmail();
                    System.out.println("Email recibido de Stripe: " + email);
                } else {
                    // Extraer email manualmente del payload
                    ObjectMapper mapper = new ObjectMapper();
                    JsonNode root = mapper.readTree(payload);
                    email = root.at("/data/object/customer_details/email").asText();
                    System.out.println("Email recibido manualmente: " + email);
                }
                if (email != null && !email.isEmpty()) {
                    Usuario usuario = usuarioRepository.findByCorreo(email).orElse(null);
                    if (usuario != null) {
                        usuario.setPagado(true);
                        usuarioRepository.save(usuario);
                        System.out.println("Usuario actualizado como pagado.");
                    } else {
                        System.out.println("Usuario no encontrado para el email: " + email);
                    }
                }
            } else {
                System.out.println("Tipo de evento no es checkout.session.completed: " + event.getType());
            }
            return ResponseEntity.ok("");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("");
        }
    }
}

// idAcount: acct_1ROjtvCylLezt502