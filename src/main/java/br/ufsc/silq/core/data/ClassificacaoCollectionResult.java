package br.ufsc.silq.core.data;

import br.ufsc.silq.core.forms.AvaliarForm;
import br.ufsc.silq.core.persistence.entities.CurriculumLattes;
import br.ufsc.silq.core.service.AvaliacaoService;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;

public class ClassificacaoCollectionResult {

    @Getter
	private final AvaliarForm form;

    @Getter
	private ArrayList<AvaliacaoResult> avaliacoes;

//    @Getter
//    private HashMap<CurriculumLattes, AvaliacaoStats> curriculumStats;

    public ClassificacaoCollectionResult(AvaliarForm form) {
        this.form = form;
        this.avaliacoes = new ArrayList<>();
    }

//    public void putCurriculumStat(CurriculumLattes curriculumLattes, AvaliacaoStats avaliacaoStats){
//        this.curriculumStats.put(curriculumLattes, avaliacaoStats);
//    }

    public void putAvaliacaoResult(AvaliacaoResult avaliacaoStats){
        this.avaliacoes.add(avaliacaoStats);
    }

}

