package com.jisu._01_ticket;

/**
 * 이벤트 당첨자는 티켓으로 교환할 수 있는 초대장
 * 이벤트에 당첨되지 않은 관람객은 티켓을 구매할 수 있는 현금
 * 따라서 관람객이 가지고 올 수 있는 소지품은 초대장, 현금, 티켓 세가지이며
 * 소지품을 담을 수 있는 가방 객체이다.
 */
public class Bag {
    private Long amount;
    private Invitation invitation;
    private Ticket ticket;

    public Long hold(Ticket ticket) {
        if (hasInvitation()) {
            setTicket(ticket);
            return 0L;
        } else {
            setTicket(ticket);
            minusAmount(ticket.getFee());
            return ticket.getFee();
        }
    }

    public Bag(long amount) {
        this(null, amount);
    }

    // 이벤트에 당첨된 관람객은 가방에 현금과 초대장이 들어 있지만 이벤트에 당첨되지 않은 관람객의 가방안에는
    // 초대장이 들어 있지 않을 것이다. 따라서 Bag 인스턴스를 생성하는 시점에 제약을 강제하기 위해 생성자를 추가함.
    public Bag(Invitation invitation, long amount) {
        this.invitation = invitation;
        this.amount = amount;
    }

    // 초대장 보유 여부 판단
    private boolean hasInvitation() {
        return invitation != null;
    }

    public boolean hasTicket() {
        return ticket != null;
    }

    // 초대장을 티켓으로 교환하는 메소드
    private void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    // 현금을 감소
    private void minusAmount(Long amount) {
        this.amount -= amount;
    }

    // 현금을 증가
    public void plusAmount(Long amount) {
        this.amount = amount;
    }
}
