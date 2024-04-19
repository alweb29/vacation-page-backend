package com.example.vacationpagebackend.mappers;

import com.example.vacationpagebackend.entity.RoomRecord;
import com.example.vacationpagebackend.entity.RoomRecordEntity;
import org.springframework.stereotype.Component;

@Component
public class RoomRecordMapperImpl implements RoomRecordMapper{
    @Override
    public RoomRecord mapRoomRecordEntityToRoomRecord(RoomRecordEntity roomRecordEntity) {
        return new RoomRecord()
                .setRoomType(roomRecordEntity.getRoomType())
                .setId(roomRecordEntity.getId())
                .setReservedBy(roomRecordEntity.getReservedBy())
                .setMonthNum(roomRecordEntity.getMonthNum())
                .setReservedDays(roomRecordEntity.getReservedDays());
    }

    @Override
    public RoomRecordEntity mapRoomRecordToRoomRecordEntity(RoomRecord roomRecord) {
        return new RoomRecordEntity()
                .setRoomType(roomRecord.getRoomType())
                .setId(roomRecord.getId())
                .setReservedBy(roomRecord.getReservedBy())
                .setMonthNum(roomRecord.getMonthNum())
                .setReservedDays(roomRecord.getReservedDays());
    }
}
