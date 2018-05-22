package com.everis.finereadercontrol.interfaces;

public interface IFineReaderControl {
    void newTicket(String id);
    String getTicketInfo(String id);
    String getTicketError(String id);
}
