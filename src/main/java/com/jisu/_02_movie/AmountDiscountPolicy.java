package com.jisu._02_movie;

/**
 * 금액 할인 정책
 * 할인 조건이 만족할 경우 일정한 금액을 할인해주는 금액 할인 정책을 구현한다.
 */
public class AmountDiscountPolicy extends DefaultDiscountPolicy {
    private Money discountAmount;

    public AmountDiscountPolicy(Money discountAmount, DiscountCondition... conditions) {
        super(conditions);
        this.discountAmount = discountAmount;
    }

    @Override
    protected Money getDiscountAmount(Screening screening) {
        return discountAmount;
    }
}
