package com.radiostation.models;

import java.time.Duration;
import java.util.Objects;

public class Track {
    private String id;
    private String title;
    private String artist;
    private String album;
    private int year;
    private Duration duration;
    private String fileUrl;
    private String sourceType; // e.g., "MP3" or "YouTube"

    // Constructor
    public Track(String id, String title, String artist, String album, int year, Duration duration, String fileUrl, String sourceType) {
        this.id = id;
        this.title = title;
        this.artist = artist;
        this.album = album;
        this.year = year;
        this.duration = duration;
        this.fileUrl = fileUrl;
        this.sourceType = sourceType;
    }

    // Getters and setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getArtist() { return artist; }
    public void setArtist(String artist) { this.artist = artist; }

    public String getAlbum() { return album; }
    public void setAlbum(String album) { this.album = album; }

    public int getYear() { return year; }
    public void setYear(int year) { this.year = year; }

    public Duration getDuration() { return duration; }
    public void setDuration(Duration duration) { this.duration = duration; }

    public String getFileUrl() { return fileUrl; }
    public void setFileUrl(String fileUrl) { this.fileUrl = fileUrl; }

    public String getSourceType() { return sourceType; }
    public void setSourceType(String sourceType) { this.sourceType = sourceType; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Track track = (Track) o;
        return year == track.year &&
                Objects.equals(id, track.id) &&
                Objects.equals(title, track.title) &&
                Objects.equals(artist, track.artist) &&
                Objects.equals(album, track.album) &&
                Objects.equals(duration, track.duration) &&
                Objects.equals(fileUrl, track.fileUrl) &&
                Objects.equals(sourceType, track.sourceType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, artist, album, year, duration, fileUrl, sourceType);
    }

    @Override
    public String toString() {
        return "Track{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", artist='" + artist + '\'' +
                ", album='" + album + '\'' +
                ", year=" + year +
                ", duration=" + duration +
                ", fileUrl='" + fileUrl + '\'' +
                ", sourceType='" + sourceType + '\'' +
                '}';
    }
}
