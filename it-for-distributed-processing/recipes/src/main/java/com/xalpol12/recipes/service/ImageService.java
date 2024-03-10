package com.xalpol12.recipes.service;

import com.xalpol12.recipes.model.mapper.ImageMapper;
import com.xalpol12.recipes.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ImageService {
    private final ImageRepository repository;
    private final ImageMapper mapper;
}
