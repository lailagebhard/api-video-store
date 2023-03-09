package com.api.videostore.repositories;

import com.api.videostore.models.VideoStoreModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface VideoStoreRepository extends JpaRepository<VideoStoreModel, UUID> {

    boolean existsByNameAndDirector(String name, String director);

}
