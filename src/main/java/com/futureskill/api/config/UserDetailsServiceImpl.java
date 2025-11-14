package com.futureskill.api.config;

import com.futureskill.api.model.Usuario;
import com.futureskill.api.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado com email: " + email));
        
        return org.springframework.security.core.userdetails.User
                .withUsername(usuario.getEmail())
                .password(usuario.getSenha())
                .roles(usuario.getRole().name())
                .build();
    }
}

