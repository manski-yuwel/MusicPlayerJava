package view;

import java.util.EventObject;

public class FormEvent extends EventObject {
    private String title;
    private String artist;
    private String duration;
    private String lyrics;
    private String albumArtPath;
    private String audioFilePath;
	
	public FormEvent(Object source) {
		super(source);
	}

	public FormEvent(Object source, String title, String artist, String duration, String lyrics, String albumArtPath,
			String audioFilePath) {
		super(source);
		this.title = title;
		this.artist = artist;
		this.duration = duration;
		this.lyrics = lyrics;
		this.albumArtPath = albumArtPath;
		this.audioFilePath = audioFilePath;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getArtist() {
		return artist;
	}

	public void setArtist(String artist) {
		this.artist = artist;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getLyrics() {
		return lyrics;
	}

	public void setLyrics(String lyrics) {
		this.lyrics = lyrics;
	}

	public String getAlbumArtPath() {
		return albumArtPath;
	}

	public void setAlbumArtPath(String albumArtPath) {
		this.albumArtPath = albumArtPath;
	}

	public String getAudioFilePath() {
		return audioFilePath;
	}

	public void setAudioFilePath(String audioFilePath) {
		this.audioFilePath = audioFilePath;
	}

}
