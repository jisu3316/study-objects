package com.jisu._04_movie_data_system;

import com.jisu._02_movie.Money;

import java.time.chrono.ChronoLocalDate;

public class ReservationAgency {
    public Reservation reserve(Screening screening, Customer customer, int audienceCount) {
        Money fee = screening.calculateFee(audienceCount);
        return new Reservation(customer, screening, fee, audienceCount);
//        Movie movie = screening.getMovie();
//
//        boolean discountable = false;
//        for(DiscountCondition condition : movie.getDiscountConditions()) {
//            if(condition.getType() == DiscountConditionType.PERIOD) {
//                discountable = screening.getWhenScreened().getDayOfWeek().equals(condition.getDayOfWeek()) &&
//                        condition.getStartTime().compareTo(ChronoLocalDate.from(screening.getWhenScreened().toLocalTime())) <= 0 &&
//                        condition.getEndTime().compareTo(ChronoLocalDate.from(screening.getWhenScreened().toLocalTime())) >= 0;
//            } else {
//                discountable = condition.getSequence() == screening.getSequence();
//            }
//
//            if(discountable) {
//                break;
//            }
//        }
//
//        Money fee;
//        if(discountable) {
//            Money discountAmount = Money.ZERO;
//            switch(movie.getMovieType()) {
//                case AMOUNT_DISCOUNT:
//                    discountAmount = movie.getDiscountAmount();
//                    break;
//                case PERCENT_DISCOUNT:
//                    discountAmount = movie.getFee().times(movie.getDiscountPercent());
//                    break;
//                case NONE_DISCOUNT:
//                    discountAmount = Money.ZERO;
//                    break;
//            }
//
//            fee = movie.getFee().minus(discountAmount).times(audienceCount);
//        } else {
//            fee = movie.getFee();
//        }
//
//        return new Reservation(customer, screening, fee, audienceCount);
    }
}
