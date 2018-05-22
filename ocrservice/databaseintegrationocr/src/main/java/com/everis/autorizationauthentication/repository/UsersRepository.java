package com.everis.autorizationauthentication.repository;

import com.everis.autorizationauthentication.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repositorio asociado a la entidad Users
 */
public interface UsersRepository extends JpaRepository<Users,String> {

}
