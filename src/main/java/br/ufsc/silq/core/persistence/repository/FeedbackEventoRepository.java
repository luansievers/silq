package br.ufsc.silq.core.persistence.repository;

import br.ufsc.silq.core.persistence.entities.FeedbackEvento;
import br.ufsc.silq.core.persistence.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FeedbackEventoRepository extends JpaRepository<FeedbackEvento, Long> {

	Optional<FeedbackEvento> findOneByQueryAndUsuarioAndValidation(String query, Usuario usuario, Boolean validation);

	Long deleteByQueryAndUsuario(String query, Usuario usuario);

	List<FeedbackEvento> findAllByUsuarioAndValidation(Usuario usuario, Boolean validation);
}
