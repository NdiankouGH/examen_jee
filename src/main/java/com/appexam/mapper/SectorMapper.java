package com.appexam.mapper;

import com.appexam.dto.SectorsDto;
import com.appexam.entity.Sectors;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SectorMapper {
    SectorsDto toSectorsDto(Sectors sectors);

    Sectors fromSectorsDto(SectorsDto sectorsDto);
}
