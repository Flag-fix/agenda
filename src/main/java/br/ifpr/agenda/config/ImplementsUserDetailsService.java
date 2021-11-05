package br.ifpr.agenda.config;

import br.ifpr.agenda.dominio.Usuario;
import br.ifpr.agenda.repositories.UsuarioRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class ImplementsUserDetailsService implements UserDetailsService {
    private final UsuarioRepository usuarioRepository;

    public ImplementsUserDetailsService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<Usuario> usuario = Optional.ofNullable(usuarioRepository.findByLogin(s))
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return usuario.get();
    }
}
