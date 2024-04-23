package com.example.vacationpagebackend.entity;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Day {
    int dayNum;
    Boolean reserved;

    public Day(int dayNum, Boolean reserved) {
        this.dayNum = dayNum;
        this.reserved = reserved;
    }
}
