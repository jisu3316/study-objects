package com.jisu._02_movie;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 할인 정책
 */
public interface DiscountPolicy {

    Money calculateDiscountAmount(Screening screening);
}
