package br.ifpr.agenda.controllers;

import javax.validation.Valid;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.ifpr.agenda.dominio.Usuario;
import br.ifpr.agenda.repositories.UsuarioRepository;

import java.util.List;

@Controller
public class UsuarioController {

	private final PasswordEncoder passwordEncoder;
	
	private UsuarioRepository usuarioRepository;
	
	public UsuarioController(PasswordEncoder passwordEncoder, UsuarioRepository usuarioRepository) {
		this.passwordEncoder = passwordEncoder;
		this.usuarioRepository = usuarioRepository;
	}


	@GetMapping()
	public List<Usuario> get(){
		List usuario = usuarioRepository.findAll();
		return usuario;
	}


	@RequestMapping("/usuarios")
	public String getUsuarios (Model model) {
		model.addAttribute("usuarios", usuarioRepository.findAll());
		return "usuarios/indexUsuarios";
	}
	
	
	
	@GetMapping("/usuarios/novo")
	public String novoUsuario(Model model) {
		model.addAttribute("usuario", new Usuario(""));
		model.addAttribute("fieldToFocus", "login");
		return "usuarios/editarUsuarios";
	}
	
	
	@GetMapping("/usuarios/alterar/{id}")
	public String alterarUsuario(@PathVariable("id") long id, Model model) {
		Usuario usuario = usuarioRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Usuário inválido"));
		
		model.addAttribute("usuario", usuario);
		model.addAttribute("fieldToFocus", "login");
		return "usuarios/editarUsuarios";
	}
	
	
	@PostMapping("/usuarios/salvar")
	public String salvarUsuario(@Valid Usuario usuario, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			return "usuarios/editarUsuarios";
		}

		usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));

		usuarioRepository.save(usuario);
		
		return "redirect:/usuarios";
	}
	
	@PostMapping("/usuarios/excluir/{id}")
	public String excluirUsuario(@PathVariable("id") long id, Model model) {
		Usuario usuario = usuarioRepository.findById(id)
											.orElseThrow(() -> new IllegalArgumentException("Usuário inválido"));
		
		usuarioRepository.delete(usuario);
		
		return "redirect:/usuarios";
	}
	

}
