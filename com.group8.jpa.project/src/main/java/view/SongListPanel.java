package view;

import model.Song;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.util.List;
import javax.swing.*;
import javax.swing.table.JTableHeader;

public class SongListPanel extends JPanel {
	private SongListTableModel tableModel = new SongListTableModel();
	private JTable table = new JTable(tableModel);
	
	// colors, NOTE: move this somewhere later
	private final Color PRIMARY_COLOR = new Color(138, 43, 226);
	private final Color SECONDARY_COLOR = new Color(75, 0, 130);
	private final Color ACCENT_COLOR = new Color(255, 20, 147);
	private final Color BACKGROUND_COLOR = new Color(240, 240, 250);
	private final Color PANEL_COLOR = Color.WHITE;

    public SongListPanel() {
    	setLayout(new BorderLayout());
        
        // table style
        // NOTE: Move some to UIManager
        // Style the song table
    	table.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        table.setRowHeight(30);
        table.setGridColor(new Color(220, 220, 220));
        table.setSelectionBackground(PRIMARY_COLOR.brighter());
        table.setSelectionForeground(Color.BLACK);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        // Style table header
        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 12));
        header.setBackground(PRIMARY_COLOR);
        header.setForeground(Color.BLACK);
        header.setReorderingAllowed(false);
        
        //NOTE: Try to make this into something adaptive? Like percentage?? or idk
        // Set column widths
        table.getColumnModel().getColumn(0).setPreferredWidth(300); // Title
        table.getColumnModel().getColumn(1).setPreferredWidth(200); // Artist
        table.getColumnModel().getColumn(2).setPreferredWidth(80);  // Duration
        
        
  
        add (new JScrollPane(table), BorderLayout.CENTER);
    }
    
    public void setData (List<Song> db){
        tableModel.setData(db);
    }
    
    public void refresh(){
        tableModel.fireTableDataChanged();
    }
}
