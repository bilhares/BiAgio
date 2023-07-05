package com.biagio.service;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

import com.biagio.model.entity.Cartao;
import com.biagio.repository.CartaoRepository;

import jakarta.servlet.http.HttpServletRequest;

public class CartaoDataTableService {

	private String[] cols = { "id", "nome", "numeroFinal", "limite", "diaVencimento", "ativo" };

	public Map<String, Object> execute(CartaoRepository repository, HttpServletRequest request) {

		int start = Integer.parseInt(request.getParameter("start"));
		int length = Integer.parseInt(request.getParameter("length"));
		int draw = Integer.parseInt(request.getParameter("draw"));

		int current = currentPage(start, length);

		String column = columnName(request);
		Sort.Direction direction = orderBy(request);
		String search = searchBy(request);

		Pageable pageable = PageRequest.of(current, length, direction, column);

		Page<Cartao> page = queryBy(search, repository, pageable);

		Map<String, Object> json = new LinkedHashMap<String, Object>();
		json.put("draw", draw);
		json.put("recordsTotal", page.getTotalElements());
		json.put("recordsFiltered", page.getTotalElements());
		json.put("data", page.getContent());

		return json;
	}

	private Page<Cartao> queryBy(String search, CartaoRepository repository, Pageable pageable) {
		if (search.isEmpty())
			return repository.findAll(pageable);

		return repository.findByNome(search, pageable);
	}

	private String searchBy(HttpServletRequest request) {
		return request.getParameter("search[value]").isEmpty() ? "" : request.getParameter("search[value]");
	}

	private Direction orderBy(HttpServletRequest request) {
		Sort.Direction sort = Sort.Direction.ASC;

		String order = request.getParameter("order[0][dir]");

		if (order.equalsIgnoreCase("desc"))
			sort = Sort.Direction.DESC;

		return sort;
	}

	private String columnName(HttpServletRequest request) {
		int iCol = Integer.parseInt(request.getParameter("order[0][column]"));
		return cols[iCol];
	}

	private int currentPage(int start, int length) {
		return start / length;
	}
}
