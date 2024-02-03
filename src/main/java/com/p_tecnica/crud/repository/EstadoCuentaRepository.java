package com.p_tecnica.crud.repository;

import com.p_tecnica.crud.model.EstadosCuentaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EstadoCuentaRepository extends JpaRepository<EstadosCuentaEntity, Long> {
    @Query(value = "SELECT ec.* FROM estados_cuenta ec WHERE ec.nombre_estado  = :nomEstado", nativeQuery = true)
    Optional<EstadosCuentaEntity> findByNombreEstado(@Param("nomEstado") String nomEstado);
}
