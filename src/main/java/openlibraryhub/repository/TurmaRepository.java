package openlibraryhub.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import openlibraryhub.entities.TurmaEntity;

@Repository
public interface TurmaRepository extends JpaRepository<TurmaEntity, Long> {}
