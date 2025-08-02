package com.appexam.controller;

import com.appexam.dto.SectorsDto;
import com.appexam.service.ISectorsService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/sectors")
public class SectorsController {

    private final ISectorsService sectorsService;

    @GetMapping("/list")
    public ResponseEntity<List<SectorsDto>> listAllSectors() {
        return ResponseEntity.ok(sectorsService.listAllSectors());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SectorsDto> getSectorById(@PathVariable Long id) {
        return ResponseEntity.ok(sectorsService.getSectorById(id));
    }

    @PostMapping("/save")
    public ResponseEntity<SectorsDto> createSector(@RequestBody @Valid SectorsDto sectorsDto ) {
        return ResponseEntity.ok(sectorsService.createSector(sectorsDto));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<SectorsDto> updateSector(@PathVariable Long id, @RequestBody @Valid SectorsDto sectorsDto) {
        return ResponseEntity.ok(sectorsService.updateSector(id, sectorsDto));
    }

    @DeleteMapping("/id")
    public void deleteSector(@PathVariable Long id) {
        sectorsService.deleteSector(id);
    }


}
