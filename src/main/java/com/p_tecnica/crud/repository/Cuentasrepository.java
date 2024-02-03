package com.p_tecnica.crud.repository;

import com.p_tecnica.crud.model.CuentasEntity;
import com.p_tecnica.crud.model.TipoCuentaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface Cuentasrepository extends JpaRepository<CuentasEntity,Long> {
    Optional<CuentasEntity> findByNumeroPin(String numeroPing);
    @Query(value = "SELECT c.* FROM cuentas c WHERE c.cliente_id = :clienteId", nativeQuery = true)
    List<CuentasEntity> findByClienteId(@Param("clienteId") String clienteId);
}
