package view;

import model.Song;

import java.awt.BorderLayout;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.JTableHeader;

public class SongListPanel extends JPanel {
	private SongListTableModel tableModel = new SongListTableModel();
	private JTable table = new JTable(tableModel);

    public SongListPanel() {
    	setLayout(new BorderLayout());
    	setBorder(BorderFactory.createCompoundBorder(
	            new TitledBorder("Song Library"),
	            new EmptyBorder(10, 10, 10, 10)
	        ));
        
        // table style
        table.setRowHeight(30);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        // Style table header
        JTableHeader header = table.getTableHeader();
        header.setReorderingAllowed(false);
        
        //NOTE: Try to make this into something adaptive? Like percentage?? or idk
        // Set column widths
        table.getColumnModel().getColumn(0).setPreferredWidth(300); // Title
        table.getColumnModel().getColumn(1).setPreferredWidth(200); // Artist
        table.getColumnModel().getColumn(2).setPreferredWidth(80);  // Duration

        add (new JScrollPane(table), BorderLayout.CENTER);
    }
    
    public SongListTableModel getTableModel() {
		return tableModel;
	}

	public JTable getTable() {
		return table;
	}

	public void setData (List<Song> db){
        tableModel.setData(db);
    }
	
    // get selected song from table
    public Song getSelectedSong() {
        Song selectedSong = null;
        try {
        	selectedSong = tableModel.getSelectedSong(table.getSelectedRow());
        } catch (Exception e) {
        	
        }
        
        return selectedSong;
    }
    
    public void refresh(){
        tableModel.fireTableDataChanged();
    }
}
