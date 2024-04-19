package com.example.vacationpagebackend.entity;

import lombok.Data;


import java.util.Set;

@Data
public class RoomRecord {
    private Long id;

    private RoomType roomType;

    private int monthNum;

    private Set<Integer> reservedDays;

    private String reservedBy;
}
