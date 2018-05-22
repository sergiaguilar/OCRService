package com.everis.autorizationauthentication.model;


import javax.persistence.*;
import java.util.Set;


/**
 * Modelo de la entidad perteneciente a la base de datos USERS
 */
@Entity
@Table(name = "Users")
public class Users{

    /**
     * Identificador de la entidad: ID Company
     */
    @Id
    @Column(name = "IDCompany")
    private String idCompany;


    /**
     * Nombre de usuario del identificador de la entidad
     */
    @Column(name = "Username")
    private String username;


    /**
     * Password del usuario entidad
     */
    @Column(name = "Password")
    private String password;


    /**
     * Relación de la entidad con la tabla productos. Cada usuario puede usar muchos productos o ninguno, al igual que cada producto puede ser usado por muchos usuarios o ninguno.
     */
    @ManyToMany(targetEntity = Products.class)
    private Set products;

    /**
     * Funcion creadora de la entidad. Los parámetros que pasamos a la función servirán para iniciar a las variables de la entidad.
     * @param idCompany
     * @param username
     * @param password
     * @param products
     */
    public Users(String idCompany, String username, String password, Set products) {
        this.idCompany = idCompany;
        this.username = username;
        this.password = password;
        this.products = products;
    }

    public Users(){

    }

    /**
     * Getters y Setter de las variables de dicha entidad.
     */

    public String getIdCompany() {
        return idCompany;
    }

    public void setIdCompany(String idCompany) {
        this.idCompany = idCompany;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set getProducts() {
        return products;
    }

    public void setProducts(Set products) {
        this.products = products;
    }
}
