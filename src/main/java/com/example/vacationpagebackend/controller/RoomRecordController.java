package com.example.vacationpagebackend.controller;

import com.example.vacationpagebackend.entity.RoomRecord;
import com.example.vacationpagebackend.entity.RoomRecordWithDaysWritten;
import com.example.vacationpagebackend.entity.RoomType;
import com.example.vacationpagebackend.service.EmailSenderService;
import com.example.vacationpagebackend.service.RoomRecordService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/room-records")
@RequiredArgsConstructor
@Slf4j
public class RoomRecordController {

    private final RoomRecordService roomRecordService;
    private final EmailSenderService emailSenderService;

    @GetMapping
    @CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS })
    public ResponseEntity<List<RoomRecord>> getAllRoomRecords() {
        log.info("Get all room records");
        List<RoomRecord> roomRecords = roomRecordService.getAllRoomRecords();
        return ResponseEntity.ok(roomRecords);
    }

    @GetMapping("/{monthNum}/{roomType}")
    @CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS })
    public ResponseEntity<RoomRecordWithDaysWritten> getRoomRecord(@PathVariable int monthNum, @PathVariable int roomType) {
        log.info("Get room record for month number " + monthNum + " and room type " + roomType);
        RoomType type = getRoomType(roomType);
        RoomRecordWithDaysWritten response = roomRecordService.getRoomRecordsForMonthAndType(monthNum,type);
        return ResponseEntity.ok(response);
    }


    @PostMapping
    @CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS })
    public ResponseEntity<RoomRecord> createRoomRecord(@RequestBody RoomRecord roomRecord) {
        log.info("Creating a new room record");
        RoomRecord createdRoomRecord = roomRecordService.createRoomRecord(roomRecord);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdRoomRecord);
    }

    @PostMapping("/reservation")
    @CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS })
    public ResponseEntity sendEmailToAdmin(@RequestBody Mail mail){
        log.info("Sending Reservation Form! \n" + mail.toString());
        if(emailSenderService.sendEmail(mail)){
            return ResponseEntity.ok().build();
        }else {
            return ResponseEntity.status(500).build();
        }
    }

    @PutMapping("/{id}")
    @CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS })
    public ResponseEntity<RoomRecord> updateRoomRecord(@PathVariable Long id, @RequestBody RoomRecord roomRecord) {
        log.info("Updating room record with id " + id);
        RoomRecord updatedRoomRecord = roomRecordService.updateRoomRecord(id, roomRecord);
        return ResponseEntity.ok(updatedRoomRecord);
    }

    @DeleteMapping("/{id}")
    @CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS })
    public ResponseEntity<Void> deleteRoomRecord(@PathVariable Long id) {
        log.info("Deleting room record with id " + id);
        roomRecordService.deleteRoomRecord(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{monthNum}/{roomType}")
    @CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS })
    public ResponseEntity<RoomRecord> deleteRoomRecord(@PathVariable int monthNum, @PathVariable int roomType) {
        log.info("Deleting room record with id " + roomType);
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
