package com.p_tecnica.crud.repository;

import com.p_tecnica.crud.model.TipoEntidadEntity;
import com.p_tecnica.crud.model.TipoTransccionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface TipoTransaccionesRepository extends JpaRepository<TipoTransccionEntity, Long> {
    Optional<TipoTransccionEntity> findByNombreTransaccion(String name);
}
