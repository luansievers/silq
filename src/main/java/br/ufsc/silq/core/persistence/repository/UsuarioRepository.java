package br.ufsc.silq.core.persistence.repository;

import br.ufsc.silq.core.persistence.entities.CurriculumLattes;
import br.ufsc.silq.core.persistence.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

	Optional<Usuario> findOneByEmail(String email);

    Optional<Usuario> findOneById(Long id);

	Optional<Usuario> findOneByEmailAndAtivoTrue(String email);

	Optional<Usuario> findOneByRegisterKey(String registerKey);

	Optional<Usuario> findOneByResetKey(String resetKey);

	List<Usuario> findAllByCurriculum(CurriculumLattes curriculum);

}
