package com.greenfox.backend.service;

import com.greenfox.backend.entity.Resort;
import com.greenfox.backend.entity.User;

import java.util.List;

public interface ResortService {
    Resort createResort(User owner, Resort resort);
    Resort updateResort(Long resortId, Resort updated);
    void deleteResort(Long resortId, User owner);
    Resort getResortById(Long id);
    List<Resort> getResortsByOwner(User owner);
    List<Resort> searchResorts(String location, Double minPrice, Double maxPrice);
}
