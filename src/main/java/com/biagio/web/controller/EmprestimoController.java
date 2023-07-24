package com.biagio.web.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.biagio.model.entity.Cartao;
import com.biagio.model.entity.Emprestimo;
import com.biagio.model.entity.Endividado;
import com.biagio.model.security.Usuario;
import com.biagio.repository.CartaoRepository;
import com.biagio.repository.EmprestimoRepository;
import com.biagio.repository.EndividadoRepository;
import com.biagio.repository.UsuarioRepository;
import com.biagio.service.EmprestimoService;
import com.biagio.service.UsuarioService;
import com.biagio.web.validator.EmprestimoValidator;

import jakarta.validation.Valid;

@Controller
@RequestMapping("emprestimos")
public class EmprestimoController {

	@Autowired
	EmprestimoRepository emprestimoRepository;

	@Autowired
	EndividadoRepository endividadoRepository;

	@Autowired
	CartaoRepository cartaoRepository;

	@Autowired
	UsuarioService usuarioService;

	@Autowired
	EmprestimoService service;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.addValidators(new EmprestimoValidator());
	}

	@GetMapping("/listar")
	public String listar(ModelMap model) {
		Usuario usuario = usuarioService.obterUsuarioLogado();
		List<Cartao> cartoes = cartaoRepository.findByAtivoAndUsuario(true, usuario);
		model.addAttribute("emprestimos", emprestimoRepository.findByCartaoIn(cartoes));
		return "/emprestimo/listar";
	}

	@GetMapping("/cadastro")
	public String cadastro(Emprestimo entity) {
		return "/emprestimo/cadastrar";
	}

	@GetMapping("/editar/{id}")
	public String editar(@PathVariable("id") Long id, ModelMap model) {
		model.addAttribute("emprestimo", emprestimoRepository.findById(id).get());
		return "/emprestimo/cadastrar";
	}

	@PostMapping("/cadastro/salvar")
	public String salvar(@Valid Emprestimo entity, BindingResult result, RedirectAttributes attr) {

		if (result.hasErrors()) {
			return "/emprestimo/cadastrar";
		}

		service.criarEmprestimo(entity);

		attr.addFlashAttribute("sucesso", "Endividado cadastrado com sucesso!");

		return "redirect:/emprestimos/cadastro";
	}

	@ModelAttribute("endividados")
	public List<Endividado> getEndividados() {
		return endividadoRepository.findByAtivo(true);
	}

	@ModelAttribute("cartoes")
	public List<Cartao> getCartoes(@AuthenticationPrincipal User user) {
		Usuario usuario = usuarioService.obterUsuarioLogado();
		return cartaoRepository.findByAtivoAndUsuario(true, usuario);
	}

}
