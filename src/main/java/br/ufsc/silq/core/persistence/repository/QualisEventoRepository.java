package br.ufsc.silq.core.persistence.repository;

import br.ufsc.silq.core.persistence.entities.QualisEvento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface QualisEventoRepository extends JpaRepository<QualisEvento, Long> {

	Optional<QualisEvento> findOneById(Long id);

	Page<QualisEvento> findAllByAreaAvaliacaoIgnoreCase(String area, Pageable pageable);

}
