package com.appexam.controller;

import com.appexam.dto.ClassesDto;
import com.appexam.service.IClasseService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/classes")
public class ClassesController {

    private IClasseService classeService;

    @GetMapping("/list")
    public ResponseEntity<List<ClassesDto>> listAllClasses() {
        return ResponseEntity.ok(classeService.listAllClasses());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClassesDto> getClassById(@PathVariable Long id) {
        return ResponseEntity.ok(classeService.getClassById(id));
    }

    @PostMapping("/save")
    public ResponseEntity<ClassesDto> createClass(@RequestBody @Valid ClassesDto classesDto) {
        return ResponseEntity.ok(classeService.createClass(classesDto));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ClassesDto> updateClass(@PathVariable Long id, @RequestBody @Valid ClassesDto classesDto) {
        return ResponseEntity.ok(classeService.updateClass(id, classesDto));
    }

    @DeleteMapping("/delete/{id}")
    public void deleteClass(@PathVariable Long id) {
        classeService.deleteClass(id);
    }

}
