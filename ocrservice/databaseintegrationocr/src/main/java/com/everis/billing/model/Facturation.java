package com.everis.billing.model;


import com.everis.autorizationauthentication.model.Users;

import javax.persistence.*;

/**
 * Modelo de la entidad perteneciente a la base de datos FACTURATION
 */

@Entity
@Table(name = "Facturation")
public class Facturation {


    /**
     * Identificador de la entidad. Se trata de un identificador compuesto por dos variables, un TimeStamp y un String.
     */
    @EmbeddedId
    private FacturationComposed facturationComposed;

    /**
     * Número de páginas tratadas por el motor de reconocimiento en dicha ejecución.
     */
    @Column(name = "NumberPages")
    private Integer numberPages;

    /**
     * Identificador de la licencia asociada a dicha facturacion. Una facturacion tan solo puede ir asociada a una
     * Licencia. Mientras que una licencia puede tener varias Facturaciones asociadas.
     */
    @ManyToOne
    @JoinColumn(name = "IDLicense")
    private License license;

    /**
     * Identificador del Usuario asociado a dicha facturación. Una facturación tan solo puede ir asociada a un usuario.
     * Mientras que un usuario puede tener varias Facturacions asociadas.
     */
    @ManyToOne
    @JoinColumn(name = "IDCompany")
    private Users users;

    public Facturation() {}

    /**
     * Funcion creadora de la entidad. Los parámetros que pasamos a la función servirán para iniciar a las variables
     * de la entidad.
     * @param facturationComposed
     * @param numberPages
     * @param license
     * @param users
     */
    public Facturation(FacturationComposed facturationComposed, Integer numberPages, License license, Users users) {
        this.facturationComposed = facturationComposed;
        this.numberPages = numberPages;
        this.license = license;
        this.users = users;
    }

    /**
     * Getters y Setter de las variables de dicha entidad.
     */

    public FacturationComposed getFacturationComposed() {
        return facturationComposed;
    }

    public void setFacturationComposed(FacturationComposed facturationComposed) {
        this.facturationComposed = facturationComposed;
    }

    public Integer getNumberPages() {
        return numberPages;
    }

    public void setNumberPages(Integer numberPages) {
        this.numberPages = numberPages;
    }

    public License getLicense() {
        return license;
    }

    public void setLicense(License license) {
        this.license = license;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }
}
