package com.biagio.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.Base64Utils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.biagio.model.security.Perfil;
import com.biagio.model.security.Usuario;
import com.biagio.repository.PerfilRepository;
import com.biagio.repository.UsuarioRepository;
import com.biagio.service.UsuarioService;

import jakarta.mail.MessagingException;
import jakarta.validation.Valid;

@Controller
@RequestMapping("usuarios")
public class UsuarioController {

	@Autowired
	PerfilRepository perfilRepository;

	@Autowired
	UsuarioRepository usuarioRepository;

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
		} catch (MessagingException e) {
			attr.addFlashAttribute("falha", "Cadastro não realizado, falha ao enviar email.");
		}

		return "redirect:/usuarios/cadastro";
	}

	@GetMapping("/confirmar/cadastro")
	public String confirmarCadastro(@RequestParam("codigo") String codigo, ModelMap model, RedirectAttributes attr,
			Usuario usuario) {
		String email = new String(Base64Utils.decodeFromString(codigo));

		Usuario usuarioEncontrado = usuarioRepository.findByEmail(email);

		if (usuarioEncontrado == null) {
			model.addAttribute("falha", "Usuário inexistente na base");

		} else if (usuarioEncontrado != null && usuarioEncontrado.isAtivo()) {
			model.addAttribute("falha", "Usuário já ativo no sistema");
		} else {
			usuarioEncontrado.setSenha(null);
			model.addAttribute("usuario", usuarioEncontrado);
		}

		return "/usuario/confirmar-cadastro";
	}

	@PostMapping("/cadastro/ativar")
	public String ativarCadastro(@Valid Usuario usuario, BindingResult result, RedirectAttributes attr) {

		Usuario usuarioEncontrado = usuarioRepository.findByEmail(usuario.getEmail());

		if (!usuarioEncontrado.getCodigoVerificador().equals(usuario.getCodigoVerificador())) {
			attr.addFlashAttribute("falha", "Código verificador não confere.");
			return "redirect:/usuarios/confirmar/cadastro?codigo="
					+ Base64Utils.encodeToString(usuario.getEmail().getBytes());
		}

		usuarioEncontrado.setCodigoVerificador(null);
		usuarioEncontrado.setAtivo(true);
		usuarioService.alterarSenha(usuarioEncontrado, usuario.getSenha());
		attr.addFlashAttribute("sucesso", "Usuário atualizado com sucesso!");

		return "redirect:/login";
	}
}
