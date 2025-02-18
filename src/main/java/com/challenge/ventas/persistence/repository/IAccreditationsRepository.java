package com.challenge.ventas.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.challenge.ventas.persistence.model.SaleAccreditation;

public interface IAccreditationsRepository extends JpaRepository<SaleAccreditation, Long> {

}
