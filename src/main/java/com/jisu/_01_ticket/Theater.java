package com.jisu._01_ticket;

/**
 * 소극장
 * 관람객을 입장시킬수 있는 enter 메소드가 있다.
 */
public class Theater {

    private TicketSeller ticketSeller;

    public Theater(TicketSeller ticketSeller) {
        this.ticketSeller = ticketSeller;
    }

    public void enter(Audience audience) {
        ticketSeller.sellTo(audience);
    }
}
