package com.jisu._01_ticket;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 판매하거나 교환해 줄 티켓의 목록 tickets과 판매 금액을 인스턴스 변수로 포함한다.
 */
public class TicketOffice {
    private Long amount;
    private List<Ticket> tickets = new ArrayList<>();

    public TicketOffice(Long amount, Ticket ... tickets) {
        this.amount = amount;
        this.tickets.addAll(Arrays.asList(tickets));
    }

    public void sellTicketTo(Audience audience) {
        plusAmount(audience.buy(getTicket()));
    }

    public Ticket getTicket() {
        return tickets.remove(0);
    }

    //판매금액을 차감한다.
    public void minusAmount(Long amount) {
        this.amount -= amount;
    }

    //판매금액을 더한다.
    public void plusAmount(Long amount) {
        this.amount += amount;
    }
}
