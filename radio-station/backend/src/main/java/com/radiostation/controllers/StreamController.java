package com.radiostation.controllers;

import com.radiostation.services.StreamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation;

@RestController
@RequestMapping("/api/stream")
public class StreamController {

    private final StreamService streamService;

    @Autowired
    public StreamController(StreamService streamService) {
        this.streamService = streamService;
    }

    @GetMapping("/current")
    public ResponseEntity<String> getCurrentStream() {
        String streamUrl = streamService.getCurrentStreamUrl();
        return ResponseEntity.ok(streamUrl);
    }

    @GetMapping("/next")
    public ResponseEntity<String> getNextTrack() {
        String nextTrackInfo = streamService.getNextTrackInfo();
        return ResponseEntity.ok(nextTrackInfo);
    }

    @PostMapping("/start")
    public ResponseEntity<String> startStreaming() {
        streamService.startStreaming();
        return ResponseEntity.ok("Streaming started");
    }

    @PostMapping("/stop")
    public ResponseEntity<String> stopStreaming() {
        streamService.stopStreaming();
        return ResponseEntity.ok("Streaming stopped");
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleExceptions(Exception e) {
        return ResponseEntity.internalServerError().body("An error occurred: " + e.getMessage());
    }
}
