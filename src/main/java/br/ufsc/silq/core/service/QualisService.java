package br.ufsc.silq.core.service;

import br.ufsc.silq.core.data.SimilarityResult;
import br.ufsc.silq.core.forms.QualisSearchForm;
import br.ufsc.silq.core.persistence.entities.QualisEvento;
import br.ufsc.silq.core.persistence.entities.QualisPeriodico;
import br.ufsc.silq.core.persistence.repository.QualisEventoRepository;
import br.ufsc.silq.core.persistence.repository.QualisPeriodicoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.validation.Valid;
import java.util.ArrayList;

@Service
@Transactional
public class QualisService {

	@Inject
	private SimilarityService similarityService;

	@Inject
	private QualisPeriodicoRepository periodicoRepo;

	@Inject
	private QualisEventoRepository eventoRepo;

	/**
	 * Pesquisa por periódicos na base Qualis do sistema que sejam similares à query.
	 *
	 * @param form Form contendo a query e os filtros de busca.
	 * @param pageable Configuração de paginação.
	 * @return Uma página (sublista) dos resultados encontrados.
	 */
	public Page<SimilarityResult<QualisPeriodico>> searchPeriodicos(@Valid QualisSearchForm form, Pageable pageable) {
		if (!form.hasQuery()) {
			Page<QualisPeriodico> periodicos;

			if (form.hasArea()) {
				periodicos = this.periodicoRepo.findAllByAreaAvaliacaoIgnoreCase(form.getArea(), pageable);
			} else {
				periodicos = this.periodicoRepo.findAll(pageable);
			}

			return this.mapToSimilarityResult(pageable, periodicos);
		}

		return this.similarityService.searchQualis(QualisPeriodico.class, form, pageable);
	}

	/**
	 * Pesquisa por eventos na base Qualis do sistema que sejam similares à query.
	 *
	 * @param form Form contendo a query e os filtros de busca.
	 * @param pageable Configuração de paginação.
	 * @return Uma página (sublista) dos resultados encontrados.
	 */
	public Page<SimilarityResult<QualisEvento>> searchEventos(@Valid QualisSearchForm form, Pageable pageable) {
		if (!form.hasQuery()) {
			Page<QualisEvento> eventos;

			if (form.hasArea()) {
				eventos = this.eventoRepo.findAllByAreaAvaliacaoIgnoreCase(form.getArea(), pageable);
			} else {
				eventos = this.eventoRepo.findAll(pageable);
			}

			return this.mapToSimilarityResult(pageable, eventos);
		}

		return this.similarityService.searchQualis(QualisEvento.class, form, pageable);
	}

	private <T> Page<SimilarityResult<T>> mapToSimilarityResult(Pageable pageable, Page<T> periodicos) {
		ArrayList<SimilarityResult<T>> results = new ArrayList<>();
		periodicos.forEach(p -> results.add(new SimilarityResult<>(p, null)));
		return new PageImpl<>(results, pageable, periodicos.getTotalElements());
	}
}
