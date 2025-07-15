package view;

import controller.Controller;
import model.Song;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;

public class MusicPlayerUI_JPA extends JFrame{
    private LyricsPanel lyricsPanel = new LyricsPanel();
    private SongListPanel songListPanel = new SongListPanel();
    private NowPlayingPanel nowPlayingPanel = new NowPlayingPanel();
    private ControlsPanel controlsPanel = new ControlsPanel();
    
    private CardLayout card = new CardLayout();
    private JPanel cardPanel = new JPanel(card) {
        @Override
        public Dimension getPreferredSize() {
        	// workaround cause JTable is hogging space :(
        	if (getParent() != null) {
        		int totalWidth = getParent().getWidth();
        		int width = (int) (getParent().getWidth() / 3.0);
                return new Dimension(width, getParent().getHeight());
        	}
        	
            return super.getPreferredSize();
        }
    };

    private JPanel centerPanel = new JPanel(new GridBagLayout());
    private GridBagConstraints gbc = new GridBagConstraints();

    private Controller controller = new Controller();
    
    // Audio components
    private Clip currentClip;
    private AudioInputStream audioInputStream;
    private boolean isPaused = false;
    private long pausePosition = 0;

    public MusicPlayerUI_JPA(){
    	setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1000, 600);
        setTitle("Music Player");
        
        // north - title
        JPanel titlePanel = new JPanel();
        
        JLabel titleLabel = new JLabel("JAVA Music Player");
        Font defaultStyle = titleLabel.getFont();
        titleLabel.setFont(new Font(defaultStyle.getName(), Font.BOLD, 28));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        titlePanel.add(titleLabel);
        
        // center - gridbag
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(5, 5, 5, 5);
        
        // col 1 - player
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridheight = 1;
        gbc.weightx = 2.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        centerPanel.add(nowPlayingPanel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weighty = 0.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        centerPanel.add(controlsPanel, gbc);

        // col 2 - card
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridheight = 3;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        centerPanel.add(cardPanel, gbc);
        
        cardPanel.add(songListPanel, "songList");
        cardPanel.add(lyricsPanel, "lyrics");
 
        // connect db to songlist using controller
        if (controller.getSongs().isEmpty()) {
        	addInitialSongs();
        	songListPanel.refresh();
        }
        songListPanel.setData(controller.getSongs());
        
        // custom listener
        controlsPanel.setSongListener(new SongListener(){
            public void songEventOccured(SongEvent e){
            	Song selectedSong = songListPanel.getSelectedSong();
            	
            	try {
            		e.setSong_data(selectedSong);
            	} catch (Exception err) {
            		
            	}
            	
            	switch(e.getCommand()){
            	
	                case "Play": 
	                	playCurrentSong(e);
	                	break;
	                case "Pause": 
	                	pauseCurrentSong();
	                	break;
	                case "Stop": 
	                	stopCurrentSong();
	                	break;
            	}
            }
        });
        
        // card switch listener
        // NOTE: convert to custom event later
        songListPanel.getSwitchButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
            	changeCard("lyrics");
            }
        });
        lyricsPanel.getSwitchButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
            	changeCard("songList");
            }
        });

        // Song table selection
        songListPanel.getTable().getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                loadSelectedSong();
            }
        });
   
        add(titlePanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        
        setVisible(true);
    }
    
    private void addInitialSongs() {
    	String lyricsPath = System.getProperty("user.dir") + "/resources/lyrics/";
    	
        Song[] initialSongs = {
            new Song("Bohemian Rhapsody", "Queen", "5:59",
        		getTextFromFile(lyricsPath,"bohemian_rhapsody"),
                "queen_bohemian.jpg", "bohemian_rhapsody.wav"),
            
            new Song("Hotel California", "Eagles", "6:31",
            		getTextFromFile(lyricsPath,"hotel_california"),
            		"eagles_hotel.jpg", "hotel_california.wav"),
            
            new Song("Imagine", "John Lennon", "3:14",
            		getTextFromFile(lyricsPath,"imagine"),
            		"john_lennon_imagine.jpg", "imagine.wav"),
            
            new Song("Stairway to Heaven", "Led Zeppelin", "8:05",
            		getTextFromFile(lyricsPath,"stairway_to_heaven"),
            		"led_zeppelin_stairway.jpg", "stairway_to_heaven.wav"),
            
            new Song("Purple Rain", "Prince", "8:40",
            		getTextFromFile(lyricsPath,"purple_rain"),
            		"prince_purple_rain.jpg", "purple_rain.wav")
        };
        
        for (Song song : initialSongs) {
            try {
            	controller.addSong(new SongEvent(this, song, ""));
                System.out.println("Saved song: " + song.getTitle());
            } catch (Exception e) {
                System.err.println("Error saving song: " + song.getTitle() + " - " + e.getMessage());
            }
        }
    }
    
    // get lyrics from txt file
    private String getTextFromFile(String folderpath, String filename) {
    	File textfile = new File(folderpath + filename + ".txt");
    	
    	StringBuilder text = new StringBuilder();
    	
    	// parse contents to string
        try (BufferedReader reader = new BufferedReader(new FileReader(textfile))) {
            String next;
            while ((next = reader.readLine()) != null) {
            	text.append(next + "\n");
            }
        } catch (IOException err) {
        	JOptionPane.showMessageDialog(this, 
        			"Error reading file: " + err.getMessage(), 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
        }
    	
    	return text.toString();
    }
    
    // load song data to display
    private void loadSelectedSong() {
        Song selectedSong = songListPanel.getSelectedSong();
        
        if (selectedSong != null) {
            // Update UI
            nowPlayingPanel.getNowPlayingLabel().setText(selectedSong.getTitle());
            nowPlayingPanel.getArtistNameLabel().setText(selectedSong.getArtist());
            lyricsPanel.getLyricsArea().setText(selectedSong.getLyrics());
            loadAlbumArt(selectedSong.getAlbumArtPath());
            
            // Reset progress
            nowPlayingPanel.getProgressBar().setValue(0);
            nowPlayingPanel.getProgressBar().setString("Ready to play: " + selectedSong.getTitle());
            nowPlayingPanel.getCurrentTimeLabel().setText("00:00");
            nowPlayingPanel.getTimeLabel().setText(selectedSong.getDuration());
            
            // Stop current song if playing
            stopCurrentSong();
        }
    }

    private void loadAlbumArt(String imagePath) {
        try {
        	String folderPath = System.getProperty("user.dir") + "/resource/images/";
        	
            // Try to load from resources or use placeholder
            ImageIcon icon = new ImageIcon(folderPath + imagePath);
            if (icon.getIconWidth() > 0) {
            	int targetSize = nowPlayingPanel.getAlbumArtLabel().getHeight();
                Image img = icon.getImage().getScaledInstance(targetSize, targetSize, Image.SCALE_SMOOTH);
            	
                nowPlayingPanel.getAlbumArtLabel().setIcon(new ImageIcon(img));
                nowPlayingPanel.getAlbumArtLabel().setText("");
            } else {
                setPlaceholderAlbumArt();
            }
        } catch (Exception e) {
            setPlaceholderAlbumArt();
        }
    }

    private void setPlaceholderAlbumArt() {
        nowPlayingPanel.getAlbumArtLabel().setIcon(null);
        nowPlayingPanel.getAlbumArtLabel().setText("<html><center>🎵<br>Album Art<br>Not Available</center></html>");
        nowPlayingPanel.getAlbumArtLabel().setFont(new Font("Segoe UI", Font.PLAIN, 14));
        nowPlayingPanel.getAlbumArtLabel().setForeground(Color.GRAY);
    }
 
    private void playCurrentSong(SongEvent evt) {  
        Song selectedSong = evt.getSong_data();
        
        if (selectedSong != null) {
            try {
                if (isPaused && currentClip != null) {
                    // Resume from pause
                    currentClip.setMicrosecondPosition(pausePosition);
                    currentClip.start();
                    isPaused = false;
                } else {
                    // Stop any current playback
                    stopCurrentSong();
                    
                    // Load audio file from src/audio folder
                	String folderPath = System.getProperty("user.dir") + "/resource/audio/";
                    String audioPath = folderPath + selectedSong.getAudioFilePath();
                    File audioUrl = new File(audioPath);
                    
                	if (audioUrl.exists()) {
                        audioInputStream = AudioSystem.getAudioInputStream(audioUrl);
                        currentClip = AudioSystem.getClip();
                        currentClip.open(audioInputStream);
                        currentClip.start();
                        
                        // Start real progress tracking
                        startProgressTracking();
                        
                        // connect clip to volume
                        controlsPanel.getVolumePanel().setAudioClip(currentClip);
                    } else {
                        throw new IOException("Audio file not found: " + audioPath);
                    }
                }

                nowPlayingPanel.getProgressBar().setString("Playing: " + selectedSong.getTitle());
                controlsPanel.toggle_play_and_pause("Pause");
                
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, 
                    "Error playing song: " + e.getMessage() + 
                    "\nMake sure WAV files are in src/audio/ folder", 
                    "Playback Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, 
                "Please select a song from the list first.", 
                "No Song Selected", 
                JOptionPane.WARNING_MESSAGE);
        }
    }
    
    private void startProgressTracking() {
        Timer progressTimer = new Timer(100, e -> {
            if (currentClip != null && !isPaused) {
                long currentPosition = currentClip.getMicrosecondPosition();
                long totalLength = currentClip.getMicrosecondLength();
                
                if (totalLength > 0) {
                    int progress = (int) ((currentPosition * 100) / totalLength);
                    nowPlayingPanel.getProgressBar().setValue(progress);
                    
                    // Update time display
                    String currentTime = formatTime(currentPosition / 1000000);
                    String totalTime = formatTime(totalLength / 1000000);
                    nowPlayingPanel.getCurrentTimeLabel().setText(currentTime);
                    nowPlayingPanel.getTimeLabel().setText(totalTime);
                }
                
                // Check if song finished
                if (!currentClip.isRunning() && !isPaused) {
                    stopCurrentSong();
                    ((Timer) e.getSource()).stop();
                }
            }
        });
        
        nowPlayingPanel.getProgressBar().setMaximum(100);
        progressTimer.start();
    }
    
    private String formatTime(long seconds) {
        long minutes = seconds / 60;
        long secs = seconds % 60;
        return String.format("%02d:%02d", minutes, secs);
    }
    
    private void stopCurrentSong() {
        if (currentClip != null) {
            currentClip.stop();
            currentClip.close();
            currentClip = null;
        }
        
        if (audioInputStream != null) {
            try {
                audioInputStream.close();
            } catch (IOException e) {
                // Handle silently
            }
            audioInputStream = null;
        }
        
        isPaused = false;
        pausePosition = 0;
        controlsPanel.toggle_play_and_pause("Play");
        nowPlayingPanel.getProgressBar().setValue(0);
        
        Song selectedSong = songListPanel.getSelectedSong();

        if (selectedSong != null) {
            nowPlayingPanel.getProgressBar().setString("Stopped: " + selectedSong.getTitle());
        } else {
        	nowPlayingPanel.getProgressBar().setString("No song selected");
        }
        
    	nowPlayingPanel.getCurrentTimeLabel().setText("00:00");
    	nowPlayingPanel.getTimeLabel().setText("00:00");
    }
    
    private void pauseCurrentSong() {
        if (currentClip != null && currentClip.isRunning()) {
            pausePosition = currentClip.getMicrosecondPosition();
            currentClip.stop();
            isPaused = true;
            
            controlsPanel.toggle_play_and_pause("Play");
            nowPlayingPanel.getProgressBar().setString("Paused");
        }
    }
    
    private void changeCard(String target) {
        card.show(cardPanel, target);
    }
    
}
