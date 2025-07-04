package view;

import model.Song;

import java.util.List;

import javax.swing.table.AbstractTableModel;

public class SongListTableModel extends AbstractTableModel {
	private List<Song> db;
	
	private String[] colNames = {
			"Title",
			"Artist Name",
			"Duration"
	};
	
	@Override
	public boolean isCellEditable(int row, int column) {
	    return false; 
	}
	
	@Override
	public String getColumnName(int column) {
		return colNames[column];
	}
	
	public void setData(List<Song> db) {
		this.db = db;
	}
	
	@Override
    public int getRowCount() {
        return db.size();
    }

    @Override
    public int getColumnCount() {
    	return colNames.length;
    }
    
    @Override
    public Object getValueAt(int row, int col) {
    	Song song=db.get(row);
        
        switch(col){
            case 0: return song.getTitle();
            case 1: return song.getArtist();
            case 2: return song.getDuration();
        }
        return null;
    }
    
    public Song getSelectedSong(int row) {
    	return db.get(row);
    }
}