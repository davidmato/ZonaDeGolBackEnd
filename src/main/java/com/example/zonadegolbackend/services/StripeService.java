package com.example.zonadegolbackend.services;

import com.example.zonadegolbackend.dtos.StripeResponse;
import com.example.zonadegolbackend.entity.Usuario;
import com.example.zonadegolbackend.repository.UsuarioRepository;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class StripeService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${stripe.secretKey}")
    private String secretKey;

    //stripe -API
    //-> productName , amount , quantity , currency
    //-> return sessionId and url

    private final UsuarioRepository usuarioRepository;

    public StripeService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public StripeResponse checkoutProducts() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !(authentication.getPrincipal() instanceof Usuario)) {
            throw new RuntimeException("El usuario no está autenticado.");
        }

        Usuario usuario = (Usuario) authentication.getPrincipal();
        Integer usuarioId = usuario.getId();


        // Verificar si el usuario ya ha pagado
        Usuario usuarioDB = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        if (usuarioDB.isPagado()) {
            throw new RuntimeException("Ya has realizado el pago. No puedes volver a pagar.");
        }


        // Clave secreta de la API de Stripe
        Stripe.apiKey = secretKey;

        // Crea un PaymentIntent con el monto del pedido y la moneda
        SessionCreateParams.LineItem.PriceData.ProductData productData =
                SessionCreateParams.LineItem.PriceData.ProductData.builder()
                        .setName("Suscripcion para el acceso a la liga")
                        .build();

        // Crear la linea del objeto con su detalles y precio por producto
        SessionCreateParams.LineItem.PriceData priceData =
                SessionCreateParams.LineItem.PriceData.builder()
                        .setCurrency("EUR")
                        .setUnitAmount(5000L)
                        .setProductData(productData)
                        .build();

        // Crear la linea del objeto con el precio y la cantidad
        SessionCreateParams.LineItem lineItem =
                SessionCreateParams
                        .LineItem.builder()
                        .setQuantity(1L)
                        .setPriceData(priceData)
                        .build();

        // Crear las redirecciones de éxito y cancelación
        SessionCreateParams params =
                SessionCreateParams.builder()
                        .setMode(SessionCreateParams.Mode.PAYMENT)
                        .setSuccessUrl("http://localhost:4200/success")
                        .setCancelUrl("http://localhost:4200/cancel")
                        .addLineItem(lineItem)
                        .build();

        // Crear la sesión de Stripe
        Session session = null;
        try {
            session = Session.create(params);

            usuarioRepository.findById(usuarioId).ifPresent(u -> {
                u.setPagado(true);
                usuarioRepository.save(u);
            });

            // Enviar correo al usuario
            try{
                MimeMessage mimeMessage = mailSender.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
                helper.setTo(usuario.getCorreo());
                helper.setSubject("¡Subscripción realizada con éxito!");

                String html = "<div style=\"max-width:420px;margin:40px auto;padding:28px;background:#f4f8fb;border-radius:12px;box-shadow:0 2px 12px rgba(52,67,83,0.10);font-family:Arial,sans-serif;\">" +
                        "<div style='text-align:center; margin-bottom:20px;'>" +
                        "<img src='https://res.cloudinary.com/dyfoaulb5/image/upload/fl_preserve_transparency/v1747739581/logo_ohmfq7.jpg' alt='Logo' style='max-width:120px;border-radius:8px;'>" +
                        "</div>" +
                        "<h2 style=\"color:#344353;text-align:center;margin-bottom:12px;\">¡Gracias por tu compra!</h2>" +
                        "<p style=\"color:#222;text-align:center;font-size:16px;\">Has adquirido la <b>suscripción para el acceso a la liga</b>.</p>" +
                        "<div style=\"text-align:center;margin:30px 0;\">" +
                        "<span style=\"display:inline-block;padding:14px 32px;background:#344353;color:#fff;text-decoration:none;border-radius:8px;font-weight:bold;font-size:17px;box-shadow:0 1px 6px rgba(52,67,83,0.10);\">Acceso concedido</span>" +
                        "</div>" +
                        "<p style=\"margin-top:18px;color:#888;font-size:13px;text-align:center;\">Si tienes dudas, responde a este correo.<br>¡Disfruta de la liga!</p>" +
                        "<div style=\"display:none;max-width:0;overflow:hidden;\">&nbsp;</div>" +
                        "</div>";

                helper.setText(html, true);
                mailSender.send(mimeMessage);
            }
            catch (jakarta.mail.MessagingException e) {
                throw new RuntimeException("Error al enviar el correo de restablecimiento", e);
            }


        } catch (StripeException e) {
            throw new RuntimeException("Error para acceder a la sesión de stripe ", e);
        }

        return StripeResponse
                .builder()
                .status("SUCCESS")
                .message("Payment session created ")
                .sessionId(session.getId())
                .sessionUrl(session.getUrl())
                .build();



    }



}
