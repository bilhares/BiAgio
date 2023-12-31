package com.biagio.web.controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.biagio.model.dto.DetalheFaturaDTO;
import com.biagio.model.dto.FaturaDTO;
import com.biagio.model.entity.ControleEmprestimoParcela;
import com.biagio.model.entity.Endividado;
import com.biagio.model.entity.StatusParcela;
import com.biagio.repository.EndividadoRepository;
import com.biagio.service.FaturaService;
import com.biagio.util.FaturaUtils;

@Controller
@RequestMapping("faturas")
public class FaturaController {

	@Autowired
	FaturaService faturaService;

	@Autowired
	EndividadoRepository endividadoRepository;

	@GetMapping("/listar")
	public String listar(ModelMap model, @RequestParam("page") Optional<Integer> page,
			@RequestParam("size") Optional<Integer> size,
			@RequestParam(name = "endividado", required = false) Long endividado,
			@RequestParam(name = "status", required = false) StatusParcela status) {

		int currentPage = page.orElse(1);
		int pageSize = size.orElse(5);

		Pageable pageable = PageRequest.of(currentPage - 1, pageSize);

		List<StatusParcela> statusList = status == null ? List.of(StatusParcela.NAO_PAGO, StatusParcela.ATRASADO)
				: List.of(status);
		List<Long> endividados = endividado == null ? endividadoRepository.findAllIds() : List.of(endividado);

		Page<FaturaDTO> acutalPage = faturaService.obterFaturas(pageable, statusList, endividados);

		model.addAttribute("page", acutalPage);
		model.addAttribute("totalDeRegistros", acutalPage.getTotalElements());

		model.addAttribute("selectedEndividado", endividado);
		model.addAttribute("selectedStatus", status);

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

	@GetMapping("/pagamento/total/{id}/{dtVencimento}")
	public String efetuarPagamentoTotal(@PathVariable("id") Long cartaoId,
			@PathVariable("dtVencimento") LocalDate dtVencimento, RedirectAttributes attr) {

		try {
			faturaService.efetuarPagamentoTotal(cartaoId, dtVencimento);

			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
			String dtVencimentoFormatada = dtVencimento.format(formatter);

			attr.addFlashAttribute("sucesso",
					"Pagamento da fatura " + dtVencimentoFormatada + " realizado com sucesso!");
		} catch (Exception e) {
			attr.addFlashAttribute("erro", "Erro ao realizar pagamento " + e.getMessage());
			e.printStackTrace();
		}

		return "redirect:/faturas/listar";
	}

	@GetMapping("/pagamento/parcela/{id}")
	public String efetuarPagamentoTotal(@PathVariable("id") Long idParcela, RedirectAttributes attr) {

		try {
			ControleEmprestimoParcela parcela = faturaService.efetuarPagamentoParcela(idParcela);

			attr.addFlashAttribute("sucesso",
					"Pagamento da parcela " + parcela.getEmprestimo().getNome() + " realizado com sucesso!");

			return "redirect:/faturas/detalhes/" + parcela.getEmprestimo().getCartao().getId() + "/"
					+ parcela.getDataVencimento();
		} catch (Exception e) {
			attr.addFlashAttribute("erro", "Erro ao realizar pagamento " + e.getMessage());
			e.printStackTrace();
		}

		return "redirect:/faturas/listar";
	}

	@GetMapping("/pagamento/parcial/{id}/{dtVencimento}")
	public String efetuarPagamentoParcial(@PathVariable("id") Long cartaoId,
			@PathVariable("dtVencimento") LocalDate dtVencimento, @RequestParam("valor") String valor,
			RedirectAttributes attr) {

		try {
			BigDecimal valorPagamento = FaturaUtils.parseBigDecimal(valor);
			faturaService.efetuarPagamentoParcial(cartaoId, dtVencimento, valorPagamento);

			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
			String dtVencimentoFormatada = dtVencimento.format(formatter);

			attr.addFlashAttribute("sucesso",
					"Pagamento da fatura " + dtVencimentoFormatada + " realizado com sucesso!");
		} catch (Exception e) {
			attr.addFlashAttribute("erro", "Erro ao realizar pagamento " + e.getMessage());
			e.printStackTrace();
		}

		return "redirect:/faturas/listar";
	}

	@GetMapping("/limpar-filtros")
	public String clearFilters() {
		// Redirect to the original listing page without any filter parameters
		return "redirect:/faturas/listar";
	}

	@ModelAttribute("endividados")
	public List<Endividado> getEndividados() {
		return endividadoRepository.findByAtivo(true);
	}

	@ModelAttribute("listaStatus")
	public List<StatusParcela> getStatus() {
		return Arrays.asList(StatusParcela.values());
	}
}
