package com.jisu._02_movie;

/**
 * 할인 조건
 */
public interface DiscountCondition {
    boolean isSatisfiedBy(Screening screening);
}
