package com.example.vacationpagebackend.service;

import com.example.vacationpagebackend.entity.RoomRecord;
import com.example.vacationpagebackend.entity.RoomRecordEntity;
import com.example.vacationpagebackend.entity.RoomType;
import com.example.vacationpagebackend.mappers.RoomRecordMapper;
import com.example.vacationpagebackend.repository.RoomRecordRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.example.vacationpagebackend.service.ContextExceptionFactory.roomRecordNotFound;

@Service
@Validated
@RequiredArgsConstructor
public class RoomRecordService {

    private final RoomRecordRepository roomRecordRepository;
    private final RoomRecordMapper roomRecordMapper;

    public RoomRecord getByRoomType(RoomType roomType) {
        return (roomRecordRepository.findRoomRecordByRoomType(roomType)
                .map(roomRecordMapper::mapRoomRecordEntityToRoomRecord)
                .orElseThrow());
    }

    public RoomRecord getByMonthNum(int monthNum) {
        return (roomRecordRepository.findRoomRecordByMonthNum(monthNum)
                .map(roomRecordMapper::mapRoomRecordEntityToRoomRecord)
                .orElseThrow(() -> roomRecordNotFound(monthNum)));
    }

    public RoomRecord getByMonthNumAndRoomType(int monthNum, RoomType roomType){
        return roomRecordRepository.findRoomRecordEntityByMonthNumAndRoomType(monthNum, roomType)
                .map(roomRecordMapper::mapRoomRecordEntityToRoomRecord)
                .orElseThrow(() -> roomRecordNotFound(monthNum));
    }
    public RoomRecord createRoomRecord(RoomRecord roomRecord) {
        return roomRecordMapper.mapRoomRecordEntityToRoomRecord(roomRecordRepository.save(
                roomRecordMapper.mapRoomRecordToRoomRecordEntity(
                        fillEmptyDays(roomRecord))));
    }

    public RoomRecord updateRoomRecord(Long id, RoomRecord roomRecord) {
        return roomRecordRepository.findById(id)
                .map(roomRecordEntity -> roomRecordMapper.mapRoomRecordToRoomRecordEntity(roomRecord))
                .map(roomRecordRepository::save)
                .map(roomRecordMapper::mapRoomRecordEntityToRoomRecord)
                .map(this::fillEmptyDays)
                .orElseThrow(() -> roomRecordNotFound(id));
    }

    public void deleteRoomRecord(Long id) {
        roomRecordRepository.deleteById(id);
    }

    public List<RoomRecord> getAllRoomRecords() {
        return roomRecordRepository.findAll().stream()
                .map(roomRecordMapper::mapRoomRecordEntityToRoomRecord)
                .collect(Collectors.toList());
    }

    public RoomRecord fillEmptyDays(RoomRecord roomRecord) {
        Set<Integer> reservedDays = roomRecord.getReservedDays();
        int max = Collections.max(reservedDays), min = Collections.min(reservedDays);

        for (int i = min; i < max; i++) {
            reservedDays.add(i);
        }
        roomRecord.setReservedDays(reservedDays);
        return roomRecord;
    }
}
