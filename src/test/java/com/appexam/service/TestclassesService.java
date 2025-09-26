package com.appexam.service;


import com.appexam.dao.IClassesRepository;
import com.appexam.dto.ClassesDto;
import com.appexam.entity.Classes;
import com.appexam.entity.Sectors;
import com.appexam.exception.EntityNotFoundException;
import com.appexam.mapper.ClassesMapper;
import com.appexam.service.impl.ClassesService;
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
public class TestclassesService {

    @InjectMocks
    private ClassesService classesService;

    @Mock
    private   ClassesMapper classesMapper;

    @Mock
    private  IClassesRepository classesRepository;

    private ClassesDto classesDto;
    private Classes classesEntity;

    @BeforeEach
    void setup(){

        Sectors sectorsEntity = new Sectors();
        sectorsEntity.setId(1L);
        sectorsEntity.setName("Sector 1");

       classesDto = new ClassesDto();
       classesDto.setId(1L);
       classesDto.setClassName("Classe1");
       classesDto.setDescription("Description classes1");
       classesDto.setSectorId(1L);


        classesEntity = new Classes();
        classesEntity.setId(1L);
        classesEntity.setClassName("Classe1");
        classesEntity.setDescription("Description classes1");
        classesEntity.setSectors(sectorsEntity);
    }


    @Test
    void testCreateClassesSuccess(){
        when(classesMapper.toClassesDto(classesEntity)).thenReturn(classesDto);
        when(classesMapper.fromClassesDto(classesDto)).thenReturn(classesEntity);
        when(classesRepository.save(classesEntity)).thenReturn(classesEntity);

        ClassesDto createClassesDto = classesService.createClass(classesDto);

        assertEquals("Classe1", createClassesDto.getClassName());
        assertEquals("Description classes1", createClassesDto.getDescription());
        assertEquals(1L, createClassesDto.getSectorId());

        verify(classesRepository).save(classesEntity);
    }

    @Test
    void testGetClasseByIdSucces(){
        when(classesRepository.findById(classesEntity.getId())).thenReturn(Optional.of(classesEntity));
        when(classesMapper.toClassesDto(classesEntity)).thenReturn(classesDto);

        ClassesDto foundClasses = classesService.getClassById(classesEntity.getId());

        assertEquals("Classe1", foundClasses.getClassName());
        assertEquals("Description classes1", foundClasses.getDescription());
        assertEquals(1L, foundClasses.getSectorId());

        verify(classesRepository).findById(classesEntity.getId());
    }

    @Test
    void testGetClasseByIdFailure(){
        when(classesRepository.findById(classesEntity.getId())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> classesService.getClassById(1L));
    }
    @Test void testGetAllClassesSuccess(){
        List<Classes> classesList = List.of(classesEntity);
        when(classesMapper.toClassesDto(classesEntity)).thenReturn(classesDto);
        when(classesRepository.findAll()).thenReturn(classesList);
        List<ClassesDto> classesDtoList = classesService.listAllClasses();
        assertEquals(1,classesDtoList.size());
        assertEquals("Classe1", classesDtoList.get(0).getClassName());
        verify(classesRepository).findAll(); }

    @Test
    void testUpdateClassesSuccess(){
        when(classesRepository.findById(classesEntity.getId())).thenReturn(Optional.of(classesEntity));
        when(classesRepository.save(classesEntity)).thenReturn(classesEntity);
        when(classesMapper.toClassesDto(classesEntity)).thenReturn(classesDto);
        when(classesMapper.fromClassesDto(classesDto)).thenReturn(classesEntity);

        ClassesDto updatedClassesDto = classesService.updateClass(classesEntity.getId(), classesDto);

        assertEquals("Classe1", updatedClassesDto.getClassName());
        assertEquals("Description classes1", updatedClassesDto.getDescription());

        verify(classesRepository).findById(classesEntity.getId());
        verify(classesRepository).save(classesEntity);
    }

    @Test
    void testUpdateClassesFailure(){
        when(classesRepository.findById(classesEntity.getId())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> classesService.updateClass(classesEntity.getId(), classesDto));
    }

    @Test
    void testDeleteClassesSuccess(){
       // when(classesRepository.findById(classesEntity.getId())).thenReturn(Optional.of(classesEntity));
        doNothing().when(classesRepository).deleteById(classesEntity.getId());

        classesService.deleteClass(classesEntity.getId());

        verify(classesRepository).deleteById(classesEntity.getId());
    }

}
