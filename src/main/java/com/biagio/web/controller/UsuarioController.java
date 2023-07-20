package com.biagio.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.biagio.model.security.Perfil;
import com.biagio.model.security.Usuario;
import com.biagio.repository.PerfilRepository;
import com.biagio.service.UsuarioService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("usuarios")
public class UsuarioController {

	@Autowired
	PerfilRepository perfilRepository;

	@Autowired
	UsuarioService usuarioService;

	@GetMapping("/cadastro")
	public String cadastro(Usuario usuario) {
		return "/usuario/cadastrar";
	}

	@ModelAttribute("perfis")
	public List<Perfil> getEndividados() {
		return perfilRepository.findAll();
	}

	@PostMapping("/cadastro/salvar")
	public String salvar(@Valid Usuario usuario, BindingResult result, RedirectAttributes attr) {
		try {
			usuarioService.salvarUsuario(usuario);
			attr.addFlashAttribute("sucesso", "Usuário cadastrado com sucesso!");
		} catch (DataIntegrityViolationException e) {
			attr.addFlashAttribute("falha", "Cadastro não realizado, email já existente.");
		}

		return "redirect:/usuarios/cadastro";
	}
}
