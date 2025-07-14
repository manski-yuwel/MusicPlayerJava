package view;

import java.util.EventObject;

import model.Song;

public class SongEvent extends EventObject {
	private Song song_data = null;
	private String command;
	
	public SongEvent(Object source, String command) {
		super(source);
		this.command = command;
	}

	public SongEvent(Object source, Song song_data, String command) {
		super(source);
		this.song_data = song_data;
		this.command = command;
	}
	
	public String getCommand() {
		return command;
	}

	public Song getSong_data() {
		return song_data;
	}

	public void setSong_data(Song song_data) {
		this.song_data = song_data;
	}

}
