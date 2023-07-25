package com.biagio.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.biagio.model.dto.EmprestimoDTO;
import com.biagio.model.dto.ExtratoCobrancaDTO;
import com.biagio.model.dto.FaturaDTO;
import com.biagio.model.dto.ResumoEndividadosDTO;
import com.biagio.repository.EndividadoRepository;
import com.biagio.repository.FaturaRepository;

import jakarta.mail.MessagingException;

@Service
public class AdminService {

	@Autowired
	FaturaRepository faturaRepository;

	@Autowired
	EndividadoRepository endividadoRepository;

	@Autowired
	EmailService emailService;

	@Autowired
	UsuarioService usuarioService;

	public List<ResumoEndividadosDTO> obterResumoDosEndividados() {
		return faturaRepository.obterResumoDosEndividados();
	}

	public void gerarExtratoCobranca(Long endividadoId) {
		try {
			List<EmprestimoDTO> compras = faturaRepository.obterEmprestimosDeUmEndividado(endividadoId);
			List<FaturaDTO> faturas = faturaRepository.obterFaturasDeUmEndividado(endividadoId);

			ResumoEndividadosDTO endividado = obterResumoDosEndividados().stream()
					.filter(dto -> dto.getId() == endividadoId).findFirst().orElse(new ResumoEndividadosDTO());

			ExtratoCobrancaDTO extrato = new ExtratoCobrancaDTO(endividadoId, endividado.getNome(),
					endividado.getTotalDivida(), compras, faturas);

			emailService.enviarExtratoDeCobranca(extrato, "felipe.bilhares@gmail.com");

		} catch (MessagingException e) {
			System.err.println("Erro ao enviar email " + e.getMessage());
		} catch (Exception e) {
			System.out.println("Erro inesperado " + e.getMessage());
		}
	}
}
