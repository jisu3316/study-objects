package com.jisu._02_movie;

/**
 * 비율 할인 정책
 * 고정 금액이 아닌 일정 비율을 차감한다는 것이다. 할인 비율은 인스턴스 변수인 percent에 저장한다.
 */
public class PercentDiscountPolicy extends DefaultDiscountPolicy {
    private double percent;

    public PercentDiscountPolicy(double percent, DiscountCondition... conditions) {
        super(conditions);
        this.percent = percent;
    }

    @Override
    protected Money getDiscountAmount(Screening screening) {
        return screening.getMovieFee().times(percent);
    }
}
