package com.everis.billing.model;

import com.everis.autorizationauthentication.model.Products;

import javax.persistence.*;


/**
 * Modelo de la entidad perteneciente a la base de datos LICENSE
 */
@Entity
@Table(name = "License")
public class License {

    /**
     * String identificador de la entidad.
     */
    @Id
    @Column(name = "IDLicense")
    private String idLicense;

    /**
     * Identificador del Usuario asociado a dicha Licencia. Una licencia tan solo puede ir asociada a un usuario.
     * Mientras que un usuario puede tener varias Licencias asociadas.
     */
    @OneToOne
    @JoinColumn(name = "IDProduct")
    private Products idProduct;

    /**
     * Entero que nos muestra el volumen máximo de dicha licencia. Este volumen no puede ser superado por la variable
     * Actual_Volume.
     */
    @Column(name = "Volume")
    private Integer volume;

    /**
     * String que referencia al Serial de la licencia.
     */
    @Column(name = "Serial")
    private String serial;

    /**
     * Volumen actual consumido por dicha licencia. No podrá superar nunca a la variable Volumen.
     */
    @Column(name = "Actual_Volume")
    private Integer actualVolume;

    public License() {}


    /**
     * Funcion creadora de la entidad. Los parámetros que pasamos a la función servirán para iniciar a las variables
     * de la entidad.
     * @param idLicense
     * @param idProduct
     * @param volume
     * @param serial
     * @param actualVolume
     */
    public License(String idLicense, Products idProduct, Integer volume, String serial, Integer actualVolume) {
        this.idLicense = idLicense;
        this.idProduct = idProduct;
        this.volume = volume;
        this.serial = serial;
        this.actualVolume = actualVolume;
    }


    /**
     * Getters y Setter de las variables de dicha entidad.
     */

    public String getIdLicense() {
        return idLicense;
    }

    public void setIdLicense(String idLicense) {
        this.idLicense = idLicense;
    }

    public Products getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(Products idProduct) {
        this.idProduct = idProduct;
    }

    public Integer getVolume() {
        return volume;
    }

    public void setVolume(Integer volume) {
        this.volume = volume;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public Integer getActualVolume() {
        return actualVolume;
    }

    public void setActualVolume(Integer actualVolume) {
        this.actualVolume = actualVolume;
    }

}
