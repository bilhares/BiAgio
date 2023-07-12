package com.biagio.web.validator;

import java.math.BigDecimal;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.biagio.model.entity.Emprestimo;

public class EmprestimoValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return Emprestimo.class.equals(clazz);
	}

	@Override
	public void validate(Object obj, Errors errors) {
		Emprestimo emprestimo = (Emprestimo) obj;

		if (emprestimo.getCartao() == null || emprestimo.getValorParcela() == null
				|| emprestimo.getValorTotal() == null)
			return;

		if (emprestimo.getCartao().getLimite().compareTo(emprestimo.getValorTotal()) < 0) {
			errors.rejectValue("valorParcela", "cartaoSemLimite.emprestimo");
		}

		if (emprestimo.getQtdParcelas() == 1
				&& emprestimo.getValorTotal().compareTo(emprestimo.getValorParcela()) != 0) {
			errors.rejectValue("valorParcela", "valorTotalEValorParcelaDivergentes.emprestimo");
		} else if (emprestimo.getQtdParcelas() > 1) {
			BigDecimal somaTotalParcelas = emprestimo.getValorParcela()
					.multiply(BigDecimal.valueOf(emprestimo.getQtdParcelas()));

			if (somaTotalParcelas.compareTo(emprestimo.getValorTotal()) != 0) {
				errors.rejectValue("valorParcela", "somaDasParcelasDivergente.emprestimo");
			}
		}

	}

}
