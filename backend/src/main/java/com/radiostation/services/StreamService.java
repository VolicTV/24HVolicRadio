package com.radiostation.services;

import com.azure.resourcemanager.mediaservices.MediaServicesManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

@Service
public class StreamService {

    private final MediaServicesManager mediaServicesManager;
    private final String resourceGroup;
    private final String accountName;
    @Value("${upload.dir}")
    private String uploadDir;

    private List<String> playlist = new ArrayList<>();

    @Autowired
    public StreamService(MediaServicesManager mediaServicesManager,
                         @Value("${azure.media-services.resource-group}") String resourceGroup,
                         @Value("${azure.media-services.account-name}") String accountName) {
        this.mediaServicesManager = mediaServicesManager;
        this.resourceGroup = resourceGroup;
        this.accountName = accountName;
    }

    public String getCurrentStreamUrl() {
        // Implement logic to get current stream URL from Azure Media Services
        // This might involve creating or retrieving a streaming locator
        return "https://example.com/stream";
    }

    public String getNextTrackInfo() {
        // Implement logic to get info about the next track
        return "Next track: Song Title by Artist";
    }

    public void startStreaming() {
        // Implement logic to start streaming
        // This might involve creating a new streaming endpoint or starting an existing one
    }

    public void stopStreaming() {
        // Implement logic to stop streaming
        // This might involve stopping or deleting a streaming endpoint
    }

    public String saveUploadedFile(MultipartFile file) throws IOException {
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        Path filePath = Paths.get(uploadDir, fileName);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        addToPlaylist(fileName);
        return fileName;
    }

    private void addToPlaylist(String fileName) {
        playlist.add(fileName);
    }

    public String getNextTrack() {
        if (playlist.isEmpty()) {
            return null;
        }
        return playlist.remove(0); // Remove and return the first track
    }
}
