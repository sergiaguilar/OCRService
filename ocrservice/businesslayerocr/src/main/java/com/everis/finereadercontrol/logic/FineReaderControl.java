package com.everis.finereadercontrol.logic;

import com.everis.finereadercontrol.interfaces.IFineReaderControl;
import com.everis.ocr.model.Ticket;
import com.everis.ocr.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.stereotype.Component;


@Component
@EntityScan({"com.everis.*"})
public class FineReaderControl implements IFineReaderControl {

    @Autowired
    private TicketRepository ticketRepository;

    public void newTicket(String id) {
        Ticket ticket = new Ticket(id, "In the queue","No error");
        ticketRepository.save(ticket);
    }

    public String getTicketInfo(String id) {
        if(ticketRepository.exists(id)) {
            Ticket ticket = ticketRepository.findOne(id);
            return ticket.getState();
        }
        else return "Ticket doesn't exists!";
    }

    public String getTicketError(String id) {
        Ticket ticket = ticketRepository.findOne(id);
        return ticket.getError();
    }
}
