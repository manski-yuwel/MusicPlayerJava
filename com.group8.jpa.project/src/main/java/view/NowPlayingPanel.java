package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

public class NowPlayingPanel  extends JPanel{
	private final JLabel nowPlayingLabel = new JLabel("No song selected");
    private final JLabel albumArtLabel = new JLabel();
	
	// colors, NOTE: move this somewhere later
	private final Color PRIMARY_COLOR = new Color(138, 43, 226);
	private final Color SECONDARY_COLOR = new Color(75, 0, 130);
	private final Color ACCENT_COLOR = new Color(255, 20, 147);
	private final Color BACKGROUND_COLOR = new Color(240, 240, 250);
	private final Color PANEL_COLOR = Color.WHITE;
	
	public NowPlayingPanel() {
		setLayout(new BorderLayout());
		setBorder(BorderFactory.createCompoundBorder(
				new TitledBorder(
						BorderFactory.createLineBorder(new Color(220, 220, 220)), 
						"Now Playing", 
						TitledBorder.LEFT, 
						TitledBorder.TOP, 
						new Font("Segoe UI", Font.BOLD, 14), 
						PRIMARY_COLOR
						),
	            new EmptyBorder(10, 10, 10, 10)
	        ));
		
		// Style album art
        albumArtLabel.setPreferredSize(new Dimension(200, 200));
        albumArtLabel.setHorizontalAlignment(SwingConstants.CENTER);
        albumArtLabel.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 2));
        albumArtLabel.setBackground(Color.WHITE);
        albumArtLabel.setOpaque(true);
        albumArtLabel.setText("No Album Art");
        
        // Style now playing label
        nowPlayingLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        nowPlayingLabel.setForeground(PRIMARY_COLOR);
        nowPlayingLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        add(nowPlayingLabel, BorderLayout.NORTH);
        add(albumArtLabel, BorderLayout.CENTER);
	}

}
