package com.jisu._01_ticket;

/**
 * 판매원
 * 매표소에서 초대장을 티켓으로 교환하거나 티켓을 판매하는 역할을 수행한다.
 * 판매원은 자신이 일한느 매표소(ticketOffice)를 알고 있어야 한다.
 */
public class TicketSeller {
    private TicketOffice ticketOffice;

    public TicketSeller(TicketOffice ticketOffice) {
        this.ticketOffice = ticketOffice;
    }

    public void sellTo(Audience audience) {
        ticketOffice.sellTicketTo(audience);
    }
}
