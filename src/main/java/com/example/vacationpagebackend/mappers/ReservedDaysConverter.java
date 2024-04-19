package com.example.vacationpagebackend.mappers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@Converter
public class ReservedDaysConverter implements AttributeConverter<Set<Integer>, String> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(Set<Integer> reservedDays) {
        try {
            return objectMapper.writeValueAsString(reservedDays);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Error converting reserved days to JSON string", e);
        }
    }

    @Override
    public Set<Integer> convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.isEmpty()) {
            return new HashSet<>();
        }
        try {
            return objectMapper.readValue(dbData, objectMapper.getTypeFactory().constructCollectionType(Set.class, Integer.class));
        } catch (IOException e) {
            throw new IllegalArgumentException("Error converting JSON string to reserved days", e);
        }
    }
}
