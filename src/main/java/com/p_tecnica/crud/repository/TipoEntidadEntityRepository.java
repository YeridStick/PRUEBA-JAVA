package com.p_tecnica.crud.repository;

import com.p_tecnica.crud.model.TipoCuentaEntity;
import com.p_tecnica.crud.model.TipoEntidadEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TipoEntidadEntityRepository extends JpaRepository<TipoEntidadEntity,Long> {
    Optional<TipoEntidadEntity> findBySigla(String name);
}
