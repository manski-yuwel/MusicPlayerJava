package view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

public class NowPlayingPanel extends JPanel{
	private final JLabel nowPlayingLabel = new JLabel("No song selected");
	private final JLabel artistNameLabel = new JLabel("Artist name");
    private final JLabel albumArtLabel = new JLabel();
    
    private final JPanel songDisplayPanel = new JPanel(new BorderLayout(5,5));
    private final JPanel songLabelPanel = new JPanel(new GridLayout(0,1));
    
    private final JPanel progressPanel = new JPanel(new BorderLayout(5, 5));
    private final JProgressBar progressBar = new JProgressBar();
    private final JLabel timeLabel = new JLabel("00:00 / 00:00");
	
	public NowPlayingPanel() {
		setLayout(new BorderLayout());
		setBorder(BorderFactory.createCompoundBorder(
	            new TitledBorder("Now Playing"),
	            new EmptyBorder(10, 10, 10, 10)
	        ));
        
		// center - albumArt + grid for song title and artist
		// Style album art
        albumArtLabel.setPreferredSize(new Dimension(200, 200)); // NOTE: Revise later so it scales proportionally instead of fixed size
        albumArtLabel.setHorizontalAlignment(SwingConstants.CENTER);
        albumArtLabel.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 2));
        albumArtLabel.setBackground(Color.WHITE);
        albumArtLabel.setOpaque(true);
        albumArtLabel.setText("No Album Art");
        
        // Style now playing label
        Font defaultStyle = nowPlayingLabel.getFont();
        nowPlayingLabel.setFont(new Font(defaultStyle.getName(), Font.BOLD, 16));
        nowPlayingLabel.setHorizontalAlignment(SwingConstants.CENTER);
        nowPlayingLabel.setVerticalAlignment(SwingConstants.BOTTOM);
        
        artistNameLabel.setFont(new Font(defaultStyle.getName(), Font.BOLD, 16));
        artistNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        artistNameLabel.setVerticalAlignment(SwingConstants.TOP);
        
        songLabelPanel.add(nowPlayingLabel);
        songLabelPanel.add(artistNameLabel);
		songDisplayPanel.add(albumArtLabel, BorderLayout.CENTER);
		songDisplayPanel.add(songLabelPanel, BorderLayout.EAST);
        
        // Progress section
        progressPanel.add(progressBar, BorderLayout.CENTER);
        progressPanel.add(timeLabel, BorderLayout.SOUTH);
        
        // Style progress bar
        progressBar.setStringPainted(true);
        progressBar.setString("No song loaded");
        
        // Style time label
        timeLabel.setFont(new Font("Consolas", Font.PLAIN, 12));
        timeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
//        add(nowPlayingLabel, BorderLayout.NORTH);
        add(songDisplayPanel, BorderLayout.CENTER);
        add(progressPanel, BorderLayout.SOUTH);
	}

	public JLabel getNowPlayingLabel() {
		return nowPlayingLabel;
	}

	public JLabel getArtistNameLabel() {
		return artistNameLabel;
	}

	public JLabel getAlbumArtLabel() {
		return albumArtLabel;
	}

	public JProgressBar getProgressBar() {
		return progressBar;
	}

	public JLabel getTimeLabel() {
		return timeLabel;
	}
}
