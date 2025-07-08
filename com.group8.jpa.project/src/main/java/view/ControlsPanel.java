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
import javax.swing.border.LineBorder;

public class ControlsPanel extends JPanel {
    // Control buttons
    private final JButton playButton = new JButton("Play");
    private final JButton pauseButton = new JButton("Pause");
    private final JButton stopButton = new JButton("Stop");
	
	private SongListener songListener;
	
	public ControlsPanel() {
        setLayout(new BorderLayout(10, 10));
        setBorder(new EmptyBorder(20, 10, 20, 10));
        
        // Style control buttons
        styleControlButton(playButton, new Color(155, 82, 224));
        styleControlButton(pauseButton, Color.ORANGE);
        styleControlButton(stopButton, Color.RED);
        
        // Initially disable pause button
        pauseButton.setEnabled(false);
        
        // Buttons section
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonsPanel.add(playButton);
        buttonsPanel.add(pauseButton);
        buttonsPanel.add(stopButton);
        
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
            		button.setBorder(new LineBorder(Color.BLACK, 2, false));
            	}
            }
            public void mouseExited(MouseEvent e) {
            	button.setBorder(new EmptyBorder(10, 20, 10, 20));
            }
        });
    }
}
