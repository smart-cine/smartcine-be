package org.example.cinemamanagement.mapper;

import org.example.cinemamanagement.dto.cinema.CinemaRoomDTO;
import org.example.cinemamanagement.model.CinemaRoom;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;

public class CinemaRoomMapper {
    public static CinemaRoomDTO toDTO(CinemaRoom cinemaRoom) {
        TypeMap<CinemaRoom, CinemaRoomDTO> typeMap = new ModelMapper().createTypeMap(CinemaRoom.class, CinemaRoomDTO.class);
        CinemaRoomDTO cinemaRoomDTO = typeMap.map(cinemaRoom);
        cinemaRoomDTO.setCinemaId(cinemaRoom.getCinema().getId());
//        cinemaRoomDTO.setCinemaLayoutId(cinemaRoom.getCinemaLayout().getId());

        return cinemaRoomDTO;
    }

    public static CinemaRoom toEntity(CinemaRoomDTO cinemaRoomDTO) {
        TypeMap<CinemaRoomDTO, CinemaRoom> typeMap = new ModelMapper().createTypeMap(CinemaRoomDTO.class, CinemaRoom.class);
        return typeMap.map(cinemaRoomDTO);
    }
}
