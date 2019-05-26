package br.ufsc.silq.core.forms;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

@Data
public class GrupoForm {

	private Long id;

	@NotBlank
	private String nomeGrupo;

	@NotBlank
	private String nomeInstituicao;

	@NotBlank
	private String nomeArea;

}
