package com.p_tecnica.crud.repository;

import com.p_tecnica.crud.model.TipoCuentaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;
@Repository
public interface TipoCuentaRepository extends JpaRepository<TipoCuentaEntity,Long> {
    @Query(value = "SELECT tc.* FROM tipo_cuenta tc WHERE tc.nombre_cuenta  = :nomCuenta", nativeQuery = true)
    Optional<TipoCuentaEntity> findByNombreCuenta(@Param("nomCuenta") String nomCuenta);
}
