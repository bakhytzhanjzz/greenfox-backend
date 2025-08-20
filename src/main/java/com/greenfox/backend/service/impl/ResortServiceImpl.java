package com.greenfox.backend.service.impl;

import com.greenfox.backend.entity.Resort;
import com.greenfox.backend.entity.User;
import com.greenfox.backend.repository.ResortRepository;
import com.greenfox.backend.service.ResortService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ResortServiceImpl implements ResortService {

    private final ResortRepository resortRepository;

    @Override
    @Transactional
    public Resort createResort(User owner, Resort resort) {
        resort.setOwner(owner);
        return resortRepository.save(resort);
    }

    @Override
    @Transactional
    public Resort updateResort(Long resortId, Resort updated) {
        Resort existing = getResortById(resortId);
        existing.setName(updated.getName());
        existing.setDescription(updated.getDescription());
        existing.setLocation(updated.getLocation());
        existing.setPricePerNight(updated.getPricePerNight());
        existing.setAmenities(updated.getAmenities());
        existing.setImages(updated.getImages());
        existing.setPolicies(updated.getPolicies());
        return resortRepository.save(existing);
    }

    @Override
    @Transactional
    public void deleteResort(Long resortId, User owner) {
        Resort resort = getResortById(resortId);
        if (!resort.getOwner().equals(owner)) {
            throw new SecurityException("Not owner of resort");
        }
        resortRepository.delete(resort);
    }

    @Override
    public Resort getResortById(Long id) {
        return resortRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Resort not found"));
    }

    @Override
    public List<Resort> getResortsByOwner(User owner) {
        return resortRepository.findByOwner(owner);
    }

    @Override
    public List<Resort> searchResorts(String location, Double minPrice, Double maxPrice) {
        // пока примитивно: location LIKE
        List<Resort> resorts = resortRepository.findByLocationContainingIgnoreCase(location != null ? location : "");
        if (minPrice != null) {
            resorts.removeIf(r -> r.getPricePerNight().compareTo(BigDecimal.valueOf(minPrice)) < 0);
        }
        if (maxPrice != null) {
            resorts.removeIf(r -> r.getPricePerNight().compareTo(BigDecimal.valueOf(maxPrice)) > 0);
        }
        return resorts;
    }
}
