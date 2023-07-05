package com.biagio.web.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.biagio.model.entity.Cartao;
import com.biagio.repository.CartaoRepository;

import jakarta.validation.Valid;

@Controller
@RequestMapping("cartoes")
public class CartaoController {

	@Autowired
	CartaoRepository cartaoRepository;

	@GetMapping("/listar")
	public String listar(ModelMap model) {
		model.addAttribute("cartoes", cartaoRepository.findAll());
		return "/cartao/listar";
	}

	@GetMapping("/cadastro")
	public String cadastro(Cartao cartao) {
		return "/cartao/cadastrar";
	}

	@GetMapping("/editar/{id}")
	public String editar(@PathVariable("id") Long id, ModelMap model) {
		model.addAttribute("cartao", cartaoRepository.findById(id).get());
		return "/cartao/cadastrar";
	}

	@PostMapping("/cadastro/salvar")
	public String salvar(@Valid Cartao cartao, BindingResult result, RedirectAttributes attr) {

		if (result.hasErrors()) {
			return "/cartao/cadastrar";
		}

		cartao.setAtivo(true);
		cartaoRepository.save(cartao);
		attr.addFlashAttribute("sucesso", "Cartão cadastrado com sucesso!");

		return "redirect:/cartoes/cadastro";
	}

	@PostMapping("/cadastro/editar")
	public String salvarEdicao(@Valid Cartao cartao, BindingResult result, RedirectAttributes attr) {

		if (result.hasErrors()) {
			return "/cartao/cadastrar";
		}
		cartaoRepository.save(cartao);
		attr.addFlashAttribute("sucesso", "Cartão atualizado com sucesso!");

		return "redirect:/cartoes/cadastro";
	}

	@GetMapping("/inativar/{id}")
	public String inativar(@PathVariable("id") Long id, RedirectAttributes attr) {

		Optional<Cartao> cartaoOpt = cartaoRepository.findById(id);
		if (cartaoOpt.isEmpty())
			attr.addFlashAttribute("falha", "Cartão não encontrado");

		Cartao cartao = cartaoOpt.get();
		cartao.setAtivo(false);

		cartaoRepository.save(cartao);

		return "redirect:/cartoes/listar";
	}

}
