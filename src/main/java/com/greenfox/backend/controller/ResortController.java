package com.greenfox.backend.controller;

import com.greenfox.backend.dto.ResortDto;
import com.greenfox.backend.dto.ResortCreateDto;
import com.greenfox.backend.dto.ResortUpdateDto;
import com.greenfox.backend.entity.Resort;
import com.greenfox.backend.entity.User;
import com.greenfox.backend.mapper.ResortMapper;
import com.greenfox.backend.service.ResortService;
import com.greenfox.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/resorts")
@RequiredArgsConstructor
public class ResortController {

    private final ResortService resortService;
    private final UserService userService;
    private final ResortMapper resortMapper;

    @PostMapping("/owner/{ownerId}")
    public ResponseEntity<ResortDto> create(
            @PathVariable Long ownerId,
            @Valid @RequestBody ResortCreateDto dto) {
        User owner = userService.getById(ownerId);
        Resort resort = resortMapper.fromCreateDto(dto);
        Resort saved = resortService.createResort(owner, resort);
        return ResponseEntity.ok(resortMapper.toDto(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResortDto> update(
            @PathVariable Long id,
            @Valid @RequestBody ResortUpdateDto dto) {
        Resort updated = resortService.updateResort(id, resortMapper.fromUpdateDto(dto));
        return ResponseEntity.ok(resortMapper.toDto(updated));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResortDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(resortMapper.toDto(resortService.getResortById(id)));
    }

    @GetMapping
    public ResponseEntity<List<ResortDto>> search(
            @RequestParam(required = false) String location,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice) {
        List<Resort> resorts = resortService.searchResorts(location, minPrice, maxPrice);
        return ResponseEntity.ok(resorts.stream().map(resortMapper::toDto).toList());
    }

    @DeleteMapping("/{id}/owner/{ownerId}")
    public ResponseEntity<Void> delete(@PathVariable Long id, @PathVariable Long ownerId) {
        User owner = userService.getById(ownerId);
        resortService.deleteResort(id, owner);
        return ResponseEntity.noContent().build();
    }
}
