package com.everis.ocr.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Ticket")
public class Ticket {

    @Id
    @Column(name = "Id_Ticket")
    private String id_ticket;

    @Column(name ="State")
    private String state;

    @Column (name = "Error")
    private String error;

    public Ticket() {}

    public Ticket(String id_ticket, String state, String error) {
        this.id_ticket = id_ticket;
        this.state = state;
        this.error = error;
    }

    public String getId_ticket() {
        return id_ticket;
    }

    public void setId_ticket(String id_ticket) {
        this.id_ticket = id_ticket;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
