package com.everis.businesslogic.interfaces;

import com.everis.autorizationauthentication.model.Products;
import com.everis.autorizationauthentication.model.Users;

import java.util.List;

public interface IProductControl {
    List listProducts();
    String newProduct(Products products);
    boolean userCanUseProduct(String idProduct, Users users);
}
