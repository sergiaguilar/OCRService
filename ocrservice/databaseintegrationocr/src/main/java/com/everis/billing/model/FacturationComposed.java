package com.everis.billing.model;


import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.sql.Timestamp;


/**
 * Clase que utilizaremos para crear un identificador compuesto para la Entidad FACTURACION
 */
@Embeddable
public class FacturationComposed implements Serializable {

    /**
     * Timestamp relacionado con la facturacion
     */
    @Column(name = "Timestamp")
    Timestamp timestamp;

    /**
     * Identificador de la facturacion. Está compuesto por la concatenación del Identificador del Usuario y
     * el identificador de la Licencia.
     */
    @Column(name = "IdFacturation")
    String idFacturation;

    public FacturationComposed(){}

    /**
     * Funcion creadora de la clase. Los parámetros que pasamos a la función servirán para iniciar las variables
     * de la clase. idCompany y idLicense serán concatenadas dando lugar a un string idFacturacion.
     * @param timestamp
     * @param idCompany
     * @param idLicense
     */
    public FacturationComposed(Timestamp timestamp, String idCompany, String idLicense) {
        this.timestamp = timestamp;
        this.idFacturation = idCompany+idLicense;
    }

    /**
     * Getters y Setter de las variables de dicha clase.
     */

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public String getIdFacturation() {
        return idFacturation;
    }

    public void setIdFacturation(String idFacturation) {
        this.idFacturation = idFacturation;
    }
}
