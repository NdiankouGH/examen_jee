package com.appexam.mapper;

import com.appexam.dto.ClassesDto;
import com.appexam.entity.Classes;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")

public interface ClassesMapper {

    ClassesDto toClassesDto(Classes classes);

    Classes fromClassesDto(ClassesDto classesDto);
}
