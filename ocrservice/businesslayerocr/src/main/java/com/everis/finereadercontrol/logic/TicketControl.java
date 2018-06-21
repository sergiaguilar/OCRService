package com.everis.finereadercontrol.logic;

import com.everis.finereadercontrol.interfaces.ITicketControl;
import com.everis.ocr.model.Ticket;
import com.everis.ocr.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.stereotype.Component;


@Component
@EntityScan({"com.everis.*"})
public class TicketControl implements ITicketControl {

    @Autowired
    private TicketRepository ticketRepository;

    public void newTicket(String id) {
        Ticket ticket = new Ticket(id, "In the queue","No error");
        ticketRepository.save(ticket);
    }

    public void setDownloaded(String id) {
        Ticket ticket = new Ticket(id, "Downloaded","No error");
        ticketRepository.save(ticket);
    }

    public String getTicketInfo(String id) {
        if(ticketRepository.existsById(id)) {
            Ticket ticket = ticketRepository.findById(id).get();
            return ticket.getState();
        }
        else return "Ticket doesn't exists!";
    }

    public String getTicketError(String id) {
        Ticket ticket = ticketRepository.findById(id).get();
        return ticket.getError();
    }
}
