package com.jisu._02_movie;

import java.time.Duration;

/**
 * 영화
 */
public class Movie {
    private String title;
    private Duration runningTime;
    private Money fee;
    private DiscountPolicy discountPolicy;

    public Movie(String title, Duration runningTime, Money fee, DiscountPolicy discountPolicy) {
        this.title = title;
        this.runningTime = runningTime;
        this.fee = fee;
        this.discountPolicy = discountPolicy;
    }

    public Money getFee() {
        return fee;
    }

    /**
     * 메시지와 메서드는 다른 개념이다.
     * Movie는 DiscountPolicy의 인스턴스에게 calculateDiscountAmount 메시지를 전송한다.
     * 그렇다면 실행되는 메서드는 Movie와 상호작용하기 위해 연결된 객체의 클래스가 무엇인가에 따라 달라진다.
     * Movie와 협력하는 객체가 AmountDiscountPolicy라면 AmountDiscountPolicy 객체가 오버라이딩한 메서드가 실행된다.
     * PercenDiscountPolicy의 인슽턴스가 연결된 경우라면 PercenDiscountPolicy가 오버라이딩한 메서드가 실행된다.
     * 이렇게 실행시점에 실제로 협력되는 객체에 따라 다르게 실핸된다. 이를 다형성이라 한다.
     * 다형성은 객체지향프로그램에서 컴파일 시간 의존성과 실행 시간 의존성이 다를 수 있다는 사실을 기반으로 한다.
     */
    public Money calculateMovieFee(Screening screening) {
        return fee.minus(discountPolicy.calculateDiscountAmount(screening));
    }

    public void changeDiscountPolicy(DiscountPolicy discountPolicy) {
        this.discountPolicy = discountPolicy;
    }
}
