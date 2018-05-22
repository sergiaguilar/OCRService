package com.everis.productservice;


import com.everis.autorizationauthentication.model.Products;
import com.everis.autorizationauthentication.model.Users;
import com.everis.businesslogic.interfaces.IProductControl;
import com.everis.tokenuser.TokenUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/secure/product")
public class ProductController {

    @Autowired
    private IProductControl iProductControl;

    private TokenUser tokenUser = new TokenUser();

    @GetMapping(value = "/ListProducts")
    public List listProducts() {
        return iProductControl.listProducts();
    }

    @PostMapping(value = "/newProduct")
    public String newProduct(@RequestBody Products products) {
        return iProductControl.newProduct(products);
    }

    @RequestMapping(value = "/{idProduct}")
    public String userCanUseProduct(@PathVariable String idProduct, HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        token = token.substring(7);
        Users users = tokenUser.getUser(token);
        if(iProductControl.userCanUseProduct(idProduct,users)) {
            return "User can use this product";
        }
        else return "User can not use this product";
    }


}
