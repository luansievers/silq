package br.ufsc.silq.web.rest;

import br.ufsc.silq.core.forms.FeedbackEventoForm;
import br.ufsc.silq.core.forms.FeedbackPeriodicoForm;
import br.ufsc.silq.core.persistence.entities.FeedbackEvento;
import br.ufsc.silq.core.persistence.entities.FeedbackPeriodico;
import br.ufsc.silq.core.service.FeedbackService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/feedback")
@Slf4j
public class FeedbackResource {

	@Inject
	private FeedbackService feedbackService;

	@RequestMapping(value = "/evento/", method = RequestMethod.POST)
	public ResponseEntity<FeedbackEvento> sugerirMatchingEvento(@RequestBody @Valid FeedbackEventoForm form) {
		FeedbackEvento feedback = this.feedbackService.sugerirMatchingEvento(form);
		return new ResponseEntity<>(feedback, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/periodico/", method = RequestMethod.POST)
	public ResponseEntity<FeedbackPeriodico> sugerirMatchingPeriodico(@RequestBody @Valid FeedbackPeriodicoForm form) {
		FeedbackPeriodico feedback = this.feedbackService.sugerirMatchingPeriodico(form);
		return new ResponseEntity<>(feedback, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/evento/delete", method = RequestMethod.POST)
	public ResponseEntity<FeedbackPeriodico> deletarFeedbackEvento(@RequestBody @Valid FeedbackEventoForm form) {
		this.feedbackService.deleteFeedbackEvento(form);
		return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
	}

	@RequestMapping(value = "/periodico/delete", method = RequestMethod.POST)
	public ResponseEntity<FeedbackPeriodico> deletarFeedbackPeriodico(@RequestBody @Valid FeedbackPeriodicoForm form) {
		this.feedbackService.deleteFeedbackPeriodico(form);
		return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
	}
}
