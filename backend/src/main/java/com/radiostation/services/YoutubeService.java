package com.radiostation.services;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchResult;
import com.radiostation.models.Track;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class YoutubeService {

    @Value("${youtube.api.key}")
    private String apiKey;

    private YouTube youtube;

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
                "", // album not available from
    }
}
