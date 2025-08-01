package view;

import model.Song;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.JTableHeader;

public class SongListPanel extends JPanel {
	private SongListTableModel tableModel = new SongListTableModel();
	private JTable table = new JTable(tableModel);
	
	private JButton switchButton = new JButton("");
	private JButton addButton = new JButton("Add Song");
	private JButton deleteButton = new JButton("Delete");

    public SongListPanel() {
    	setMinimumSize(new Dimension(300, 200));
    	setLayout(new BorderLayout(0, 10));
    	setBorder(new EmptyBorder(10, 10, 10, 10));
    	
    	JPanel northPanel = new JPanel(new BorderLayout());
    	JLabel panelTitle = new JLabel("Song Library");
    	panelTitle.setFont(new Font(panelTitle.getFont().getName(), Font.BOLD, 16));
    	
    	northPanel.add(panelTitle, BorderLayout.CENTER);
    	northPanel.add(switchButton, BorderLayout.EAST);
        
        // table style
        table.setRowHeight(30);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        // Style table header
        JTableHeader header = table.getTableHeader();
        header.setReorderingAllowed(false);

        // Set column widths
        table.getColumnModel().getColumn(0).setPreferredWidth(300); // Title
        table.getColumnModel().getColumn(1).setPreferredWidth(200); // Artist
        table.getColumnModel().getColumn(2).setPreferredWidth(75);  // Duration
        
        // south - buttons
        JPanel southPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        southPanel.add(addButton);
        southPanel.add(deleteButton);
        
        add(northPanel, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);
        add(southPanel, BorderLayout.SOUTH);
    }
    
    public JButton getSwitchButton() {
		return switchButton;
	}

	public JButton getAddButton() {
		return addButton;
	}

	public JButton getDeleteButton() {
		return deleteButton;
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
