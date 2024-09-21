package com.radiostation.services;

import com.radiostation.models.Track;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchResult;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class YoutubeService {

    @Value("${youtube.api.key}")
    private String apiKey;

    private YouTube youtube;

    // Add these new fields
    @Value("${yt-dlp.path}")
    private String ytDlpPath;

    @Value("${ffmpeg.path}")
    private String ffmpegPath;

    public YoutubeService() throws Exception {
        this.youtube = new YouTube.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),
                JacksonFactory.getDefaultInstance(),
                null)
                .setApplicationName("RadioStation")
                .build();
    }

    public List<Track> searchTracks(String query, long maxResults) throws Exception {
        YouTube.Search.List search = youtube.search().list("id,snippet");
        search.setKey(apiKey);
        search.setQ(query);
        search.setType("video");
        search.setVideoCategoryId("10"); // Music category
        search.setFields("items(id/videoId,snippet/title,snippet/channelTitle)");
        search.setMaxResults(maxResults);

        List<SearchResult> searchResults = search.execute().getItems();

        return searchResults.stream()
                .map(this::convertToTrack)
                .collect(Collectors.toList());
    }

    private Track convertToTrack(SearchResult searchResult) {
        String videoId = searchResult.getId().getVideoId();
        String title = searchResult.getSnippet().getTitle();
        String artist = searchResult.getSnippet().getChannelTitle();
    
        return new Track(
            videoId,
            title,
            artist,
            "", // album not available
            0,  // year not available
            Duration.ZERO, // duration not available
            "https://www.youtube.com/watch?v=" + videoId, // file URL
            "YouTube" // source type
        );
    }

    public String processAudio(String videoId) throws IOException, InterruptedException {
        String outputFile = "processed_" + videoId + ".mp3";
        Path outputPath = Paths.get(outputFile);
        
        // Check if the file already exists
        if (Files.exists(outputPath)) {
            System.out.println("Processed file already exists: " + outputFile);
            return outputFile;
        }

        String tempOutputFile = "temp_" + videoId + ".mp3";
        
        // Download audio using yt-dlp
        ProcessBuilder ytDlpBuilder = new ProcessBuilder(
            ytDlpPath,
            "--ffmpeg-location", ffmpegPath,  // Explicitly tell yt-dlp where to find ffmpeg
            "-x",
            "--audio-format", "mp3",
            "-o", tempOutputFile,
            "https://www.youtube.com/watch?v=" + videoId
        );
        ytDlpBuilder.inheritIO();
        System.out.println("Executing yt-dlp command: " + String.join(" ", ytDlpBuilder.command()));
        Process ytDlpProcess = ytDlpBuilder.start();
        int ytDlpExitCode = ytDlpProcess.waitFor();
        
        if (ytDlpExitCode != 0) {
            throw new RuntimeException("yt-dlp process failed with exit code " + ytDlpExitCode);
        }

        // Process audio using ffmpeg
        ProcessBuilder ffmpegBuilder = new ProcessBuilder(
            ffmpegPath,
            "-i", tempOutputFile,
            "-af", "acompressor=threshold=-10dB:ratio=4:attack=200:release=1000",
            "-b:a", "192k",
            outputFile
        );
        ffmpegBuilder.inheritIO();
        System.out.println("Executing ffmpeg command: " + String.join(" ", ffmpegBuilder.command()));
        Process ffmpegProcess = ffmpegBuilder.start();
        int ffmpegExitCode = ffmpegProcess.waitFor();

        if (ffmpegExitCode != 0) {
            throw new RuntimeException("ffmpeg process failed with exit code " + ffmpegExitCode);
        }

        // Delete the temporary file
        Files.deleteIfExists(Paths.get(tempOutputFile));

        // Return the path to the processed file
        return outputFile;
    }
}
