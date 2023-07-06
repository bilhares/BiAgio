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

import com.biagio.model.entity.Endividado;
import com.biagio.repository.EndividadoRepository;

import jakarta.validation.Valid;

@Controller
@RequestMapping("endividados")
public class EndividadoController {

	@Autowired
	EndividadoRepository endividadoRepository;

	@GetMapping("/listar")
	public String listar(ModelMap model) {
		model.addAttribute("endividados", endividadoRepository.findAll());
		return "/endividado/listar";
	}

	@GetMapping("/cadastro")
	public String cadastro(Endividado entity) {
		return "/endividado/cadastrar";
	}

	@GetMapping("/editar/{id}")
	public String editar(@PathVariable("id") Long id, ModelMap model) {
		model.addAttribute("endividado", endividadoRepository.findById(id).get());
		return "/endividado/cadastrar";
	}

	@PostMapping("/cadastro/salvar")
	public String salvar(@Valid Endividado entity, BindingResult result, RedirectAttributes attr) {

		if (result.hasErrors()) {
			return "/endividado/cadastrar";
		}

		entity.setAtivo(true);
		endividadoRepository.save(entity);
		attr.addFlashAttribute("sucesso", "Endividado cadastrado com sucesso!");

		return "redirect:/endividados/cadastro";
	}

	@PostMapping("/cadastro/editar")
	public String salvarEdicao(@Valid Endividado entity, BindingResult result, RedirectAttributes attr) {

		if (result.hasErrors()) {
			return "/endividado/cadastrar";
		}
		entity.setAtivo(true);
		endividadoRepository.save(entity);
		attr.addFlashAttribute("sucesso", "Endividado atualizado com sucesso!");

		return "redirect:/endividados/cadastro";
	}

	@GetMapping("/inativar/{id}")
	public String inativar(@PathVariable("id") Long id, RedirectAttributes attr) {

		Optional<Endividado> opt = endividadoRepository.findById(id);
		if (opt.isEmpty())
			attr.addFlashAttribute("falha", "Endividado não encontrado");

		Endividado entity = opt.get();
		entity.setAtivo(false);

		endividadoRepository.save(entity);

		return "redirect:/endividados/listar";
	}

	@GetMapping("/ativar/{id}")
	public String ativar(@PathVariable("id") Long id, RedirectAttributes attr) {

		Optional<Endividado> opt = endividadoRepository.findById(id);
		if (opt.isEmpty())
			attr.addFlashAttribute("falha", "Endividado não encontrado");

		Endividado entity = opt.get();
		entity.setAtivo(true);

		endividadoRepository.save(entity);

		return "redirect:/endividados/listar";
	}

}
