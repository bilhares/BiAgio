package com.biagio.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import com.biagio.model.dto.ExtratoCobrancaDTO;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private SpringTemplateEngine template;

	public void enviarPedidoDeConfirmacaoDeCadastro(String destino, String codigo, String verificador)
			throws MessagingException {
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
				"UTF-8");

		Context context = new Context();
		context.setVariable("titulo", "Bem vindo ao Sistema Bilhares Agiotagem");
		context.setVariable("texto", "Precisamos que confirme seu cadastro, se endivide clicando no link abaixo");
		context.setVariable("verificador", verificador);
		context.setVariable("linkConfirmacao", "http://localhost:8080/usuarios/confirmar/cadastro?codigo=" + codigo);

		String html = template.process("email/confirmacao", context);
		helper.setTo(destino);
		helper.setText(html, true);
		helper.setSubject("Confirmacao de Cadastro");
		helper.setFrom("nao-responder@biagio.com.br");

		helper.addInline("logo", new ClassPathResource("/static/image/biagio.png"));

		mailSender.send(message);
	}

	public void enviarPedidoRedefinicaoSenha(String destino, String verificador) throws MessagingException {
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
				"UTF-8");

		Context context = new Context();
		context.setVariable("titulo", "Redefinição de Senha");
		context.setVariable("texto",
				"Para redefinir sua senha use o código de verficação " + "quando exigido no formulário.");
		context.setVariable("verificador", verificador);

		String html = template.process("email/confirmacao", context);
		helper.setTo(destino);
		helper.setText(html, true);
		helper.setSubject("Redefinição de Senha");
		helper.setFrom("nao-responder@biagio.com.br");

		helper.addInline("logo", new ClassPathResource("/static/image/biagio.png"));

		mailSender.send(message);
	}

	public void enviarExtratoDeCobranca(ExtratoCobrancaDTO dto, String destino) throws MessagingException {
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
				"UTF-8");

		Context context = new Context();
		context.setVariable("titulo", "Extrato de cobrança");
		context.setVariable("texto", "Olá, este é o seu extrato BILHARES AGIOTAGEM");
		context.setVariable("extrato", dto);

		String html = template.process("fatura/extrato", context);
		helper.setTo(destino);
		helper.setText(html, true);
		helper.setSubject("Extrato de cobrança");
		helper.setFrom("nao-responder@biagio.com.br");

		helper.addInline("logo", new ClassPathResource("/static/image/biagio.png"));

		mailSender.send(message);
	}
}
