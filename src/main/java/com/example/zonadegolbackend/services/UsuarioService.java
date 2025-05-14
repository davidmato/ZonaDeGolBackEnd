package com.example.zonadegolbackend.services;


import com.example.zonadegolbackend.dtos.AuthenticationDTO;
import com.example.zonadegolbackend.dtos.UsuarioDto;
import com.example.zonadegolbackend.entity.Entrenador;
import com.example.zonadegolbackend.entity.TokenAcceso;
import com.example.zonadegolbackend.entity.Usuario;
import com.example.zonadegolbackend.enums.Rol;
import com.example.zonadegolbackend.repository.EntrenadorRepository;
import com.example.zonadegolbackend.repository.UsuarioRepository;
import com.example.zonadegolbackend.security.JwtService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class UsuarioService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;
    private final EntrenadorRepository entrenadorRepository;
    private final TokenService tokenService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

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
        usuarioRepository.save(usuario);
        Entrenador entrenador = new Entrenador();
        entrenador.setUsuario(usuario);
        entrenador.setDni(userDTO.getDni());
        entrenador.setNombre(userDTO.getNombreEntrenador());
        entrenador.setApellido(userDTO.getApellido());
        entrenador.setFechaNacimiento(userDTO.getFechaNacimiento());
        entrenador.setImagen(userDTO.getImagenEntrenador());
        entrenadorRepository.save(entrenador);

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
                TokenAcceso token = usuario.getToken() == null ? new TokenAcceso() : usuario.getToken();
                token.setUsuario(usuario);
                token.setToken(apiKey);
                token.setFechaExpiracion(LocalDateTime.now().plusDays(1));
                tokenService.save(token);
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


    public void crearUsuario(Usuario usuario) {

        Usuario usuarioNuevo = new Usuario();

        usuarioNuevo.setUsername(usuario.getUsername());
        usuarioNuevo.setCorreo(usuario.getCorreo());
        usuarioNuevo.setPassword(usuario.getPassword());
        usuarioNuevo.setRol(Rol.JUGADOR);

        usuarioRepository.save(usuarioNuevo);
    }



    public Usuario FindAllArbitros() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        Usuario usuarioAutenticado = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (usuarioAutenticado.getRol() != Rol.ADMIN) {
            throw new RuntimeException("Solo un administrador puede ver los árbitros");
        }

        return usuarioRepository.findAllByRol(Rol.ARBITRO);
    }


    public Usuario CrearArbitro(Usuario usuario) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        Usuario usuarioAutenticado = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (usuarioAutenticado.getRol() != Rol.ADMIN) {
            throw new RuntimeException("Solo un administrador puede crear árbitros");
        }

        Usuario usuarioNuevo = new Usuario();

        usuarioNuevo.setUsername(usuario.getUsername());
        usuarioNuevo.setCorreo(usuario.getCorreo());
        usuarioNuevo.setPassword(usuario.getPassword());
        usuarioNuevo.setRol(Rol.ARBITRO);

        return usuarioRepository.save(usuarioNuevo);
    }

    public Usuario editarArbitro(Integer id, Usuario usuario) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        Usuario usuarioAutenticado = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (usuarioAutenticado.getRol() != Rol.ADMIN) {
            throw new RuntimeException("Solo un administrador puede editar árbitros");
        }

        Usuario usuarioExistente = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        usuarioExistente.setUsername(usuario.getUsername());
        usuarioExistente.setCorreo(usuario.getCorreo());
        usuarioExistente.setPassword(usuario.getPassword());

        return usuarioRepository.save(usuarioExistente);
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
        usuarioRepository.delete(usuario);
    }




}
