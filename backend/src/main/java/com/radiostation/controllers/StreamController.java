package com.radiostation.controllers;

import com.radiostation.services.StreamService;
import com.radiostation.services.YoutubeService;
import com.radiostation.models.Track;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.net.MalformedURLException;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class StreamController {

    private final StreamService streamService;
    private final YoutubeService youtubeService;

    @Autowired
    public StreamController(StreamService streamService, YoutubeService youtubeService) {
        this.streamService = streamService;
        this.youtubeService = youtubeService;
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

    @GetMapping("/search")
    public ResponseEntity<List<Track>> searchYoutube(@RequestParam("q") String query) {
        try {
            List<Track> tracks = youtubeService.searchTracks(query, 10);
            return ResponseEntity.ok(tracks);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(null);
        }
    }

    @PostMapping("/process")
    public ResponseEntity<String> processAudio(@RequestParam("videoId") String videoId) {
        try {
            String processedFilePath = youtubeService.processAudio(videoId);
            return ResponseEntity.ok(processedFilePath);
        } catch (Exception e) {
            e.printStackTrace(); // This will print the full stack trace to your console
            return ResponseEntity.internalServerError().body("Error processing audio: " + e.getMessage());
        }
    }

    @GetMapping("/audio/{fileName:.+}")
    public ResponseEntity<Resource> serveAudio(@PathVariable String fileName) {
        try {
            Path filePath = Paths.get(fileName);
            Resource resource = new UrlResource(filePath.toUri());
            
            if (resource.exists() || resource.isReadable()) {
                return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType("audio/mpeg"))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);
            } else {
                throw new RuntimeException("Could not read the file!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleExceptions(Exception e) {
        return ResponseEntity.internalServerError().body("An error occurred: " + e.getMessage());
    }
}
