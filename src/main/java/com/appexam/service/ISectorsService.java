package com.appexam.service;

import com.appexam.dto.SectorsDto;

import java.util.List;

public interface ISectorsService {
    List<SectorsDto> listAllSectors();

    SectorsDto getSectorById(Long id);

    SectorsDto createSector(SectorsDto sectorsDto);

    SectorsDto updateSector(Long id, SectorsDto sectorsDto);

    void deleteSector(Long id);
}
