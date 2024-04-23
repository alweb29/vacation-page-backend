package com.example.vacationpagebackend.service;

import com.example.vacationpagebackend.entity.*;
import com.example.vacationpagebackend.mappers.RoomRecordMapper;
import com.example.vacationpagebackend.repository.RoomRecordRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.lang.reflect.Array;
import java.time.Year;
import java.time.YearMonth;
import java.util.*;
import java.util.stream.Collectors;

import static com.example.vacationpagebackend.service.ContextExceptionFactory.roomRecordNotFound;

@Service
@Validated
@RequiredArgsConstructor
public class RoomRecordService {

    private final RoomRecordRepository roomRecordRepository;
    private final RoomRecordMapper roomRecordMapper;

    public RoomRecordWithDaysWritten getRoomRecordsForMonthAndType(int monthNum, RoomType roomType) {
        Optional<List<RoomRecordEntity>> roomRecordEntitiesOptional = roomRecordRepository.findRoomRecordEntitiesByMonthNumAndRoomType(monthNum, roomType);

        if (roomRecordEntitiesOptional.isPresent()) {
            List<RoomRecordEntity> roomRecordEntities = roomRecordEntitiesOptional.get();
            List<Day> reservedDaysList = new ArrayList<>();
            Set<Integer> reservedNum = new HashSet<>();
            for (RoomRecordEntity roomRecordEntity : roomRecordEntities) {
                RoomRecord roomRecord = roomRecordMapper.mapRoomRecordEntityToRoomRecord(roomRecordEntity);
                reservedNum.addAll(roomRecord.getReservedDays());
            }

            YearMonth yearMonth = YearMonth.of(Year.now().getValue(), monthNum);
            int lengthOfMonth = yearMonth.lengthOfMonth();

            for (int i = 1; i <= lengthOfMonth; i++) {
                if (!reservedNum.contains(i)){
                    reservedDaysList.add(new Day(i, false));
                }else {
                    reservedDaysList.add(new Day(i, true));
                }
            }
            return new RoomRecordWithDaysWritten(reservedDaysList, roomType, monthNum);
        } else {
            throw roomRecordNotFound(monthNum);
        }
    }

    public List<RoomRecord> getRoomRecordsAsListByMonthAndType(int monthNum, RoomType roomType) {
        List<RoomRecordEntity> roomRecordEntities = roomRecordRepository.findRoomRecordEntitiesByMonthNumAndRoomType(monthNum, roomType)
                .orElseThrow(() -> roomRecordNotFound(monthNum));

        return roomRecordEntities.stream()
                .map(roomRecordMapper::mapRoomRecordEntityToRoomRecord)
                .collect(Collectors.toList());
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
