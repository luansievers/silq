package br.ufsc.silq.web.rest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.ufsc.silq.core.data.AvaliacaoResult;
import br.ufsc.silq.core.exception.SilqException;
import br.ufsc.silq.core.exception.SilqLattesException;
import br.ufsc.silq.core.forms.AvaliarForm;
import br.ufsc.silq.core.persistence.entities.CurriculumLattes;
import br.ufsc.silq.core.service.AvaliacaoService;
import br.ufsc.silq.core.service.CurriculumLattesService;
import br.ufsc.silq.core.service.UsuarioService;
import br.ufsc.silq.web.cache.AvaliacaoCache;
import br.ufsc.silq.web.cache.CurriculumCache;
import br.ufsc.silq.web.cache.CurriculumCache.Curriculum;
import br.ufsc.silq.web.rest.exception.HttpNotFound;
import br.ufsc.silq.web.rest.form.AvaliacaoLivreForm;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api")
@Slf4j
public class AvaliarResource {

	@Inject
	private UsuarioService usuarioService;

	@Inject
	private AvaliacaoService avaliacaoService;

	@Inject
	private CurriculumCache curriculumCache;

	@Inject
	private AvaliacaoCache avaliacaoCache;

	@Inject
	private CurriculumLattesService curriculumService;

	/**
	 * POST /api/avaliar/curriculum/{curriculumId} -> Avalia o currículo salvo com ID especificado,
	 * caso o usuário logado tenha permissão.
	 *
	 * @throws SilqLattesException
	 */
	@RequestMapping(value = "/avaliar/curriculum/{curriculumId}", method = RequestMethod.POST)
	public ResponseEntity<AvaliacaoResult> avaliarCurriculum(@PathVariable Long curriculumId, @Valid @RequestBody AvaliarForm avaliarForm) throws SilqLattesException {
		CurriculumLattes lattes = this.curriculumService.findOneWithPermission(curriculumId)
				.orElseThrow(() -> new HttpNotFound("Currículo não encontrado"));

		AvaliacaoResult result = this.avaliacaoService.avaliar(lattes.getXml(), avaliarForm);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	/**
	 * POST /api/avaliar/atual -> Avalia o currículo do usuário atual
	 *
	 * @throws SilqLattesException
	 */
	@RequestMapping(value = "/avaliar/atual", method = RequestMethod.POST)
	public ResponseEntity<AvaliacaoResult> avaliarAtual(@Valid @RequestBody AvaliarForm avaliarForm) throws SilqLattesException {
		CurriculumLattes lattes = this.usuarioService.getCurriculumUsuarioLogado();
		AvaliacaoResult result = this.avaliacaoService.avaliar(lattes.getXml(), avaliarForm);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	/**
	 * POST /api/avaliar/upload -> envia um currículo e salva-o em cache para
	 * ser posteriormente avaliado.
	 *
	 * @param file
	 *            Currículo Lattes em XML
	 * @param cacheId
	 *            ID do cache a ser utilizado para salvar o currículo. Pedidos
	 *            de avaliação (/api/avaliar) deverão informar o cacheId para
	 *            utilizar os currículos salvos no cache especificado.
	 */
	@RequestMapping(value = "/avaliar/upload", method = RequestMethod.POST)
	public ResponseEntity<?> upload(@RequestParam("file") MultipartFile file, @RequestParam("cacheId") String cacheId)
			throws IOException, SilqException {
		log.debug("Received file upload {}", file.getOriginalFilename());
		this.curriculumCache.insert(cacheId, file);
		return new ResponseEntity<>(this.curriculumCache.get(cacheId), HttpStatus.OK);
	}

	/**
	 * POST /api/avaliar/ -> avalia os currículos anteriormente enviados via
	 * 'api/avaliar/upload' (e salvos na cache) de acordo com as opções de
	 * avaliação informadas. O formulário de configuração deve conter o cacheId
	 * dos currículos a serem utilizados na avaliação. O resultado desta
	 * avaliação também é salvo na cache {@link AvaliacaoCache} para consultas
	 * posteriores.
	 */
	@RequestMapping(value = "/avaliar/", method = RequestMethod.POST)
	public ResponseEntity<List<AvaliacaoResult>> avaliar(@Valid @RequestBody AvaliacaoLivreForm avaliacaoForm)
			throws SilqLattesException {
		List<AvaliacaoResult> results = new ArrayList<>();

		for (Curriculum curriculum : this.curriculumCache.get(avaliacaoForm.getCacheId())) {
			AvaliacaoResult result = this.avaliacaoService.avaliar(curriculum.getDocument(), avaliacaoForm);
			results.add(result);
			this.avaliacaoCache.insert(avaliacaoForm.getCacheId(), result);
		}

		return new ResponseEntity<>(results, HttpStatus.OK);
	}

	/**
	 * GET /api/avaliar/result/{cacheId} -> Retorna os resultados de avaliação
	 * previamente realizada e salvas na cache com ID especificado. Limpa o item
	 * de {@link CurriculumCache} associado ao cacheId especificado.
	 */
	@RequestMapping(value = "/avaliar/result/{cacheId}", method = RequestMethod.GET)
	public ResponseEntity<List<AvaliacaoResult>> getResult(@PathVariable String cacheId) {
		this.curriculumCache.clear(cacheId);
		return new ResponseEntity<>(this.avaliacaoCache.get(cacheId), HttpStatus.OK);
	}
}
