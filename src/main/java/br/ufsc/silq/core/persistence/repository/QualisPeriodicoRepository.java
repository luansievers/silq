package br.ufsc.silq.core.persistence.repository;

import br.ufsc.silq.core.persistence.entities.QualisPeriodico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import java.util.Optional;

public interface QualisPeriodicoRepository extends JpaRepository<QualisPeriodico, Long>, QueryDslPredicateExecutor<QualisPeriodico> {

	Optional<QualisPeriodico> findOneById(Long id);

	Page<QualisPeriodico> findAllByAreaAvaliacaoIgnoreCase(String area, Pageable pageable);

}
