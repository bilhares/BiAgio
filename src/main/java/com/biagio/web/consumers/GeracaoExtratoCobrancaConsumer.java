package com.biagio.web.consumers;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import com.biagio.model.dto.amqp.ExtratoCobrancaConsumerDTO;
import com.biagio.service.AdminService;
import com.biagio.service.UsuarioService;

@Service
public class GeracaoExtratoCobrancaConsumer {

	@Autowired
	AdminService adminService;

	@Autowired
	UsuarioService usuarioService;

	@RabbitListener(queues = "${spring.rabbitmq.queue-cobranca}")
	public void consume(@Payload ExtratoCobrancaConsumerDTO dto) {
		try {

			System.out.println("Consuming " + dto.getEndividadoId());

			usuarioService.setUsuario(dto.getUsuario());
			adminService.gerarExtratoCobranca(dto.getEndividadoId());

			System.out.println("Consumed " + dto.getEndividadoId());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
