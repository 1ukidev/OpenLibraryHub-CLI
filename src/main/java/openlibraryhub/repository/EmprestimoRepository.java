package openlibraryhub.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import openlibraryhub.entities.EmprestimoEntity;

@Repository
public interface EmprestimoRepository extends JpaRepository<EmprestimoEntity, Long> {
}
