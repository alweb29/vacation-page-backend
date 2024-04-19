package com.example.vacationpagebackend.controller;
import com.example.vacationpagebackend.entity.RoomRecord;
import com.example.vacationpagebackend.entity.RoomType;
import com.example.vacationpagebackend.service.RoomRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/room-records")
@RequiredArgsConstructor
public class RoomRecordController {

    private final RoomRecordService roomRecordService;

    @GetMapping
    public ResponseEntity<List<RoomRecord>> getAllRoomRecords() {
        List<RoomRecord> roomRecords = roomRecordService.getAllRoomRecords();
        return ResponseEntity.ok(roomRecords);
    }

    @GetMapping("/{monthNum}/{roomType}")
    public ResponseEntity<RoomRecord> getRoomRecord(@PathVariable int monthNum, @PathVariable int roomType) {
        RoomRecord roomRecord;
        if (monthNum == 0) {
            RoomType type = getRoomType(roomType);
            roomRecord = roomRecordService.getByMonthNumAndRoomType(monthNum, type);
        } else {
            roomRecord = roomRecordService.getByMonthNum(monthNum);
        }
        return ResponseEntity.ok(roomRecord);
    }


    @PostMapping
    public ResponseEntity<RoomRecord> createRoomRecord(@RequestBody RoomRecord roomRecord) {
        RoomRecord createdRoomRecord = roomRecordService.createRoomRecord(roomRecord);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdRoomRecord);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoomRecord> updateRoomRecord(@PathVariable Long id, @RequestBody RoomRecord roomRecord) {
        RoomRecord updatedRoomRecord = roomRecordService.updateRoomRecord(id, roomRecord);
        return ResponseEntity.ok(updatedRoomRecord);
    }

    @PutMapping("/{monthNum}/{roomType}")
    public ResponseEntity<RoomRecord> updateRoomRecord(@PathVariable int monthNum, @PathVariable int roomType, @RequestBody RoomRecord roomRecord) {
        RoomType type = getRoomType(roomType);
        Long id = roomRecordService.getByMonthNumAndRoomType(monthNum, type).getId();
        RoomRecord updatedRoomRecord = roomRecordService.updateRoomRecord(id, roomRecord);
        return ResponseEntity.ok(updatedRoomRecord);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoomRecord(@PathVariable Long id) {
        roomRecordService.deleteRoomRecord(id);
        return ResponseEntity.noContent().build();
    }
    @DeleteMapping("/{monthNum}/{roomType}")
    public ResponseEntity<RoomRecord> deleteRoomRecord(@PathVariable int monthNum, @PathVariable int roomType) {
        RoomType type = getRoomType(roomType);
        RoomRecord roomRecord = roomRecordService.getByMonthNumAndRoomType(monthNum, type);
        roomRecordService.deleteRoomRecord(roomRecord.getId());
        return ResponseEntity.noContent().build();
    }
    private RoomType getRoomType(int roomType) {
        return switch (roomType) {
            case 2 -> RoomType.TWO_PERSON_ROOM;
            case 3 -> RoomType.THREE_PERSON_ROOM;
            case 4 -> RoomType.FOUR_PERSON_ROOM;
            default -> throw new IllegalArgumentException("Invalid room type: " + roomType);
        };
    }
}
