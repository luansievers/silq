package br.ufsc.silq.core.persistence.entities;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.util.Date;

@Entity
@Inheritance
@DiscriminatorColumn(name = "co_tipo")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@SequenceGenerator(name = "Token_generator", sequenceName = "sq_feedback", allocationSize = 1, initialValue = 1)
@Table(name = "tb_feedback")
@Getter
@Setter
public abstract class Feedback {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Token_generator")
	@Column(name = "co_seq_feedback")
	private Long id;

	@Column(name = "ds_query")
	private String query;

	@Column(name = "nu_ano")
	private Integer ano;

	@ManyToOne
	@JoinColumn(name = "co_usuario")
	private Usuario usuario;

	@Column(name = "dt_feedback")
	private Date date;

	@Column(name = "st_validation")
	private Boolean validation = false;
}
