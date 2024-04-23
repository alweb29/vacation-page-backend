package com.example.vacationpagebackend.entity;


import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

@Getter
@Setter

@Accessors(chain = true)
public class RoomRecordWithDaysWritten extends RoomRecord {
    private List<Day> reservedDaysFull;
    private RoomType roomType;
    private int monthNum;

    public RoomRecordWithDaysWritten(List<Day> reservedDaysFull, RoomType roomType, int monthNum) {
        this.reservedDaysFull = reservedDaysFull;
        this.roomType = roomType;
        this.monthNum = monthNum;
    }
}
