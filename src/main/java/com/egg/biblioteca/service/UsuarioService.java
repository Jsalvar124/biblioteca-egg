package com.egg.biblioteca.service;

import com.egg.biblioteca.entity.Usuario;
import com.egg.biblioteca.enums.Rol;
import com.egg.biblioteca.exeptions.MyException;
import com.egg.biblioteca.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService implements UserDetailsService {

    @Autowired
    UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Transactional
    public Usuario registrar(String nombre, String email, String password, String password2) throws MyException {
        validar(nombre, email, password, password2);
        Usuario usuario = new Usuario(
                nombre,
                email,
                new BCryptPasswordEncoder().encode(password), // guardar la contraseña encriptada.
                Rol.USER);
        return usuarioRepository.save(usuario);
    }

    private void validar(String nombre, String email, String password, String password2) throws MyException {
        if (nombre == null || nombre.isEmpty()) {
            throw new MyException("el nombre no puede ser nulo o estar vacío");
        }
        if (email == null || email.isEmpty()) {
            throw new MyException("el email no puede ser nulo o estar vacío");
        }
        if (password == null || password.length() <= 5) {
            throw new MyException("La contraseña no puede estar vacía, y debe tener más de 5 caracteres");
        }
        if (!password.equals(password2)) {
            throw new MyException("Las contraseñas ingresadas deben ser iguales");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByEmail(email);
        if(usuario != null){
            // Spring User Class and Authority list
            List<GrantedAuthority> permisos = new ArrayList<>();
            GrantedAuthority p = new SimpleGrantedAuthority("ROL_"+usuario.getRol().toString());
            permisos.add(p);
            return new User(usuario.getEmail(), usuario.getPassword(), permisos); // User from Spring Security Class.

        }else {
            return null;
        }
    }
}
