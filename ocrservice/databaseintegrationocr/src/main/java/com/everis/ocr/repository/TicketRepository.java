package com.everis.ocr.repository;

import com.everis.ocr.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket, String>{
}
