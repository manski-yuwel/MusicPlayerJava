package view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.Color;
import java.awt.Font;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

public class NowPlayingPanel extends JPanel{
	private final JLabel nowPlayingLabel = new JLabel("No song selected");
	private final JLabel artistNameLabel = new JLabel("");
    private final JLabel albumArtLabel = new JLabel();
    
    private final JPanel songDisplayPanel = new JPanel(new BorderLayout());
    private final JPanel songLabelPanel = new JPanel(new GridLayout(0,1));
    
    private final JPanel progressPanel = new JPanel(new BorderLayout(5, 5));
    private final JSlider seekBar = new JSlider(0, 100, 0);
    private final JLabel currentTimeLabel = new JLabel("00:00");
    private final JLabel timeLabel = new JLabel("00:00");
	
	public NowPlayingPanel() {
		setLayout(new BorderLayout());
		setBorder(new EmptyBorder(10, 10, 0, 10));
        
		// center - albumArt + grid for song title and artist
		// Style album art
        albumArtLabel.setHorizontalAlignment(SwingConstants.CENTER);
        albumArtLabel.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 2));
        albumArtLabel.setBackground(Color.WHITE);
        albumArtLabel.setOpaque(true);
        albumArtLabel.setText("No Album Art");
        
        // Style now playing label
        Font defaultStyle = nowPlayingLabel.getFont();
        nowPlayingLabel.setFont(new Font(defaultStyle.getName(), Font.BOLD, 16));
        nowPlayingLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        artistNameLabel.setFont(new Font(defaultStyle.getName(), Font.BOLD, defaultStyle.getSize()));
        artistNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        songLabelPanel.add(nowPlayingLabel);
        songLabelPanel.add(artistNameLabel);
		songDisplayPanel.add(albumArtLabel, BorderLayout.CENTER);
		songDisplayPanel.add(songLabelPanel, BorderLayout.SOUTH);
        
		// Style seek bar
		seekBar.setUI(new CircleThumbSliderUI(seekBar));
		
        // Progress section
		progressPanel.add(currentTimeLabel, BorderLayout.WEST);
        progressPanel.add(seekBar, BorderLayout.CENTER);
        progressPanel.add(timeLabel, BorderLayout.EAST);
        
        // Style time label
        timeLabel.setFont(new Font("Consolas", Font.PLAIN, 12));
        currentTimeLabel.setFont(new Font("Consolas", Font.PLAIN, 12));
        
		// make image scale to height
        albumArtLabel.addComponentListener(new ComponentAdapter() {
        	@Override
        	public void componentResized(ComponentEvent e) {
        		int targetSize = albumArtLabel.getHeight();
        		try {
        			ImageIcon icon = (ImageIcon) albumArtLabel.getIcon();

                    Image img = icon.getImage().getScaledInstance(targetSize, targetSize, Image.SCALE_SMOOTH);
                    albumArtLabel.setIcon(new ImageIcon(img));
        		} catch (Exception err) {
                }
        		
        	}
		});
        
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

	public JSlider getSeekBar() {
		return seekBar;
	}

	public JLabel getCurrentTimeLabel() {
		return currentTimeLabel;
	}

	public JLabel getTimeLabel() {
		return timeLabel;
	}
}
