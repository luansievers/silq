package br.ufsc.silq.core.forms.usuario;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterForm {

	@NotBlank
	private String nome;

	@NotBlank
	private String senha;

	@NotBlank
	@Email
	private String email;
}
