package com.example.vacationpagebackend.entity;

import com.example.vacationpagebackend.mappers.ReservedDaysConverter;
import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Set;

@Entity
@Table(name = "room_record")
@Data
@Accessors(chain = true)
public class RoomRecordEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RoomType roomType;

    @Column(nullable = false)
    private int monthNum;

    @Convert(converter = ReservedDaysConverter.class)
    private Set<Integer> reservedDays;

    @Column(nullable = false)
    private String reservedBy;
}
