package com.example.vacationpagebackend.controller;
import com.example.vacationpagebackend.entity.RoomRecord;
import com.example.vacationpagebackend.service.RoomRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/room-records")
public class RoomRecordController {

    @Autowired
    private RoomRecordService roomRecordService;

    @PostMapping
    public ResponseEntity<RoomRecord> addRoomRecord(@RequestBody RoomRecord roomRecord) {
        RoomRecord savedRoomRecord = roomRecordService.addRoomRecord(roomRecord);
        return ResponseEntity.ok(savedRoomRecord);
    }

    // Implement other CRUD endpoints (GET, PUT, DELETE) as needed
}
