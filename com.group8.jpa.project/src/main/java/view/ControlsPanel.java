package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.*;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class ControlsPanel extends JPanel {	
    // Control buttons
    private final JButton playButton = new JButton("");
    private final JButton pauseButton = new JButton("");
    private final JButton stopButton = new JButton("");
    private final JButton nextButton = new JButton("");
    private final JButton prevButton = new JButton("");
	
	private SongListener songListener;
	
	public ControlsPanel() {
		setMinimumSize(new Dimension(650, 60));
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(0, 10, 10, 10));

        // Initially disable pause button
        toggle_play_and_pause("Play");
        
        // Buttons section
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        buttonsPanel.add(prevButton);
        buttonsPanel.add(playButton);
        buttonsPanel.add(pauseButton);
        buttonsPanel.add(stopButton);
        buttonsPanel.add(nextButton);
        add(buttonsPanel, BorderLayout.CENTER);
        
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
        
        nextButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
            	SongEvent songEvent = new SongEvent(this, "Next");
                
                if (getSongListener() != null) {
                	getSongListener().songEventOccured(songEvent);
                }
            }
        });
        
        prevButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
            	SongEvent songEvent = new SongEvent(this, "Previous");
                
                if (getSongListener() != null) {
                	getSongListener().songEventOccured(songEvent);
                }
            }
        });
	}

	public SongListener getSongListener() {
		return songListener;
	}

	public void setSongListener(SongListener songListener) {
		this.songListener = songListener;
	}
	
	public JButton getPlayButton() {
		return playButton;
	}

	public JButton getPauseButton() {
		return pauseButton;
	}

	public JButton getStopButton() {
		return stopButton;
	}

	public JButton getNextButton() {
		return nextButton;
	}

	public JButton getPrevButton() {
		return prevButton;
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
}
