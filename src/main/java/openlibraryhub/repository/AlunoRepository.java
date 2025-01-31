package openlibraryhub.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import openlibraryhub.entities.AlunoEntity;

@Repository
public interface AlunoRepository extends JpaRepository<AlunoEntity, Long> {
}
