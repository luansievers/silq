package br.ufsc.silq.core.parser.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Date;

@Data
@EqualsAndHashCode(of = { "idCurriculo", "ultimaAtualizacao" })
@ToString(of = { "nome", "idCurriculo" })
public class DadosGeraisResult {
	private String nome;
	private String idCurriculo;
	private AreaConhecimento areaConhecimento = new AreaConhecimento();
	private Date ultimaAtualizacao;
}
