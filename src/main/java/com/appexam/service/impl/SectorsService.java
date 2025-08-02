package com.appexam.service.impl;

import com.appexam.dao.ISectorsREpository;
import com.appexam.dto.SectorsDto;
import com.appexam.exception.EntityNotFoundException;
import com.appexam.mapper.SectorMapper;
import com.appexam.service.ISectorsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class SectorsService implements ISectorsService {
    private final SectorMapper sectorMapper;
    private final ISectorsREpository sectorsRepository;

    public SectorsService(SectorMapper sectorMapper, ISectorsREpository sectorsRepository) {
        this.sectorMapper = sectorMapper;
        this.sectorsRepository = sectorsRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<SectorsDto> listAllSectors() {
        return StreamSupport.stream(sectorsRepository.findAll().spliterator(), false)
                .map(sectorMapper::toSectorsDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public SectorsDto getSectorById(Long id) {
        return sectorMapper.toSectorsDto(
                sectorsRepository.findById(id).orElseThrow(
                        () -> new EntityNotFoundException("La secteur avec l'id:" + id + " n'existe pas ")
                )
        );
    }

    @Override
    public SectorsDto createSector(SectorsDto sectorsDto) {
        return sectorMapper.toSectorsDto(sectorsRepository.save(sectorMapper.fromSectorsDto(sectorsDto)));
    }

    @Override
    public SectorsDto updateSector(Long id, SectorsDto sectorsDto) {
        return sectorsRepository.findById(id)
                .map(sectors -> {
                    sectorsDto.setId(id);
                    return sectorMapper.toSectorsDto(sectorsRepository.save(sectorMapper.fromSectorsDto(sectorsDto))
                    );
                }).orElseThrow(() -> new EntityNotFoundException("La secteur avec l'id:" + id + " n'existe pas"));
    }

    @Override
    public void deleteSector(Long id) {

        try {
            if (sectorsRepository.findById(id).isEmpty()) {
                throw new EntityNotFoundException("La secteur avec l'id:" + id + " n'existe pas");
            }
            sectorsRepository.deleteById(id);
        } catch (Exception e) {
            throw new EntityNotFoundException("La secteur avec l'id:" + id + " n'existe pas");
        }
    }
}
