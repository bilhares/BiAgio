package com.biagio.web.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import com.biagio.model.dto.ResumoFaturasHomeDTO;
import com.biagio.model.security.Usuario;
import com.biagio.repository.UsuarioRepository;
import com.biagio.service.HomeService;

@Controller
public class HomeController {

	@Autowired
	UsuarioRepository usuarioRepository;
	
	@Autowired
	HomeService homeService;

	@GetMapping({ "/", "/home" })
	public String home(@AuthenticationPrincipal User user, ModelMap model) {
		
		ResumoFaturasHomeDTO resumoHome = homeService.obterResumoDasFaturas();
		
		Optional<Usuario> usuario = usuarioRepository.findByEmailAndAtivo(user.getUsername());
		
		model.addAttribute("usuario", usuario.get());
		model.addAttribute("resumoFaturas", resumoHome);
		return "home";
	}

	@GetMapping({ "/login" })
	public String login() {
		return "login";
	}
}
