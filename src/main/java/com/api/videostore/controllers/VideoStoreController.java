package com.api.videostore.controllers;

import com.api.videostore.dtos.VideoStoreDto;
import com.api.videostore.models.VideoStoreModel;
import com.api.videostore.services.VideoStoreService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/video-store")
public class VideoStoreController {

    final VideoStoreService videoStoreService;

    public VideoStoreController(VideoStoreService videoStoreService) {
        this.videoStoreService = videoStoreService;
    }

    @PostMapping
    public ResponseEntity<Object> saveVideoStore(@RequestBody @Valid VideoStoreDto videoStoreDto){

        if(videoStoreService.existsByNameAndDirector(videoStoreDto.getName(), videoStoreDto.getDirector())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict: Movie already registered!");
        }


        var videoStoreModel = new VideoStoreModel();
        BeanUtils.copyProperties(videoStoreDto, videoStoreModel);
        videoStoreModel.setRegistrationDate(LocalDateTime.now(ZoneId.of("UTC")));
        return ResponseEntity.status(HttpStatus.CREATED).body(videoStoreService.save(videoStoreModel));
    }

    @GetMapping
    public ResponseEntity<List<VideoStoreModel>> getAllMovies() {
        return ResponseEntity.status(HttpStatus.OK).body(videoStoreService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getOneMovie(@PathVariable(value = "id") UUID id) {
        Optional<VideoStoreModel> videoStoreModelOptional = videoStoreService.findById(id);
        if(!videoStoreModelOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Movie not found.");
        }
        return ResponseEntity.status(HttpStatus.OK).body(videoStoreModelOptional.get());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteMovie(@PathVariable(value = "id") UUID id){
        Optional<VideoStoreModel> videoStoreModelOptional = videoStoreService.findById(id);

        if(!videoStoreModelOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Movie not found.");
        } videoStoreService.delete(videoStoreModelOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body("Movie deleted successfully!");
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateMovie(@PathVariable(value = "id") UUID id,
                                              @RequestBody @Valid VideoStoreDto videoStoreDto) {
        Optional<VideoStoreModel> videoStoreModelOptional = videoStoreService.findById(id);
        if(!videoStoreModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Movie not found.");
        }
        var videoStoreModel = new VideoStoreModel();
        BeanUtils.copyProperties(videoStoreDto, videoStoreModel);
        videoStoreModel.setId(videoStoreModelOptional.get().getId());
        videoStoreModel.setRegistrationDate(videoStoreModelOptional.get().getRegistrationDate());

        return ResponseEntity.status(HttpStatus.OK).body(videoStoreService.save(videoStoreModel));
    }

}
