package com.biagio.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.biagio.model.dto.ResumoEndividadosDTO;
import com.biagio.service.AdminService;

@Controller
@RequestMapping("admin")
public class AdminController {

	@Autowired
	AdminService adminService;

	@GetMapping("/listar")
	public String listar(ModelMap model) {
		List<ResumoEndividadosDTO> resumo = adminService.obterResumoDosEndividados();

		model.addAttribute("resumoEndividados", resumo);
		return "/admin/listar";
	}

	@GetMapping("/gerar-extrato-cobranca/{id}")
	public String gerarExtratoCobranca(@PathVariable("id") Long endividadoId, ModelMap model, RedirectAttributes attr) {

		adminService.gerarExtratoCobranca(endividadoId);
		attr.addFlashAttribute("sucesso", "A cobraça será gerada e enviada por email.");

		return "redirect:/admin/listar";
		
	}
}
