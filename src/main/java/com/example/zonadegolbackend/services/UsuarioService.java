package com.example.zonadegolbackend.services;


import com.example.zonadegolbackend.dtos.ArbitroDTO;
import com.example.zonadegolbackend.dtos.AuthenticationDTO;
import com.example.zonadegolbackend.dtos.UsuarioDto;
import com.example.zonadegolbackend.entity.*;
import com.example.zonadegolbackend.enums.Rol;
import com.example.zonadegolbackend.repository.*;
import com.example.zonadegolbackend.security.JwtService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class UsuarioService implements UserDetailsService {

    @Autowired
    private JavaMailSender mailSender;

    private final UsuarioRepository usuarioRepository;
    private final EntrenadorRepository entrenadorRepository;
    private final TokenService tokenService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final ArbitroRepository arbitroRepository;
    private final EquipoRepository equipoRepository;
    private final JugadorRepository jugadorRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return usuarioRepository.findTopByUsername(email)
                .orElseThrow(()-> new UsernameNotFoundException("Usuario no encontrado"));
    }

    public Usuario buscarUsuarioPorNombre(String username) {
        return usuarioRepository.findTopByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
    }

    public AuthenticationDTO register(UsuarioDto userDTO) {
        Usuario usuario = new Usuario();
        usuario.setUsername(userDTO.getUsername());
        usuario.setCorreo(userDTO.getCorreo());
        usuario.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        usuario.setRol(Rol.ENTRENADOR);
        usuario.setFechaRegistro(LocalDateTime.now());
        usuario.setPagado(false);
        usuarioRepository.save(usuario);
        Entrenador entrenador = new Entrenador();
        entrenador.setUsuario(usuario);
        entrenador.setDni(userDTO.getDni());
        entrenador.setNombre(userDTO.getNombreEntrenador());
        entrenador.setApellido(userDTO.getApellido());
        entrenador.setFechaNacimiento(userDTO.getFechaNacimiento());
        entrenador.setImagen(userDTO.getImagenEntrenador());
        entrenadorRepository.save(entrenador);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(usuario.getCorreo());
        message.setSubject("Bienvenido a Zona de Gol");
        message.setText("¡Bienvenido, " + usuario.getUsername() + "! Tu registro ha sido exitoso.");
        mailSender.send(message);

        var jwtToken = jwtService.generateToken(usuario, usuario.getId(), usuario.getRol().name());
        return AuthenticationDTO.builder().token(jwtToken).build();

    }

    public AuthenticationDTO login(UsuarioDto usuarioDTO) {
        Usuario usuario;
        try {
            usuario = (Usuario) loadUserByUsername(usuarioDTO.getUsername());
        } catch (UsernameNotFoundException e) {
            usuario = null;
        }

        String apiKey = null;
        String mensaje;

        if (usuario == null) {
            mensaje = "Usuario No encontrado";
        } else if (!validarPassword(usuario, usuarioDTO.getPassword())) {
            mensaje = "Contraseña no válida";
        } else {
            if (usuario.getToken() == null || jwtService.isTokenExpired(usuario.getToken().getToken())) {
                apiKey = jwtService.generateToken(usuario, usuario.getId(), usuario.getRol().name());
//                TokenAcceso token = usuario.getToken() == null ? new TokenAcceso() : usuario.getToken();
//                token.setUsuario(usuario);
//                token.setToken(apiKey);
//                token.setFechaExpiracion(LocalDateTime.now().plusDays(1));
//                tokenService.save(token);
            } else {
                apiKey = usuario.getToken().getToken();
            }
        }

        return AuthenticationDTO.builder().token(apiKey).build();
    }



    public boolean validarPassword(Usuario usuario, String passwordSinEncriptar){
        return passwordEncoder.matches(passwordSinEncriptar, usuario.getPassword());
    }


    public Usuario getUsuarioById(Integer id) {
        return usuarioRepository.findById(id).orElse(null);
    }


    public void solicitarRestablecimientoPassword(String correo) {
        Usuario usuario = usuarioRepository.findByCorreo(correo)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ese correo"));

        String token = java.util.UUID.randomUUID().toString();
        usuario.setTokenRestablecimiento(token);
        usuario.setTokenExpiracion(LocalDateTime.now().plusHours(1));
        usuarioRepository.save(usuario);

        String enlace = "http://localhost:4200/restablecer?token=" + token;

        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            helper.setTo(usuario.getCorreo());
            helper.setSubject("Restablecimiento de contraseña");
            String html = "<div style=\"max-width:400px;margin:40px auto;padding:24px;background:#f9f9f9;border-radius:10px;box-shadow:0 2px 8px rgba(0,0,0,0.08);font-family:Arial,sans-serif;\">" +
                    "<div style='text-align:center; margin-bottom:16px;'>" +
                    "<img src='https://res.cloudinary.com/dyfoaulb5/image/upload/fl_preserve_transparency/v1747739581/logo_ohmfq7.jpg' alt='Logo' style='max-width:120px;'>" +
                    "</div>" +
                    "<h2 style=\"color:#333;text-align:center;\">Restablecimiento de contraseña</h2>" +
                    "<p style=\"text-align:center;\">Para restablecer tu contraseña, haz clic en el siguiente botón:</p>" +
                    "<div style=\"text-align:center;margin:24px 0;\">" +
                    "<a href='" + enlace + "' style=\"display:inline-block;padding:12px 28px;background:#344353;color:#fff;text-decoration:none;border-radius:6px;font-weight:bold;font-size:16px;box-shadow:0 1px 4px rgba(0,0,0,0.10);transition:background 0.2s;\">Restablecer contraseña</a>" +
                    "</div>" +
                    "<p style=\"margin-top:20px;color:#888;font-size:12px;text-align:center;\">Si no solicitaste este cambio, puedes ignorar este correo.</p>" +
                    "<div style=\"display:none;max-width:0;overflow:hidden;\">&nbsp;</div>" +
                    "</div>";
            helper.setText(html, true);
            mailSender.send(mimeMessage);
        } catch (jakarta.mail.MessagingException e) {
            throw new RuntimeException("Error al enviar el correo de restablecimiento", e);
        }
    }

    public void restablecerPassword(String token, String newPassword) {
        Usuario usuario = usuarioRepository.findByTokenRestablecimiento(token)
                .orElseThrow(() -> new RuntimeException("Token inválido"));

        if (usuario.getTokenExpiracion().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("El token ha expirado");
        }

        usuario.setPassword(passwordEncoder.encode(newPassword));
        usuario.setTokenRestablecimiento(null);
        usuario.setTokenExpiracion(null);
        usuarioRepository.save(usuario);
    }


    public void crearUsuario(Usuario userDTO) {

        Usuario usuario = new Usuario();
        usuario.setUsername(userDTO.getUsername());
        usuario.setCorreo(userDTO.getCorreo());
        usuario.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        usuario.setRol(Rol.ADMIN);
        usuario.setFechaRegistro(LocalDateTime.now());
        usuario.setPagado(true);
        usuarioRepository.save(usuario);
    }



    public List<ArbitroDTO> findAllArbitros() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        Usuario usuarioAutenticado = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (usuarioAutenticado.getRol() != Rol.ADMIN) {
            throw new RuntimeException("Solo un administrador puede ver los árbitros");
        }

        List<Arbitro> arbitros = arbitroRepository.findAll();
        return arbitros.stream().map(arbitro -> {
            Usuario usuario = arbitro.getUsuario();
            ArbitroDTO dto = new ArbitroDTO();
            dto.setId(usuario.getId());
            dto.setUsername(usuario.getUsername());
            dto.setCorreo(usuario.getCorreo());
            dto.setNombreArbitro(arbitro.getNombre());
            dto.setApellido(arbitro.getApellidos());
            dto.setNumColegiado(arbitro.getNumColegiado());
            dto.setDni(arbitro.getDni());
            return dto;
        }).toList();
    }


    public ArbitroDTO CrearArbitro(ArbitroDTO usuario) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        Usuario usuarioAutenticado = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (usuarioAutenticado.getRol() != Rol.ADMIN) {
            throw new RuntimeException("Solo un administrador puede crear árbitros");
        }


        Usuario usuarioNuevo = new Usuario();
        usuarioNuevo.setUsername(usuario.getNombreArbitro() + "_" + usuario.getApellido());
        usuarioNuevo.setCorreo(usuario.getCorreo());
        usuarioNuevo.setPassword(passwordEncoder.encode(usuario.getPassword()));
        usuarioNuevo.setRol(Rol.ARBITRO);
        usuarioNuevo.setFechaRegistro(LocalDateTime.now());
        usuarioNuevo.setPagado(true);
        usuarioRepository.save(usuarioNuevo);

        Arbitro arbitro = new Arbitro();
        arbitro.setUsuario(usuarioNuevo);
        arbitro.setNumColegiado(usuario.getNumColegiado());
        arbitro.setDni(usuario.getDni());
        arbitro.setNombre(usuario.getNombreArbitro());
        arbitro.setApellidos(usuario.getApellido());
        arbitroRepository.save(arbitro);

        ArbitroDTO arbitroDTO = new ArbitroDTO();
        arbitroDTO.setUsername(usuarioNuevo.getUsername());
        arbitroDTO.setCorreo(usuarioNuevo.getCorreo());
        arbitroDTO.setNombreArbitro(usuario.getNombreArbitro());
        arbitroDTO.setApellido(usuario.getApellido());
        arbitroDTO.setNumColegiado(usuario.getNumColegiado());
        arbitroDTO.setDni(usuario.getDni());


        return arbitroDTO;

    }

    public ArbitroDTO editarArbitro(Integer id, ArbitroDTO arbitroDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        Usuario usuarioAutenticado = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (usuarioAutenticado.getRol() != Rol.ADMIN) {
            throw new RuntimeException("Solo un administrador puede editar árbitros");
        }

        Arbitro arbitro = arbitroRepository.findByUsuario_Id(id);
        if (arbitro == null) {
            throw new RuntimeException("Árbitro no encontrado");
        }
        Usuario usuario = arbitro.getUsuario();

        usuario.setUsername(arbitroDTO.getNombreArbitro() + "_" + arbitroDTO.getApellido());
        usuario.setCorreo(arbitroDTO.getCorreo());
        if (arbitroDTO.getPassword() != null && !arbitroDTO.getPassword().isEmpty()) {
            usuario.setPassword(passwordEncoder.encode(arbitroDTO.getPassword()));
        }
        usuarioRepository.save(usuario);

        arbitro.setNombre(arbitroDTO.getNombreArbitro());
        arbitro.setApellidos(arbitroDTO.getApellido());
        arbitro.setNumColegiado(arbitroDTO.getNumColegiado());
        arbitro.setDni(arbitroDTO.getDni());
        arbitroRepository.save(arbitro);

        ArbitroDTO respuesta = new ArbitroDTO();
        respuesta.setUsername(usuario.getUsername());
        respuesta.setCorreo(usuario.getCorreo());
        respuesta.setNombreArbitro(arbitro.getNombre());
        respuesta.setApellido(arbitro.getApellidos());
        respuesta.setNumColegiado(arbitro.getNumColegiado());
        respuesta.setDni(arbitro.getDni());

        return respuesta;
    }

    public void eliminarUsuario(Integer id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Usuario usuarioAutenticado = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        if (usuarioAutenticado.getRol() != Rol.ADMIN) {
            throw new RuntimeException("Solo un administrador puede eliminar usuarios");
        }

        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Eliminar árbitro si existe
        Arbitro arbitro = arbitroRepository.findByUsuarioUsername(usuario.getUsername()).orElse(null);
        if (arbitro != null) {
            arbitroRepository.delete(arbitro);
        }
        usuarioRepository.delete(usuario);
    }
    public boolean usuarioHaPagado(Integer usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId).orElse(null);
        return usuario != null && Boolean.TRUE.equals(usuario.getPagado());
    }


    public void marcarUsuariosEquipoComoNoPagados(Integer idEquipo) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Usuario usuarioAutenticado = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        if (usuarioAutenticado.getRol() != Rol.ADMIN) {
            throw new RuntimeException("Solo un administrador puede realizar esta acción");
        }

        Equipo equipo = equipoRepository.findById(idEquipo)
                .orElseThrow(() -> new RuntimeException("Equipo no encontrado"));

        Entrenador entrenador = equipo.getEntrenador();
        if (entrenador != null && entrenador.getUsuario() != null) {
            Usuario usuarioEntrenador = entrenador.getUsuario();
            usuarioEntrenador.setPagado(false);
            usuarioRepository.save(usuarioEntrenador);
        }

        List<Jugador> jugadores = jugadorRepository.findByEquipo(equipo);
        for (Jugador jugador : jugadores) {
            Usuario usuarioJugador = jugador.getUsuario();
            if (usuarioJugador != null) {
                usuarioJugador.setPagado(false);
                usuarioRepository.save(usuarioJugador);
            }
        }
    }

    public void marcarUsuariosEquipoComoPagados(Integer idEquipo) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Usuario usuarioAutenticado = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        if (usuarioAutenticado.getRol() != Rol.ADMIN) {
            throw new RuntimeException("Solo un administrador puede realizar esta acción");
        }

        Equipo equipo = equipoRepository.findById(idEquipo)
                .orElseThrow(() -> new RuntimeException("Equipo no encontrado"));

        Entrenador entrenador = equipo.getEntrenador();
        if (entrenador != null && entrenador.getUsuario() != null) {
            Usuario usuarioEntrenador = entrenador.getUsuario();
            usuarioEntrenador.setPagado(true);
            usuarioRepository.save(usuarioEntrenador);
        }

        List<Jugador> jugadores = jugadorRepository.findByEquipo(equipo);
        for (Jugador jugador : jugadores) {
            Usuario usuarioJugador = jugador.getUsuario();
            if (usuarioJugador != null) {
                usuarioJugador.setPagado(true);
                usuarioRepository.save(usuarioJugador);
            }
        }
    }

    @Scheduled(cron = "0 0 3 * * ?") // Todos los días a las 3:00 AM
    @Transactional
    public void eliminarUsuariosNoPagadosAntiguos() {
        LocalDateTime haceUnMes = LocalDateTime.now().minusMonths(1);
        List<Usuario> usuarios = usuarioRepository.findByPagadoFalseAndFechaRegistroBefore(haceUnMes);
        usuarioRepository.deleteAll(usuarios);
    }



}
