package br.ufsc.silq.core.persistence.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.ufsc.silq.core.persistence.entities.QualisEvento;

public interface QualisEventoRepository extends JpaRepository<QualisEvento, Long> {

	Optional<QualisEvento> findOneById(Long id);

	Page<QualisEvento> findAllByAreaAvaliacaoIgnoreCase(String area, Pageable pageable);

}
