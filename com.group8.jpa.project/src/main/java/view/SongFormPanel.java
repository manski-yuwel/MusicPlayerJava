package view;

import java.awt.*;
import javax.swing.*;

public class SongFormPanel extends JPanel {
	private JFileChooser songChooser = new JFileChooser();
	private JFileChooser artChooser = new JFileChooser();
	private JFileChooser lyricsChooser = new JFileChooser();
	private JTextField titleField = new JTextField();
	private JTextField artistField = new JTextField();
	
	private JButton uploadSong = new JButton("Upload Song");
	private JButton uploadArt = new JButton("Upload Album Art");
	private JButton uploadLyrics = new JButton("Upload Lyrics");

	public SongFormPanel() {
		setLayout(new BorderLayout());
		
		JPanel northPanel = new JPanel(new BorderLayout());
    	JLabel panelTitle = new JLabel("Add New Song");
    	panelTitle.setFont(new Font(panelTitle.getFont().getName(), Font.BOLD, 16));
    	northPanel.add(panelTitle, BorderLayout.CENTER);
    	
    	// center - form
    	JPanel centerPanel = new JPanel(new GridBagLayout());
    	GridBagConstraints gbc = new GridBagConstraints();
    	gbc.fill = GridBagConstraints.HORIZONTAL;
    	gbc.anchor = GridBagConstraints.CENTER;
    	gbc.insets = new Insets(0,5,5,5);
        
        // song file
    	gbc.gridx = 0;
    	gbc.gridy = 0;
    	gbc.gridheight = 1;
    	gbc.gridwidth = 1;
    	gbc.weightx = 1.0;
        centerPanel.add(new JLabel("Song File (wav only)"), gbc);
        gbc.gridx = 1;
        gbc.gridwidth = 1;
        centerPanel.add(uploadSong, gbc);
        
        // art file
    	gbc.gridx = 0;
    	gbc.gridy = 1;
    	gbc.gridheight = 1;
    	gbc.gridwidth = 1;
    	gbc.weightx = 1.0;
        centerPanel.add(new JLabel("Album Art File (jpg only)"), gbc);
        gbc.gridx = 1;
        gbc.gridwidth = 1;
        centerPanel.add(uploadArt, gbc);
        
		
		// create copy of art and song to resource folder
			// and then for the Song Object, put the file name
		// parse lyrics using function
		// find way to compute duration
        
        add(northPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
		
	}
}
