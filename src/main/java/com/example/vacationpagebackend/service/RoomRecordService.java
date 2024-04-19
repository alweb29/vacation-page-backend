package com.example.vacationpagebackend.service;

import com.example.vacationpagebackend.entity.RoomRecord;
import com.example.vacationpagebackend.repository.RoomRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoomRecordService {

    @Autowired
    private RoomRecordRepository roomRecordRepository;

    public RoomRecord addRoomRecord(RoomRecord roomRecord) {
        // Implement business logic if needed
        return roomRecordRepository.save(roomRecord);
    }

    // Implement other service methods for CRUD operations and business logic
}
