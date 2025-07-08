package controller;

import java.util.List;
import model.SongDAO;
import model.Song;
import view.SongEvent;

public class Controller {
    private final SongDAO songDAO = new SongDAO();
    
    public List<Song> getSongs(){
        return songDAO.getAllSongs();
    }
    public void addSong(SongEvent e){
    	// NOTE: This is wrong, format should be the commented one below
    	// for adding to db. But this works so not sure
    	Song song = e.getSong_data();
    	
    	// NOTE: This should be the format, 
    	// but we dont have a system to adding new songs yet
//        Song song=new Song(
//        		e.getTitle(), 
//        		e.getArtist(), 
//        		e.getDuration(), 
//        		e.getLyrics(), 
//        		e.getAlbumArtPath(),
//        		e.getAudioFilePath()
//        		);
        
        songDAO.saveSong(song);
    }
}
