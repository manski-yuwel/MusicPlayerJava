package com.clemente.musicplayer.entity;

import javax.persistence.*;

@Entity
@Table(name = "songs")
public class Song {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String title;
    
    @Column(nullable = false)
    private String artist;
    
    @Column(nullable = false)
    private String duration;
    
    @Column(columnDefinition = "TEXT")
    private String lyrics;
    
    @Column(name = "album_art_path")
    private String albumArtPath;
    
    @Column(name = "audio_file_path")
    private String audioFilePath;
    
    // Constructors
    public Song() {}
    
    public Song(String title, String artist, String duration, String lyrics, 
                String albumArtPath, String audioFilePath) {
        this.title = title;
        this.artist = artist;
        this.duration = duration;
        this.lyrics = lyrics;
        this.albumArtPath = albumArtPath;
        this.audioFilePath = audioFilePath;
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    
    public String getArtist() { return artist; }
    public void setArtist(String artist) { this.artist = artist; }
    
    public String getDuration() { return duration; }
    public void setDuration(String duration) { this.duration = duration; }
    
    public String getLyrics() { return lyrics; }
    public void setLyrics(String lyrics) { this.lyrics = lyrics; }
    
    public String getAlbumArtPath() { return albumArtPath; }
    public void setAlbumArtPath(String albumArtPath) { this.albumArtPath = albumArtPath; }
    
    public String getAudioFilePath() { return audioFilePath; }
    public void setAudioFilePath(String audioFilePath) { this.audioFilePath = audioFilePath; }
}
