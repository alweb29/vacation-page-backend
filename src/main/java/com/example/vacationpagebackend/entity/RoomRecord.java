package com.example.vacationpagebackend.entity;

import com.example.vacationpagebackend.mappers.RoomRecordJsonDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;


import java.util.Set;

@Data
@Accessors(chain = true)
@JsonDeserialize(using = RoomRecordJsonDeserializer.class)
public class RoomRecord {
    private Long id;

    private RoomType roomType;

    private int monthNum;

    private Set<Integer> reservedDays;

    private String reservedBy;
}
