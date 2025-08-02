package com.appexam.service.impl;

import com.appexam.dao.IClassesRepository;
import com.appexam.dto.ClassesDto;
import com.appexam.exception.EntityNotFoundException;
import com.appexam.exception.RequestException;
import com.appexam.mapper.ClassesMapper;
import com.appexam.service.IClasseService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ClassesService implements IClasseService {

    private final ClassesMapper classesMapper;
    private final IClassesRepository classesRepository;

    public ClassesService(ClassesMapper classesMapper, IClassesRepository classesRepository) {
        this.classesMapper = classesMapper;
        this.classesRepository = classesRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public ClassesDto getClassById(Long id) {
        return classesMapper.toClassesDto(
                classesRepository.findById(id)
                        .orElseThrow(() -> new EntityNotFoundException(
                                "La classe avec l'ID n'est pas trouvée:" + id
                        ))
        );
    }

    @Transactional(readOnly = true)
    @Override
    public List<ClassesDto> listAllClasses() {
        return StreamSupport.stream(classesRepository.findAll().spliterator(), false)
                .map(classesMapper::toClassesDto)
                .collect(Collectors.toList());
    }

    @Override
    public ClassesDto createClass(ClassesDto classesDto) {
        return classesMapper.toClassesDto(classesRepository.save(classesMapper.fromClassesDto(classesDto)));
    }

    @Override
    public ClassesDto updateClass(Long id, ClassesDto classesDto) {
        return classesRepository.findById(id)
                .map(classe -> {
                    classesDto.setId(id);
                    return classesMapper.toClassesDto(classesRepository.save(
                            classesMapper.fromClassesDto(classesDto)
                    ));
                }).orElseThrow(() -> new EntityNotFoundException("La classe avec l'id:" + id + " n'existe pas"));
    }

    @Override
    @Transactional
    public void deleteClass(Long id) {
        try {
            if (classesRepository.findById(id).isEmpty()) {
                throw new EntityNotFoundException("La classe avec l'id:" + id + " n'existe pas");
            }
            classesRepository.deleteById(id);

        } catch (Exception e) {
            throw new RequestException("Erreur lors de la suppression", HttpStatus.CONFLICT);
        }
    }
}
