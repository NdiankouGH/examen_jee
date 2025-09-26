package com.appexam.service;

import com.appexam.dao.ISectorsREpository;
import com.appexam.dto.SectorsDto;
import com.appexam.entity.Sectors;
import com.appexam.exception.EntityNotFoundException;
import com.appexam.mapper.SectorMapper;
import com.appexam.service.impl.SectorsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class testSectorsService {

    @InjectMocks
    private SectorsService sectorsService;

    @Mock
    private SectorMapper sectorMapper;
    @Mock
    private ISectorsREpository sectorsRepository;

    private Sectors sectorEntity;
    private SectorsDto sectorsDto;

    @BeforeEach
     void setup(){

        sectorEntity = new Sectors();
        sectorsDto = new SectorsDto();

        sectorEntity.setId(1L);
        sectorEntity.setName("Sector 1");

        sectorsDto.setId(1L);
        sectorsDto.setName("Sector 1");

    }

    @Test
    void testCreateSectorS(){
        when(sectorMapper.toSectorsDto(sectorEntity)).thenReturn(sectorsDto);
        when(sectorMapper.fromSectorsDto(sectorsDto)).thenReturn(sectorEntity);
        when(sectorsRepository.save(sectorEntity)).thenReturn(sectorEntity);

        SectorsDto createdSector = sectorsService.createSector(sectorsDto);

        assertEquals(createdSector.getName(),sectorsDto.getName());

        verify(this.sectorsRepository).save(sectorEntity);
    }


    @Test
    void testGetSectorById(){


        when(sectorsRepository.findById(1L)).thenReturn(Optional.of(sectorEntity));
        when(sectorMapper.toSectorsDto(sectorEntity)).thenReturn(sectorsDto);

        SectorsDto foundSector = sectorsService.getSectorById(1L);
        assertEquals("Sector 1",foundSector.getName());

        verify(sectorsRepository).findById(1L);


    }
    @Test
    void testGetSectorByIdFailure(){

        when(sectorsRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, ()-> sectorsService.getSectorById(1L));

    }
    @Test
    void testGetAllSectors(){
        List<Sectors> sectorsList = List.of(sectorEntity);
        when(sectorMapper.toSectorsDto(sectorEntity)).thenReturn(sectorsDto);
        when(sectorsRepository.findAll()).thenReturn(sectorsList);

        List<SectorsDto> sectorsDtoList = sectorsService.listAllSectors();
        assertEquals(1,sectorsDtoList.size());
        assertEquals("Sector 1",sectorsDtoList.get(0).getName());

        verify(sectorsRepository).findAll();
    }

    @Test
    void testUpdateSectorS(){
        when(sectorsRepository.findById(1L)).thenReturn(Optional.of(sectorEntity));
        when(sectorsRepository.save(sectorEntity)).thenReturn(sectorEntity);
        when( sectorMapper.toSectorsDto(sectorEntity)).thenReturn(sectorsDto);
        when( sectorMapper.fromSectorsDto(sectorsDto)).thenReturn(sectorEntity);

        SectorsDto updatedSector = sectorsService.updateSector(sectorEntity.getId(), sectorsDto);

        assertEquals(updatedSector.getName(),sectorsDto.getName());
        assertEquals(updatedSector.getId(),sectorEntity.getId());

        verify(sectorsRepository).findById(1L);
        verify(sectorsRepository).save(sectorEntity);

    }

    @Test
    void testUpdateSectorSFailure(){

        when( sectorsRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, ()-> sectorsService.updateSector(sectorEntity.getId(), sectorsDto));

    }

    @Test
    void testDeleteSectorS() throws Exception{
        when( sectorsRepository.findById(1L)).thenReturn(Optional.of(sectorEntity));
        doNothing().when(sectorsRepository).deleteById(sectorEntity.getId());
        sectorsService.deleteSector(sectorEntity.getId());
        verify(sectorsRepository).deleteById(1L);


    }

    @Test
    void  testDeleteSectorSFailure(){
        when( sectorsRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, ()-> sectorsService.deleteSector(sectorEntity.getId()));
    }
}

