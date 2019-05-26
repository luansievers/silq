package br.ufsc.silq.core.persistence.repository;

import br.ufsc.silq.core.persistence.entities.CurriculumLattes;
import br.ufsc.silq.core.persistence.entities.Grupo;
import br.ufsc.silq.core.persistence.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GrupoRepository extends JpaRepository<Grupo, Long> {

	List<Grupo> findAllByCoordenador(Usuario coordenador);

    List<Grupo> findAllByEspectadores(Usuario coordenador);

	Optional<Grupo> findOneByIdAndCoordenador(Long id, Usuario coordenador);

    Optional<Grupo> findOneByIdAndEspectadores(Long id, Usuario coordenador);

	/**
	 * Procura por todos os grupos que contenham o currículo.
	 * 
	 * @param curriculumPesquisador
	 * @return
	 */
	List<Grupo> findAllByPesquisadores(CurriculumLattes curriculumPesquisador);
}
