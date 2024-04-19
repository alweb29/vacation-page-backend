package com.example.vacationpagebackend.mappers;

import com.example.vacationpagebackend.entity.RoomRecord;
import com.example.vacationpagebackend.entity.RoomRecordEntity;
import org.mapstruct.Mapper;

@Mapper(
        componentModel = "spring"
)
public interface RoomRecordMapper {
    RoomRecord mapRoomRecordEntityToRoomRecord(RoomRecordEntity roomRecordEntity);

    RoomRecordEntity mapRoomRecordToRoomRecordEntity(RoomRecord roomRecord);
}
