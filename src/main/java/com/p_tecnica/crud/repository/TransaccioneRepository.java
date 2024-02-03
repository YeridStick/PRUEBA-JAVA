package com.p_tecnica.crud.repository;

import com.p_tecnica.crud.model.TransaccionesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransaccioneRepository  extends JpaRepository<TransaccionesEntity, Long> {
}
