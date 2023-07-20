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

@Service
public class UsuarioService implements UserDetailsService {

	@Autowired
	private UsuarioRepository repository;

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

	public Usuario salvarUsuario(Usuario u) {
		if (usuarioExiste(u.getEmail()))
			throw new DataIntegrityViolationException("Usuário já cadastrado na base");

		String crypt = new BCryptPasswordEncoder().encode(u.getSenha());
		String verificador = RandomStringUtils.randomAlphanumeric(6);

		u.setSenha(crypt);
		u.setCodigoVerificador(verificador);

		return repository.save(u);
	}

	public boolean usuarioExiste(String email) {
		Optional<Usuario> usuarioEncontrado = buscarPorEmailEAtivo(email);
		return usuarioEncontrado.isPresent();
	}
}
