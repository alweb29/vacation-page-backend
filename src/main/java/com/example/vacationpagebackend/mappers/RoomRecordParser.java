package com.example.vacationpagebackend.mappers;

import com.example.vacationpagebackend.entity.RoomRecord;
import com.example.vacationpagebackend.entity.RoomType;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashSet;
import java.util.Set;

public class RoomRecordParser {
    public static RoomRecord parseRoomRecordJson(String jsonString) throws JSONException {
        JSONObject jsonObject = new JSONObject(jsonString);
        RoomRecord roomRecord = new RoomRecord();

        // Parse roomType, monthNum, reservedBy fields
        roomRecord.setRoomType(getRoomType(jsonObject.getString("roomType")));
        roomRecord.setMonthNum(jsonObject.getInt("monthNum"));
        roomRecord.setReservedBy(jsonObject.getString("reservedBy"));

        // Parse reservedDays field
        String reservedDaysString = jsonObject.getString("reservedDays");
        Set<Integer> reservedDays = new HashSet<>();
        if (!reservedDaysString.isEmpty()) {
            String[] daysArray = reservedDaysString.split(",");
            for (String day : daysArray) {
                reservedDays.add(Integer.parseInt(day.trim()));
            }
        }
        roomRecord.setReservedDays(reservedDays);

        return roomRecord;
    }

    private static RoomType getRoomType(String roomType) {
        return switch (roomType) {
            case "2" -> RoomType.TWO_PERSON_ROOM;
            case "3" -> RoomType.THREE_PERSON_ROOM;
            case "4" -> RoomType.FOUR_PERSON_ROOM;
            default -> throw new IllegalArgumentException("Invalid room type: " + roomType);
        };
    }
}
