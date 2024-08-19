package org.example.cinemamanagement.mapper;

import org.example.cinemamanagement.dto.perform.PerformDTO;
import org.example.cinemamanagement.model.Perform;

public class PerformMapper {
    public static PerformDTO toDTO(Perform perform) {
        return PerformDTO.builder()
                .viewType(perform.getViewType())
                .translateType(perform.getTranslateType())
                .cinemaRoomId(perform.getCinemaRoom().getId())
                .filmId(perform.getFilm().getId())
                .startTime(perform.getStartTime())
                .endTime(perform.getEndTime())
                .id(perform.getId())
                .build();
    }

//    public static Perform toEntity(PerformDTO performDTO) {
//        TypeMap<PerformDTO, Perform> typeMap = new ModelMapper()
//                .createTypeMap(PerformDTO.class, Perform.class);
//        typeMap.addMappings(mapper -> {
//            mapper.map(PerformDTO::getCinemaRoomDTO, Perform::setCinemaRoom);
//            mapper.map(PerformDTO::getFilmDTO, Perform::setFilm);
//            mapper.map(PerformDTO::getTranslateTypeDTO, Perform::setTranslateType);
//        });
//        return typeMap.map(performDTO);
//
//    }
}
