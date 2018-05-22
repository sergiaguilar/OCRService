package com.everis.businesslogic.userlogic;

import com.everis.autorizationauthentication.model.Products;
import com.everis.autorizationauthentication.model.Users;
import com.everis.autorizationauthentication.repository.ProductsRepository;
import com.everis.autorizationauthentication.repository.UsersRepository;
import com.everis.businesslogic.interfaces.IProductControl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@EntityScan({"com.everis.*"})
public class ProductControl implements IProductControl {

    @Autowired
    private ProductsRepository productsRepository;

    @Autowired
    private UsersRepository usersRepository;

    public List listProducts() {
        return productsRepository.findAll();
    }

    public String newProduct(Products products) {
        productsRepository.save(products);
        return "Producto " + products.getName() + " guardado!";
    }

    public boolean userCanUseProduct(String idProduct, Users users) {
        Users aux = usersRepository.findOne(users.getIdCompany());
        Products products = productsRepository.findOne(idProduct);
        return aux.getProducts().contains(products);
    }
}
