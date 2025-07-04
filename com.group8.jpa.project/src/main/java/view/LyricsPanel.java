package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

public class LyricsPanel extends JPanel {
	private final JTextArea lyricsArea = new JTextArea();
	
	// colors, NOTE: move this somewhere later
	private final Color PRIMARY_COLOR = new Color(138, 43, 226);
	private final Color SECONDARY_COLOR = new Color(75, 0, 130);
	private final Color ACCENT_COLOR = new Color(255, 20, 147);
	private final Color BACKGROUND_COLOR = new Color(240, 240, 250);
	private final Color PANEL_COLOR = Color.WHITE;

	public LyricsPanel() {
		setLayout(new BorderLayout());
		setBorder(BorderFactory.createCompoundBorder(
	            new TitledBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)), 
	                           "Lyrics", 
	                           TitledBorder.LEFT, 
	                           TitledBorder.TOP, 
	                           new Font("Segoe UI", Font.BOLD, 14), 
	                           PRIMARY_COLOR),
	            new EmptyBorder(10, 10, 10, 10)
	        ));
		
		// textArea style
		lyricsArea.setFocusable(false);
		lyricsArea.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		lyricsArea.setBackground(new Color(248, 249, 250));
		lyricsArea.setBorder(new EmptyBorder(15, 15, 15, 15));
		lyricsArea.setEditable(false);
		lyricsArea.setLineWrap(true);
		lyricsArea.setWrapStyleWord(true);
		
		JScrollPane lyricsScrollPane = new JScrollPane(lyricsArea);
		lyricsScrollPane.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));
        
		add(lyricsScrollPane, BorderLayout.CENTER);
	}
}
