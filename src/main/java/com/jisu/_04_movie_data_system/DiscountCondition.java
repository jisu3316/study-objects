package com.jisu._04_movie_data_system;

import java.time.DayOfWeek;
import java.time.LocalDate;

public class DiscountCondition {
    private DiscountConditionType type;

    private int sequence;

    private DayOfWeek dayOfWeek;
    private LocalDate startTime;
    private LocalDate endTime;

    public DiscountConditionType getType() {
        return type;
    }

    public boolean isDiscountable(DayOfWeek dayOfWeek, LocalDate time) {
        if(type != DiscountConditionType.PERIOD) {
            throw new IllegalArgumentException();
        }
        return this.dayOfWeek.equals(dayOfWeek) &&
                this.startTime.compareTo(time) <= 0 &&
                this.endTime.compareTo(time) >= 0;
    }

    public boolean isDiscountable(int sequence) {
        if(type != DiscountConditionType.SEQUENCE) {
            throw new IllegalArgumentException();
        }
        return this.sequence == sequence;
    }

    public void setType(DiscountConditionType type) {
        this.type = type;
    }

    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public LocalDate getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDate startTime) {
        this.startTime = startTime;
    }

    public LocalDate getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDate endTime) {
        this.endTime = endTime;
    }
}
