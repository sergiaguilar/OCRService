package com.everis.businesslogic.interfaces;

import com.everis.autorizationauthentication.model.Users;

import java.util.List;

public interface IUserControl {

    Users findUserByIdCompany(String id);

    boolean existsUser(String id);

}