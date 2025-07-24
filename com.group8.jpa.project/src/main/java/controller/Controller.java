package controller;

import java.util.List;
import model.SongDAO;
import model.Song;
import view.FormEvent;

public class Controller {
    private final SongDAO songDAO = new SongDAO();
    
    public List<Song> getSongs(){
        return songDAO.getAllSongs();
    }
    public void addSong(FormEvent e){
        Song song=new Song(
        		e.getTitle(), 
        		e.getArtist(), 
        		e.getDuration(), 
        		e.getLyrics(), 
        		e.getAlbumArtPath(),
        		e.getAudioFilePath()
        		);
        
        songDAO.saveSong(song);
    }
    
    public void addSong(Song song) {
    	songDAO.saveSong(song);
    }
    
    public void deleteSong(Song song) {
    	songDAO.deleteSong(song.getId());
    }
}
