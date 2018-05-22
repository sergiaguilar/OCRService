package com.everis.autorizationauthentication.repository;

import com.everis.autorizationauthentication.model.Products;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 * Repositorio asociado a la entidad Products
 */
public interface ProductsRepository extends JpaRepository<Products, String> {
}
