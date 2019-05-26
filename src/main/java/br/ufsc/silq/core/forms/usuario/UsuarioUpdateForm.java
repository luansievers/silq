package br.ufsc.silq.core.forms.usuario;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Size;

/**
 * Utilizado para atualização das informações do usuário
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioUpdateForm {

	@NotBlank
	@Size(min = 5, max = 100)
	private String nome;

}
