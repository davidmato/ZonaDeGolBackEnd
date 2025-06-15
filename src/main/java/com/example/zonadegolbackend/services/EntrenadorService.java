package com.example.zonadegolbackend.services;

import com.example.zonadegolbackend.dtos.ClasificacionDTO;
import com.example.zonadegolbackend.dtos.EntrenadorDTO;
import com.example.zonadegolbackend.dtos.CrearEquipo;
import com.example.zonadegolbackend.dtos.CrearJugador;
import com.example.zonadegolbackend.entity.*;
import com.example.zonadegolbackend.enums.Rol;
import com.example.zonadegolbackend.repository.*;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@AllArgsConstructor
public class EntrenadorService {

    private final EntrenadorRepository entrenadorRepository;
    private final UsuarioRepository usuarioRepository;
    private final EquipoRepository equipoRepository;
    private final JugadorRepository jugadorRepository;
    private final LigaRepository ligaRepository;
    private final PasswordEncoder passwordEncoder;
    private final EstadisticasRepository estadisticasRepository;
    private final TemporadaService temporadaService;
    private final JavaMailSender mailSender;
    private final ClasificacionRepository clasificacionRepository;
    private final JornadaRepository jornadaRepository;



    public List<EntrenadorDTO> listarEntrenador() {
        List<Entrenador> entrenadores = entrenadorRepository.findAll();
        List<EntrenadorDTO> entrenadorDTOS = new ArrayList<>();

        for (Entrenador entrenador : entrenadores) {
            EntrenadorDTO entrenadorDTO = new EntrenadorDTO();
            entrenadorDTO.setNombre(entrenador.getNombre());
            entrenadorDTO.setApellido(entrenador.getApellido());
            entrenadorDTO.setFechaNacimiento(entrenador.getFechaNacimiento());
            entrenadorDTO.setDni(entrenador.getDni());
            entrenadorDTO.setImagen(entrenador.getImagen());
            entrenadorDTO.setUsername(entrenador.getUsuario().getUsername());
            entrenadorDTO.setCorreo(entrenador.getUsuario().getCorreo());
            entrenadorDTOS.add(entrenadorDTO);
        }
        return entrenadorDTOS;
    }

    public List<Entrenador> findAll() {
        return entrenadorRepository.findAll();
    }

    public void validarPago(Usuario usuario) {
        if (usuario.getPagado() == null || !usuario.getPagado()) {
            throw new RuntimeException("Debes haber pagado para realizar esta acción");
        }
    }


    public Entrenador create(EntrenadorDTO entrenadorDTO) {
        Usuario usuario = new Usuario();
        usuario.setUsername(entrenadorDTO.getUsername());
        usuario.setPassword(entrenadorDTO.getPassword());
        usuario.setCorreo(entrenadorDTO.getCorreo());
        usuario.setPagado(true);
        usuario.setFechaRegistro(LocalDateTime.now());
        usuario.setRol(Rol.ENTRENADOR);

        usuarioRepository.save(usuario);

        Entrenador entrenador = new Entrenador();
        entrenador.setNombre(entrenadorDTO.getNombre());
        entrenador.setApellido(entrenadorDTO.getApellido());
        entrenador.setFechaNacimiento(entrenadorDTO.getFechaNacimiento());
        entrenador.setDni(entrenadorDTO.getDni());
        entrenador.setImagen(entrenadorDTO.getImagen());
        entrenador.setUsuario(usuario);

        return entrenadorRepository.save(entrenador);
    }


    public Entrenador update(Integer idEntrenador, EntrenadorDTO entrenadorDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        Usuario usuarioAutenticado = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (usuarioAutenticado.getRol() != Rol.ADMIN) {
            throw new RuntimeException("Solo un administrador puede editar entrenadores");
        }

        Entrenador entrenador = entrenadorRepository.findById(idEntrenador)
                .orElseThrow(() -> new RuntimeException("Entrenador no encontrado"));

        Usuario usuario = entrenador.getUsuario();
        usuario.setUsername(entrenadorDTO.getUsername());
        usuario.setPassword(entrenadorDTO.getPassword());
        usuario.setCorreo(entrenadorDTO.getCorreo());
        usuarioRepository.save(usuario);

        entrenador.setNombre(entrenadorDTO.getNombre());
        entrenador.setApellido(entrenadorDTO.getApellido());
        entrenador.setFechaNacimiento(entrenadorDTO.getFechaNacimiento());
        entrenador.setDni(entrenadorDTO.getDni());
        entrenador.setImagen(entrenadorDTO.getImagen());
        entrenador.setUsuario(usuario);

        return entrenadorRepository.save(entrenador);
    }

    public void delete(Integer idEntrenador) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        Usuario usuarioAutenticado = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (usuarioAutenticado.getRol() != Rol.ADMIN) {
            throw new RuntimeException("Solo un administrador puede editar entrenadores");
        }
        Entrenador entrenador = entrenadorRepository.findById(idEntrenador)
                .orElseThrow(() -> new RuntimeException("Entrenador no encontrado"));
        entrenadorRepository.delete(entrenador);
    }

    public void deleteEyU(Integer idEntrenador) {
        Entrenador entrenador = entrenadorRepository.findById(idEntrenador)
                .orElseThrow(() -> new RuntimeException("Entrenador no encontrado"));
        Usuario usuario = entrenador.getUsuario();
        entrenadorRepository.delete(entrenador);
        usuarioRepository.delete(usuario);
    }

    public Equipo createEquipo(CrearEquipo crearEquipo, Integer idLiga) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        validarPago(usuario);

        if (usuario.getRol() != Rol.ENTRENADOR) {
            throw new RuntimeException("Solo un entrenador puede crear un equipo");
        }

        Entrenador entrenador = entrenadorRepository.findByUsuario(usuario)
                .orElseThrow(() -> new RuntimeException("Entrenador no encontrado"));

        if (equipoRepository.existsByEntrenador(entrenador)) {
            throw new RuntimeException("El entrenador ya tiene un equipo");
        }

        Liga liga = ligaRepository.findById(idLiga)
                .orElseThrow(() -> new RuntimeException("Liga no encontrada"));

        Equipo equipo = new Equipo();
        equipo.setNombre(crearEquipo.getNombre());
        equipo.setDescripcion(crearEquipo.getDescripcion());
        equipo.setFechaFundacion(crearEquipo.getFechaFundacion());
        equipo.setImagen(crearEquipo.getImagen());
        equipo.setEntrenador(entrenador);
        equipo.setLiga(liga);

        return equipoRepository.save(equipo);
    }

    public Jugador createJugador(CrearJugador crearJugador) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        validarPago(usuario);

        if (usuario.getRol() != Rol.ENTRENADOR) {
            throw new RuntimeException("Solo un entrenador puede crear un jugador");
        }

        Entrenador entrenador = entrenadorRepository.findByUsuario(usuario)
                .orElseThrow(() -> new RuntimeException("Entrenador no encontrado"));

        Equipo equipo = equipoRepository.findByEntrenador(entrenador);
        if (equipo == null) {
            throw new RuntimeException("El entrenador no tiene un equipo");
        }

        if (crearJugador.getDorsal() > 99) {
            throw new RuntimeException("El dorsal no puede ser mayor que 99");
        }

        int cantidadJugadoresActivos = jugadorRepository.countByEquipoAndActivoTrue(equipo);
        if (cantidadJugadoresActivos >=13) {
            throw new RuntimeException("No se pueden crear más de 13 activos jugadores por equipo");
        }

        int cantidadJugadores = jugadorRepository.countByEquipo(equipo);
        if (cantidadJugadores >= 21) {
            throw new RuntimeException("No se pueden crear más de 21 jugadores por equipo");
        }

        Usuario usuarioJugador = new Usuario();
        usuarioJugador.setUsername(crearJugador.getNombre()+crearJugador.getApellido()+crearJugador.getDorsal());
        usuarioJugador.setPassword(passwordEncoder.encode(crearJugador.getDni()));
        usuarioJugador.setCorreo(crearJugador.getCorreo());
        usuarioJugador.setPagado(true);
        usuarioJugador.setFechaRegistro(LocalDateTime.now());
        usuarioJugador.setRol(Rol.JUGADOR);

        usuarioRepository.save(usuarioJugador);

        Jugador jugador = new Jugador();
        jugador.setNombre(crearJugador.getNombre());
        jugador.setApellido(crearJugador.getApellido());
        jugador.setPosicion(crearJugador.getPosicion());
        jugador.setDorsal(crearJugador.getDorsal());
        jugador.setFechaNacimiento(crearJugador.getFechaNacimiento());
        jugador.setImagen(crearJugador.getImagen());
        jugador.setDni(crearJugador.getDni());
        jugador.setExpulsado(false);
        jugador.setActivo(true);
        jugador.setUsuario(usuarioJugador);
        jugador.setEquipo(equipo);

        jugador = jugadorRepository.save(jugador);

        Estadisticas estadisticas = new Estadisticas();
        estadisticas.setGoles(0);
        estadisticas.setAsistencias(0);
        estadisticas.setTarjetasAmarillas(0);
        estadisticas.setTarjetasRojas(0);
        estadisticas.setPartidosJugados(0);
        estadisticas.setPorteriaCero(0);
        Temporada temporada = temporadaService.buscarTemporadaPorAnioActual();
        if (temporada == null) {
            throw new RuntimeException("No existe una temporada para el año actual");
        }
        estadisticas.setTemporada(temporada);
        estadisticas.setJugador(jugador);
        estadisticasRepository.save(estadisticas);


        return jugador;
    }

    public Equipo updateEquipo(CrearEquipo crearEquipo) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        validarPago(usuario);

        if (usuario.getRol() != Rol.ENTRENADOR) {
            throw new RuntimeException("Solo un entrenador puede editar un equipo");
        }

        Entrenador entrenador = entrenadorRepository.findByUsuario(usuario)
                .orElseThrow(() -> new RuntimeException("Entrenador no encontrado"));

        Equipo equipo = equipoRepository.findByEntrenador(entrenador);
        if (equipo == null) {
            throw new RuntimeException("El entrenador no tiene un equipo asignado");
        }

        equipo.setNombre(crearEquipo.getNombre());
        equipo.setDescripcion(crearEquipo.getDescripcion());
        equipo.setFechaFundacion(crearEquipo.getFechaFundacion());
        equipo.setImagen(crearEquipo.getImagen());

        return equipoRepository.save(equipo);
    }

    public Jugador updateJugador(CrearJugador crearJugador, Integer idJugador) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        validarPago(usuario);

        if (usuario.getRol() != Rol.ENTRENADOR) {
            throw new RuntimeException("Solo un entrenador puede editar un jugador");
        }

        Entrenador entrenador = entrenadorRepository.findByUsuario(usuario)
                .orElseThrow(() -> new RuntimeException("Entrenador no encontrado"));


        Equipo equipo = equipoRepository.findByEntrenador(entrenador);
        if (equipo == null) {
            throw new RuntimeException("El entrenador no tiene un equipo asignado");
        }

        Jugador jugador = jugadorRepository.findById(idJugador)
                .orElseThrow(() -> new RuntimeException("Jugador no encontrado"));

        jugador.setNombre(crearJugador.getNombre());
        jugador.setApellido(crearJugador.getApellido());
        jugador.setPosicion(crearJugador.getPosicion());
        jugador.setDorsal(crearJugador.getDorsal());
        jugador.setFechaNacimiento(crearJugador.getFechaNacimiento());
        jugador.setImagen(crearJugador.getImagen());
        jugador.setDni(crearJugador.getDni());

        return jugadorRepository.save(jugador);
    }

    public Jugador cambiarEstadoActivoJugador(Integer idJugador) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        validarPago(usuario);

        if (usuario.getRol() != Rol.ENTRENADOR) {
            throw new RuntimeException("Solo un entrenador puede cambiar el estado de un jugador");
        }

        Entrenador entrenador = entrenadorRepository.findByUsuario(usuario)
                .orElseThrow(() -> new RuntimeException("Entrenador no encontrado"));

        Equipo equipo = equipoRepository.findByEntrenador(entrenador);
        if (equipo == null) {
            throw new RuntimeException("El entrenador no tiene un equipo asignado");
        }

        Jugador jugador = jugadorRepository.findById(idJugador)
                .orElseThrow(() -> new RuntimeException("Jugador no encontrado"));

        if (!jugador.getEquipo().getId().equals(equipo.getId())) {
            throw new RuntimeException("El jugador no pertenece a tu equipo");
        }

        if (!jugador.isActivo()) {
            int activos = jugadorRepository.countByEquipoAndActivoTrue(equipo);
            if (activos >= 12) {
                throw new RuntimeException("No puede haber más de 12 jugadores activos en el equipo");
            }
        }

        jugador.setActivo(!jugador.isActivo());
        return jugadorRepository.save(jugador);
    }


    private void enviarCorreoHtml(String para, String asunto, String html, String remitenteCorreo) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            helper.setTo(para);
            helper.setSubject(asunto);
            helper.setText(html, true);
            helper.setFrom(remitenteCorreo);
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new RuntimeException("Error al enviar el correo", e);
        }
    }

    public void enviarCorreoAdmin(String asunto, String contenido) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Usuario remitente = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (remitente.getRol() != Rol.ENTRENADOR && remitente.getRol() != Rol.JUGADOR && remitente.getRol() != Rol.ARBITRO) {
            throw new RuntimeException("Solo un entrenador, jugador o árbitro puede enviar correos al administrador");
        }
        String correoAdmin = "soportezonadegol@gmail.com";
        String cuerpoHtml = "<div style=\"max-width:400px;margin:40px auto;padding:24px;background:#f9f9f9;border-radius:10px;box-shadow:0 2px 8px rgba(0,0,0,0.08);font-family:Arial,sans-serif;\">" +
                "<div style='text-align:center; margin-bottom:16px;'>" +
                "<img src='https://res.cloudinary.com/dyfoaulb5/image/upload/fl_preserve_transparency/v1747739581/logo_ohmfq7.jpg' alt='Logo' style='max-width:120px;'>" +
                "</div>" +
                "<h2 style=\"color:#333;text-align:center;\">Nuevo mensaje de entrenador</h2>" +
                "<p style=\"text-align:center;\"><b>Usuario:</b> " + remitente.getUsername() + "</p>" +
                "<p style=\"text-align:center;\">" + contenido + "</p>" +
                "</div>";

        enviarCorreoHtml(correoAdmin, asunto, cuerpoHtml, remitente.getCorreo());

        String asuntoConfirmacion = "Confirmación de envío de correo al administrador";
        String cuerpoConfirmacionHtml = "<div style=\"max-width:400px;margin:40px auto;padding:24px;background:#f9f9f9;border-radius:10px;box-shadow:0 2px 8px rgba(0,0,0,0.08);font-family:Arial,sans-serif;\">" +
                "<div style='text-align:center; margin-bottom:16px;'>" +
                "<img src='https://res.cloudinary.com/dyfoaulb5/image/upload/fl_preserve_transparency/v1747739581/logo_ohmfq7.jpg' alt='Logo' style='max-width:120px;'>" +
                "</div>" +
                "<h2 style=\"color:#333;text-align:center;\">Correo enviado correctamente</h2>" +
                "<p style=\"text-align:center;\">Tu mensaje con asunto '<b>" + asunto + "</b>' ha sido enviado correctamente al administrador.</p>" +
                "</div>";

        enviarCorreoHtml(remitente.getCorreo(), asuntoConfirmacion, cuerpoConfirmacionHtml, correoAdmin);
    }


    public List<ClasificacionDTO> obtenerClasificacionUltimaTemporadaLigaEntrenadorLogueado() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Entrenador entrenador = entrenadorRepository.findByUsuario(usuario)
                .orElseThrow(() -> new RuntimeException("Entrenador no encontrado"));

        Equipo equipo = equipoRepository.findByEntrenador(entrenador);
        if (equipo == null) {
            throw new RuntimeException("El entrenador no tiene equipo asignado");
        }

        Integer ligaId = equipo.getLiga().getId();

        // Buscar la última clasificación del equipo para obtener la última temporada jugada
        List<Clasificacion> clasificacionesEquipo = clasificacionRepository.findByEquipoId(equipo.getId());
        if (clasificacionesEquipo == null || clasificacionesEquipo.isEmpty()) {
            throw new RuntimeException("No hay clasificaciones para el equipo");
        }
        Clasificacion ultimaClasificacion = clasificacionesEquipo.stream()
                .max((c1, c2) -> c1.getTemporada().getFechaInicio().compareTo(c2.getTemporada().getFechaInicio()))
                .orElseThrow(() -> new RuntimeException("No se encontró la última clasificación"));

        Integer temporadaId = ultimaClasificacion.getTemporada().getId();

        // Obtener todas las clasificaciones de la liga y temporada
        List<Clasificacion> clasificaciones = clasificacionRepository.findByEquipo_Liga_IdAndTemporada_Id(ligaId, temporadaId)
                .stream()
                .sorted(Comparator.comparingInt(Clasificacion::getPuesto))
                .toList();

        // Mapear a DTO incluyendo la forma de los últimos 5 partidos
        return clasificaciones.stream().map(c -> {
            ClasificacionDTO dto = new ClasificacionDTO();
            dto.setPuesto(c.getPuesto());
            dto.setNombre(c.getEquipo().getNombre());
            dto.setPartidosJugados(c.getPartidosJugados());
            dto.setVictorias(c.getVictorias());
            dto.setEmpates(c.getEmpates());
            dto.setDerrotas(c.getDerrotas());
            dto.setGolAFavor(c.getGolAFavor());
            dto.setGolEnContra(c.getGolEnContra());
            dto.setGolDiferencia(c.getGolDiferencia());
            dto.setImagenEquipo(c.getEquipo().getImagen());
            dto.setPuntos(c.getPuntos());

            List<Jornada> ultimos5 = jornadaRepository.findLast5ByEquipoAndTemporada(
                    c.getEquipo(), c.getTemporada(), PageRequest.of(0, 5));

            List<String> forma = ultimos5.stream().map(j -> {
                int golesEquipo, golesRival;
                boolean esLocal = j.getEquipoLocal().getId().equals(c.getEquipo().getId());

                if (esLocal) {
                    golesEquipo = j.getGolLocal();
                    golesRival = j.getGolVisitante();
                } else {
                    golesEquipo = j.getGolVisitante();
                    golesRival = j.getGolLocal();
                }

                if (golesEquipo > golesRival) return "✅";
                else if (golesEquipo == golesRival) return "➖";
                else return "❌";
            }).toList();

            dto.setForma(forma);

            return dto;
        }).toList();
    }



}
