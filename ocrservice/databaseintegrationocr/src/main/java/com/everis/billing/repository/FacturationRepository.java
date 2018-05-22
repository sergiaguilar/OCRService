package com.everis.billing.repository;

import com.everis.billing.model.Facturation;
import com.everis.billing.model.FacturationComposed;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repositorio asociado a la entidad Facturation
 */
public interface FacturationRepository extends JpaRepository<Facturation, FacturationComposed> {
}
