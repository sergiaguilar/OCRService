package com.everis.finereadercontrol.interfaces;

public interface ITicketControl {
    void newTicket(String id);
    void setDownloaded(String id);
    String getTicketInfo(String id);
    String getTicketError(String id);
}
