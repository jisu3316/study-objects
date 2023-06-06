package com.jisu._04_movie_data_system;

import com.jisu._02_movie.Money;

import java.time.LocalDateTime;

public class Screening {
    private Movie movie;
    private int sequence;
    private LocalDateTime whenScreened;

    public Screening(Movie movie, int sequence, LocalDateTime whenScreened) {
        this.movie = movie;
        this.sequence = sequence;
        this.whenScreened = whenScreened;
    }

    public Money calculateFee(int audienceCount) {
        switch (movie.getMovieType()) {
            case AMOUNT_DISCOUNT -> {
                if (movie.isDiscountable(whenScreened, sequence)) {
                    return movie.getFee().minus(movie.getDiscountAmount());
                }
            }
            case PERCENT_DISCOUNT -> {
                if (movie.isDiscountable(whenScreened, sequence)) {
                    return movie.getFee().times(1.0 - movie.getDiscountPercent());
                }
            }
            case NONE_DISCOUNT -> {
                return movie.getFee();
            }
        }
        return movie.getFee();
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }

    public LocalDateTime getWhenScreened() {
        return whenScreened;
    }

    public void setWhenScreened(LocalDateTime whenScreened) {
        this.whenScreened = whenScreened;
    }
}
