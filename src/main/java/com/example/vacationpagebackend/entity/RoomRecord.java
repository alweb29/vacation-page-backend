package com.example.vacationpagebackend.entity;

import lombok.Data;
import lombok.experimental.Accessors;


import java.util.Set;

@Data
@Accessors(chain = true)
public class RoomRecord {
    private Long id;

    private RoomType roomType;

    private int monthNum;

    private Set<Integer> reservedDays;

    private String reservedBy;
}
