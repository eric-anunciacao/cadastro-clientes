package io.platformbuilders.cliente.infra.jpa.repository;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import io.platformbuilders.cliente.infra.jpa.entity.ClienteJpa;

@Repository
public interface ClienteJpaRepository extends CrudRepository<ClienteJpa, String> {

	@Query("SELECT c FROM ClienteJpa c WHERE (:nome IS NULL OR c.nome LIKE %:nome%) AND (:rg IS NULL OR c.rg = :rg) "
			+ "AND (:cpf IS NULL OR c.cpf = :cpf) AND (:dataNascimento IS NULL OR c.dataNascimento = :dataNascimento) ")
	Page<ClienteJpa> consultarPor(@Param("nome") String nome, @Param("rg") String rg, @Param("cpf") String cpf,
			@Param("dataNascimento") LocalDate dataNascimento, Pageable paginacao);

	Optional<ClienteJpa> findByCpf(String cpf);

}
