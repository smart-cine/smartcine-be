package org.example.cinemamanagement.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.HashMap;
import java.util.Map;

@Converter
public class JsonToMapConverter implements AttributeConverter<Map<String, Object>, String> {

    private final static ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(Map<String, Object> attribute) {
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (JsonProcessingException e) {
            return null; // or handle the exception as needed
        }
    }

    @Override
    public Map<String, Object> convertToEntityAttribute(String dbData) {
        try {
            return objectMapper.readValue(dbData, HashMap.class);
        } catch (JsonProcessingException e) {
            return null; // or handle the exception as needed
        }
    }
}