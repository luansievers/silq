package br.ufsc.silq.web.rest;

import br.ufsc.silq.core.service.AuthService;
import br.ufsc.silq.security.xauth.Token;
import com.codahale.metrics.annotation.Timed;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

/**
 * Controlador de autenticação de usuários usuando um Token stateless
 */
@RestController
@RequestMapping("/api")
public class UserXAuthTokenController {

	@Inject
	private AuthService authService;

	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	@Timed
	public Token authorize(@RequestParam String login, @RequestParam String password) {
		return this.authService.authenticateAndLogin(login, password);
	}
}
