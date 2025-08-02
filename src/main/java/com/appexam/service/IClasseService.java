package com.appexam.service;

import com.appexam.dto.ClassesDto;

import java.util.List;

public interface IClasseService {
    List<ClassesDto> listAllClasses();

    ClassesDto getClassById(Long id);

    ClassesDto createClass(ClassesDto classesDto);

    ClassesDto updateClass(Long id, ClassesDto classesDto);

    void deleteClass(Long id);
}
