package com.example.vacationpagebackend.controller;

import com.example.vacationpagebackend.entity.RoomRecord;
import com.example.vacationpagebackend.entity.RoomRecordWithDaysWritten;
import com.example.vacationpagebackend.entity.RoomType;
import com.example.vacationpagebackend.mappers.RoomRecordParser;
import com.example.vacationpagebackend.service.RoomRecordService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
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
    @CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS })
    public ResponseEntity<List<RoomRecord>> getAllRoomRecords() {
        List<RoomRecord> roomRecords = roomRecordService.getAllRoomRecords();
        return ResponseEntity.ok(roomRecords);
    }

    @GetMapping("/{monthNum}/{roomType}")
    @CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS })
    public ResponseEntity<RoomRecordWithDaysWritten> getRoomRecord(@PathVariable int monthNum, @PathVariable int roomType) {
        System.out.println("get on " + monthNum + " " + roomType);
        RoomType type = getRoomType(roomType);
        RoomRecordWithDaysWritten response = roomRecordService.getRoomRecordsForMonthAndType(monthNum,type);
        return ResponseEntity.ok(response);
    }


    @SneakyThrows
    @PostMapping
    @CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS })
    public ResponseEntity<RoomRecord> createRoomRecord(@RequestBody String roomRecord) {
        RoomRecord createdRoomRecord = roomRecordService.createRoomRecord(RoomRecordParser.parseRoomRecordJson(roomRecord));
        return ResponseEntity.status(HttpStatus.CREATED).body(createdRoomRecord);
    }

    @PostMapping("/reservation")
    @CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS })
    public ResponseEntity sendEmailToAdmin(@RequestBody Mail mail){
        System.out.println("Sending Reservation Form! \n" + mail.toString());
        return ResponseEntity.ok().build();
    }
    @PutMapping("/{id}")
    @CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS })
    public ResponseEntity<RoomRecord> updateRoomRecord(@PathVariable Long id, @RequestBody RoomRecord roomRecord) {
        RoomRecord updatedRoomRecord = roomRecordService.updateRoomRecord(id, roomRecord);
        return ResponseEntity.ok(updatedRoomRecord);
    }

    @DeleteMapping("/{id}")
    @CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS })
    public ResponseEntity<Void> deleteRoomRecord(@PathVariable Long id) {
        roomRecordService.deleteRoomRecord(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{monthNum}/{roomType}")
    @CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS })
    public ResponseEntity<RoomRecord> deleteRoomRecord(@PathVariable int monthNum, @PathVariable int roomType) {
        RoomType type = getRoomType(roomType);
        List<RoomRecord> records = roomRecordService.getRoomRecordsAsListByMonthAndType(monthNum,type);
        for (RoomRecord roomRecord : records){
            roomRecordService.deleteRoomRecord(roomRecord.getId());
        }
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
