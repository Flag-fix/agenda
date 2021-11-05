package br.ifpr.agenda.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.ifpr.agenda.dominio.Usuario;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{

    Optional<Usuario> findByLogin(String login);

}
