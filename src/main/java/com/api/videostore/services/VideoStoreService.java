package com.api.videostore.services;

import com.api.videostore.models.VideoStoreModel;
import com.api.videostore.repositories.VideoStoreRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class VideoStoreService {

    final VideoStoreRepository videoStoreRepository;

    public VideoStoreService(VideoStoreRepository videoStoreRepository) {
        this.videoStoreRepository = videoStoreRepository;
    }

    @Transactional
    public VideoStoreModel save(VideoStoreModel videoStoreModel) {
        return videoStoreRepository.save(videoStoreModel);
    }

    public boolean existsByNameAndDirector(String name, String director) {
        return videoStoreRepository.existsByNameAndDirector(name, director);
    }

    public List<VideoStoreModel> findAll() {
        return videoStoreRepository.findAll();
    }

    public Optional<VideoStoreModel> findById(UUID id) {
        return videoStoreRepository.findById(id);
    }

    @Transactional
    public void delete(VideoStoreModel videoStoreModel){
        videoStoreRepository.delete(videoStoreModel);
    }


}
