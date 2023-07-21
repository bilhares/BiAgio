package com.biagio.service;

import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.biagio.model.security.Perfil;
import com.biagio.model.security.Usuario;
import com.biagio.repository.UsuarioRepository;

import jakarta.mail.MessagingException;

import org.springframework.util.Base64Utils;

@Service
public class UsuarioService implements UserDetailsService {

	@Autowired
	private UsuarioRepository repository;

	@Autowired
	private EmailService emailService;

	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario usuario = buscarPorEmailEAtivo(username)
				.orElseThrow(() -> new UsernameNotFoundException("Usuario " + username + " não encontrado."));
		return new User(usuario.getEmail(), usuario.getSenha(),
				AuthorityUtils.createAuthorityList(getAtuthorities(usuario.getPerfis())));
	}

	@Transactional(readOnly = true)
	public Optional<Usuario> buscarPorEmailEAtivo(String email) {

		return repository.findByEmailAndAtivo(email);
	}

	private String[] getAtuthorities(List<Perfil> perfis) {
		String[] authorities = new String[perfis.size()];
		for (int i = 0; i < perfis.size(); i++) {
			authorities[i] = perfis.get(i).getDesc();
		}
		return authorities;
	}

	@Transactional(readOnly = false)
	public Usuario salvarUsuario(Usuario u) throws MessagingException {
		if (usuarioExiste(u.getEmail()))
			throw new DataIntegrityViolationException("Usuário já cadastrado na base");

		String verificador = RandomStringUtils.randomAlphanumeric(6);
		String crypt = new BCryptPasswordEncoder().encode(verificador);

		u.setSenha(crypt);
		u.setCodigoVerificador(verificador);

		Usuario usuarioSalvo = repository.save(u);
		emailDeConfirmacaoDeCadastro(usuarioSalvo.getEmail(), usuarioSalvo.getCodigoVerificador());

		return usuarioSalvo;
	}

	public boolean usuarioExiste(String email) {
		Optional<Usuario> usuarioEncontrado = buscarPorEmailEAtivo(email);
		return usuarioEncontrado.isPresent();
	}

	public void emailDeConfirmacaoDeCadastro(String email, String verificador) throws MessagingException {
		String codigo = Base64Utils.encodeToString(email.getBytes());
		emailService.enviarPedidoDeConfirmacaoDeCadastro(email, codigo, verificador);
	}

	@Transactional(readOnly = false)
	public void alterarSenha(Usuario usuario, String senha) {
		usuario.setSenha(new BCryptPasswordEncoder().encode(senha));
		repository.save(usuario);
	}
}
