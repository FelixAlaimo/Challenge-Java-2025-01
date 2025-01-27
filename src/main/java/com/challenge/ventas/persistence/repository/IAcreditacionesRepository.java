package com.challenge.ventas.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.challenge.ventas.persistence.model.Acreditacion;

public interface IAcreditacionesRepository extends JpaRepository<Acreditacion, Long> {

}
