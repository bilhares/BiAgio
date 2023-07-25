package com.biagio.web.controller;

import java.util.List;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.biagio.model.dto.ResumoEndividadosDTO;
import com.biagio.model.dto.amqp.ExtratoCobrancaConsumerDTO;
import com.biagio.service.AdminService;
import com.biagio.service.UsuarioService;
import com.fasterxml.jackson.core.JsonProcessingException;

@Controller
@RequestMapping("admin")
public class AdminController {

	@Autowired
	AdminService adminService;

	@Autowired
	RabbitTemplate template;

	@Autowired
	UsuarioService usuarioService;

	@Value("${spring.rabbitmq.queue-cobranca}")
	private String queueCobranca;

	@GetMapping("/listar")
	public String listar(ModelMap model) {
		List<ResumoEndividadosDTO> resumo = adminService.obterResumoDosEndividados();

		model.addAttribute("resumoEndividados", resumo);
		return "/admin/listar";
	}

	@GetMapping("/gerar-extrato-cobranca/{id}")
	public String gerarExtratoCobranca(@PathVariable("id") Long endividadoId, ModelMap model, RedirectAttributes attr)
			throws JsonProcessingException {

		ExtratoCobrancaConsumerDTO dto = new ExtratoCobrancaConsumerDTO();
		dto.setEndividadoId(endividadoId);
		dto.setUsuario(usuarioService.obterUsuarioLogado());

		template.convertAndSend(queueCobranca, dto);
		attr.addFlashAttribute("sucesso", "A cobraça será gerada e enviada por email.");

		return "redirect:/admin/listar";
	}
}
