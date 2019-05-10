package br.ufsc.silq.core.data;

import java.util.HashSet;
import java.util.Set;

import br.ufsc.silq.core.persistence.entities.Usuario;
import br.ufsc.silq.security.AuthoritiesConstants;
import lombok.Data;

/**
 * Utilizado para retornar informações do usuário atualmente logado para o
 * cliente
 */
@Data
public class UsuarioDTO {
	private String email;

	private String nome;

	private Set<String> authorities;

	public UsuarioDTO(Usuario usuario) {
		this.email = usuario.getEmail();
		this.nome = usuario.getNome();
		this.authorities = new HashSet<>();
		usuario.getAutoridades().stream().forEach(autoridade -> {
			this.authorities.add(autoridade.getNome());
		});

		// Todo usuário logado possui a autoridade "ROLE_USER"
		// Ao invés de salvar esta informação no banco, adicionamos aqui
		this.authorities.add(AuthoritiesConstants.USER);
	}
}
