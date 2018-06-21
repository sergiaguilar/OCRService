package com.everis.businesslogic.userlogic;

import com.everis.autorizationauthentication.model.Users;
import com.everis.autorizationauthentication.repository.ProductsRepository;
import com.everis.autorizationauthentication.repository.UsersRepository;
import com.everis.businesslogic.interfaces.IUserControl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.stereotype.Component;

@Component
@EntityScan({"com.everis.*"})
public class UserControl implements IUserControl{

    @Autowired
    private UsersRepository usersRepository;


    public boolean existsUser(String id) {
        return usersRepository.existsById(id);
    }

    public Users findUserByIdCompany(String id) {
        return usersRepository.findById(id).get();
    }

}
