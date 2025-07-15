package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.*;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class ControlsPanel extends JPanel {
	private VolumePanel volumePanel = new VolumePanel();
	
    // Control buttons
    private final JButton playButton = new JButton("Play");
    private final JButton pauseButton = new JButton("Pause");
    private final JButton stopButton = new JButton("Stop");
	
	private SongListener songListener;
	
	public ControlsPanel() {
		setMinimumSize(new Dimension(650, 60));
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(0, 10, 10, 10));
        
        // Style control buttons
        styleControlButton(playButton, new Color(155, 82, 224));
        styleControlButton(pauseButton, Color.ORANGE);
        styleControlButton(stopButton, Color.RED);
        
        // Initially disable pause button
        toggle_play_and_pause("Play");
        
        // Buttons section
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonsPanel.add(playButton);
        buttonsPanel.add(pauseButton);
        buttonsPanel.add(stopButton);
        
        add(buttonsPanel, BorderLayout.CENTER);
        add(volumePanel, BorderLayout.EAST);
        
        // Control buttons
        playButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
            	SongEvent songEvent = new SongEvent(this, "Play");
                
                if (getSongListener() != null) {
                	getSongListener().songEventOccured(songEvent);
                }
            }
        });
        
        pauseButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
            	SongEvent songEvent = new SongEvent(this, "Pause");
                
                if (getSongListener() != null) {
                	getSongListener().songEventOccured(songEvent);
                }
            }
        });
        
        stopButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
            	SongEvent songEvent = new SongEvent(this, "Stop");
                
                if (getSongListener() != null) {
                	getSongListener().songEventOccured(songEvent);
                }
            }
        });
	}

	public VolumePanel getVolumePanel() {
		return volumePanel;
	}

	public SongListener getSongListener() {
		return songListener;
	}

	public void setSongListener(SongListener songListener) {
		this.songListener = songListener;
	}

	public void toggle_play_and_pause(String active_button_name) {
		switch(active_button_name){
	        case "Play": 
	            playButton.setEnabled(true);
	            pauseButton.setEnabled(false);
	        	break;
	        case "Pause": 
	        	playButton.setEnabled(false);
                pauseButton.setEnabled(true);
	        	break;
		}
	}

    private void styleControlButton(JButton button, Color color) {
        button.setBackground(color);
        button.setFocusable(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(100, 40));
        
        // manually adjust on hover
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
            	if (button.isEnabled()) {
            		button.setBackground(Color.BLACK);
            		button.setForeground(Color.WHITE);
            	}
            }
            public void mouseExited(MouseEvent e) {
            	button.setBackground(color);
            	button.setForeground(Color.BLACK);
            }
        });
    }
}
