package com.example.vacationpagebackend.mappers;

import com.example.vacationpagebackend.entity.RoomRecord;
import com.example.vacationpagebackend.entity.RoomType;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@JsonComponent
public class RoomRecordJsonDeserializer extends StdDeserializer<RoomRecord> {

    public RoomRecordJsonDeserializer() {
        this(null);
    }

    public RoomRecordJsonDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public RoomRecord deserialize(JsonParser jp, DeserializationContext ctxt)
            throws IOException {
        JsonNode node = jp.getCodec().readTree(jp);
        int monthNum = node.get("monthNum").asInt();
        String reservedBy = node.get("reservedBy").asText();
        RoomType roomType = getRoomType(node.get("roomType").asInt());
        Set<Integer> reservedDays = parseReservedDays(node.get("reservedDays").asText());
        return new RoomRecord().setRoomType(roomType).setMonthNum(monthNum).setReservedBy(reservedBy).setReservedDays(reservedDays);
    }

    private Set<Integer> parseReservedDays(String reservedDaysString) {
        String[] daysArray = reservedDaysString.split(",");
        Set<Integer> reservedDays = new HashSet<>();
        for (String day : daysArray) {
            reservedDays.add(Integer.parseInt(day.trim()));
        }
        return reservedDays;
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
