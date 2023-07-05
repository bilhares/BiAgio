package com.biagio.web.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.biagio.model.entity.Cartao;
import com.biagio.repository.CartaoRepository;
import com.biagio.service.CartaoDataTableService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@Controller
@RequestMapping("cartoes")
public class CartaoController {

	@Autowired
	CartaoRepository cartaoRepository;

	@GetMapping("/listar")
	public String listar() {
		return "/cartao/listar";
	}

	@GetMapping("/datatables/server")
	public ResponseEntity<?> dataTables(HttpServletRequest request) {
		Map<String, Object> data = new CartaoDataTableService().execute(cartaoRepository, request);
		return ResponseEntity.ok(data);
	}

	@GetMapping("/cadastro")
	public String cadastro(Cartao cartao) {
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
}
