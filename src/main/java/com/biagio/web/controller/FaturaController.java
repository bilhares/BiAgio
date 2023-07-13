package com.biagio.web.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.biagio.model.dto.DetalheFaturaDTO;
import com.biagio.model.dto.FaturaDTO;
import com.biagio.service.FaturaService;

@Controller
@RequestMapping("faturas")
public class FaturaController {

	@Autowired
	FaturaService faturaService;

	@GetMapping("/listar")
	public String listar(ModelMap model, @RequestParam("page") Optional<Integer> page,
			@RequestParam("size") Optional<Integer> size) {

		int currentPage = page.orElse(1);
		int pageSize = size.orElse(5);

		Pageable pageable = PageRequest.of(currentPage - 1, pageSize);

		Page<FaturaDTO> acutalPage = faturaService.obterFaturas(pageable);

		model.addAttribute("page", acutalPage);
		model.addAttribute("totalDeRegistros", acutalPage.getTotalElements());

		return "/fatura/listar";
	}

	@GetMapping("/detalhes/{id}/{dtVencimento}")
	public String detalhesFatura(@PathVariable("id") Long cartaoId,
			@PathVariable("dtVencimento") LocalDate dtVencimento, ModelMap model) {

		List<DetalheFaturaDTO> detalhes = faturaService.obterDetalhesFatura(cartaoId, dtVencimento);

		model.addAttribute("dtVencimento", dtVencimento);
		model.addAttribute("detalhes", detalhes);
		return "/fatura/cadastrar";
	}

}
