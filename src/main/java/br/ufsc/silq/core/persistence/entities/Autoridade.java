package br.ufsc.silq.core.persistence.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
@Table(name = "rl_autoridade_usuario")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Autoridade implements Serializable {
	private static final long serialVersionUID = 26495563697583630L;

	@Id
	@NotNull
	@Column(name = "no_autoridade", length = 50)
	@Size(min = 0, max = 50)
	private String nome;

	@Id
	@NotNull
	@Column(name = "co_usuario", length = 19)
	private Long usuarioId;
}
