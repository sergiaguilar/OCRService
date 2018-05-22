package com.everis.autorizationauthentication.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Modelo de la entidad perteneciente a la base de datos PRODUCTOS.
 */

@Entity
@Table(name = "Products")
public class Products {

    /**
     * String identificador de la clase producto: ID Producto
     */
    @Id
    @Column(name = "IDProduct")
    private String idProduct;


    /**
     * String para representar el nombre del producto
     */
    @Column(name = "Name")
    private String name;


    @Column(name = "Class")
    private String className;


    public Products(String idProduct, String name, String className) {
        this.idProduct = idProduct;
        this.name = name;
        this.className = className;
    }

    public Products() {
    }

    public String getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(String idProduct) {
        this.idProduct = idProduct;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }
}
