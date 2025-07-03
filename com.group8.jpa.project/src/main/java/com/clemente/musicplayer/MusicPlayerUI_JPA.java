package com.clemente.musicplayer;

import javax.sound.sampled.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.clemente.musicplayer.dao.SongDAO;
import com.clemente.musicplayer.entity.Song;

public class MusicPlayerUI_JPA extends JFrame {
    private static final long serialVersionUID = 1L;

    // Audio components
    private Clip currentClip;
    private AudioInputStream audioInputStream;
    private boolean isPaused = false;
    private long pausePosition = 0;

    // UI Components
    private final JTable songTable;
    private final DefaultTableModel tableModel;
    private final JTextArea lyricsArea = new JTextArea(15, 40);
    private final JLabel albumArtLabel = new JLabel();
    private final JLabel nowPlayingLabel = new JLabel("No song selected");
    private final JProgressBar progressBar = new JProgressBar();
    private final JLabel timeLabel = new JLabel("00:00 / 00:00");

    // Control buttons
    private final JButton playButton = new JButton("Play");
    private final JButton pauseButton = new JButton("Pause");
    private final JButton stopButton = new JButton("Stop");

    // Color scheme
    private final Color PRIMARY_COLOR = new Color(138, 43, 226);
    private final Color SECONDARY_COLOR = new Color(75, 0, 130);
    private final Color ACCENT_COLOR = new Color(255, 20, 147);
    private final Color BACKGROUND_COLOR = new Color(240, 240, 250);
    private final Color PANEL_COLOR = Color.WHITE;

    // Database components - Replace HashMap with JPA
    private final SongDAO songDAO = new SongDAO();
    private List<Song> songList = new ArrayList<>();

    public MusicPlayerUI_JPA() {
        super("Music Player - Java Edition (JPA)");
        
        // Initialize database and load songs
        initializeDatabase();
        loadSongsFromDatabase();
        
        // Initialize table
        String[] columnNames = {"Title", "Artist", "Duration"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        songTable = new JTable(tableModel);
        
        initializeUI();
        layoutComponents();
        setupEventListeners();
        populateSongTable();
        
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(1000, 700));
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initializeDatabase() {
        try {
            // Check if songs already exist in database
            List<Song> existingSongs = songDAO.getAllSongs();
            if (existingSongs.isEmpty()) {
                // Add initial song data
                addInitialSongs();
                System.out.println("Initial songs added to database");
            } else {
                System.out.println("Found " + existingSongs.size() + " songs in database");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Database connection error: " + e.getMessage(),
                "Database Error",
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    
    private void addInitialSongs() {
        Song[] initialSongs = {
            new Song("Bohemian Rhapsody", "Queen", "6:07",
                "Is this the real life?\nIs this just fantasy?\nCaught in a landslide\nNo escape from reality\n\nOpen your eyes\nLook up to the skies and see\nI'm just a poor boy\nI need no sympathy\nBecause I'm easy come, easy go\nLittle high, little low\nAny way the wind blows\nDoesn't really matter to me, to me\n\nMama, just killed a man\nPut a gun against his head\nPulled my trigger, now he's dead\nMama, life had just begun\nBut now I've gone and thrown it all away\n\nMama, ooh\nDidn't mean to make you cry\nIf I'm not back again this time tomorrow\nCarry on, carry on as if nothing really matters",
                "queen_bohemian.jpg", "bohemian_rhapsody.wav"),
            
            new Song("Hotel California", "Eagles", "6:31",
                "On a dark desert highway\nCool wind in my hair\nWarm smell of colitas\nRising up through the air\n\nUp ahead in the distance\nI saw a shimmering light\nMy head grew heavy and my sight grew dim\nI had to stop for the night\n\nThere she stood in the doorway\nI heard the mission bell\nAnd I was thinking to myself\nThis could be Heaven or this could be Hell\n\nThen she lit up a candle\nAnd she showed me the way\nThere were voices down the corridor\nI thought I heard them say\n\nWelcome to the Hotel California\nSuch a lovely place (Such a lovely place)\nSuch a lovely face\nPlenty of room at the Hotel California\nAny time of year (Any time of year)\nYou can find it here",
                "eagles_hotel.jpg", "hotel_california.wav"),
            
            new Song("Imagine", "John Lennon", "3:07",
                "Imagine there's no heaven\nIt's easy if you try\nNo hell below us\nAbove us only sky\nImagine all the people living for today\n\nImagine there's no countries\nIt isn't hard to do\nNothing to kill or die for\nAnd no religion too\nImagine all the people living life in peace\n\nYou may say I'm a dreamer\nBut I'm not the only one\nI hope someday you'll join us\nAnd the world will be as one\n\nImagine no possessions\nI wonder if you can\nNo need for greed or hunger\nA brotherhood of man\nImagine all the people sharing all the world\n\nYou may say I'm a dreamer\nBut I'm not the only one\nI hope someday you'll join us\nAnd the world will be as one",
                "john_lennon_imagine.jpg", "imagine.wav"),
            
            new Song("Stairway to Heaven", "Led Zeppelin", "8:02",
                "There's a lady who's sure\nAll that glitters is gold\nAnd she's buying a stairway to heaven\n\nWhen she gets there she knows\nIf the stores are all closed\nWith a word she can get what she came for\n\nOoh, ooh, and she's buying a stairway to heaven\n\nThere's a sign on the wall\nBut she wants to be sure\n'Cause you know sometimes words have two meanings\n\nIn a tree by the brook\nThere's a songbird who sings\nSometimes all of our thoughts are misgiven\n\nOoh, it makes me wonder\nOoh, it makes me wonder\n\nThere's a feeling I get\nWhen I look to the west\nAnd my spirit is crying for leaving\n\nIn my thoughts I have seen\nRings of smoke through the trees\nAnd the voices of those who stand looking",
                "led_zeppelin_stairway.jpg", "stairway_to_heaven.wav"),
            
            new Song("Purple Rain", "Prince", "8:41",
                "I never meant to cause you any sorrow\nI never meant to cause you any pain\nI only wanted to one time to see you laughing\nI only wanted to see you\nLaughing in the purple rain\n\nPurple rain, purple rain\nPurple rain, purple rain\nPurple rain, purple rain\nI only wanted to see you\nBathing in the purple rain\n\nI never wanted to be your weekend lover\nI only wanted to be some kind of friend\nBaby, I could never steal you from another\nIt's such a shame our friendship had to end\n\nPurple rain, purple rain\nPurple rain, purple rain\nPurple rain, purple rain\nI only wanted to see you\nUnderneath the purple rain",
                "prince_purple_rain.jpg", "purple_rain.wav")
        };
        
        for (Song song : initialSongs) {
            try {
                songDAO.saveSong(song);
                System.out.println("Saved song: " + song.getTitle());
            } catch (Exception e) {
                System.err.println("Error saving song: " + song.getTitle() + " - " + e.getMessage());
            }
        }
    }
    
    private void loadSongsFromDatabase() {
        try {
            songList = songDAO.getAllSongs();
            System.out.println("Loaded " + songList.size() + " songs from database");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Error loading songs from database: " + e.getMessage(),
                "Database Error",
                JOptionPane.ERROR_MESSAGE);
            songList = new ArrayList<>();
            e.printStackTrace();
        }
    }

    private void initializeUI() {
        getContentPane().setBackground(BACKGROUND_COLOR);
        
        // Style the song table
        songTable.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        songTable.setRowHeight(30);
        songTable.setGridColor(new Color(220, 220, 220));
        songTable.setSelectionBackground(PRIMARY_COLOR.brighter());
        songTable.setSelectionForeground(Color.BLACK);
        songTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        // Style table header
        JTableHeader header = songTable.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 12));
        header.setBackground(PRIMARY_COLOR);
        header.setForeground(Color.BLACK);
        header.setReorderingAllowed(false);
        
        // Set column widths
        songTable.getColumnModel().getColumn(0).setPreferredWidth(300); // Title
        songTable.getColumnModel().getColumn(1).setPreferredWidth(200); // Artist
        songTable.getColumnModel().getColumn(2).setPreferredWidth(80);  // Duration
        
        // Style lyrics area
        lyricsArea.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lyricsArea.setBackground(new Color(248, 249, 250));
        lyricsArea.setBorder(new EmptyBorder(15, 15, 15, 15));
        lyricsArea.setEditable(false);
        lyricsArea.setLineWrap(true);
        lyricsArea.setWrapStyleWord(true);
        
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
        
        // Style progress bar
        progressBar.setStringPainted(true);
        progressBar.setString("No song loaded");
        progressBar.setBackground(Color.WHITE);
        progressBar.setForeground(ACCENT_COLOR);
        
        // Style time label
        timeLabel.setFont(new Font("Consolas", Font.PLAIN, 12));
        timeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        // Style control buttons
        styleControlButton(playButton, SECONDARY_COLOR);
        styleControlButton(pauseButton, Color.ORANGE);
        styleControlButton(stopButton, Color.RED);
        
        // Initially disable pause button
        pauseButton.setEnabled(false);
    }

    private void styleControlButton(JButton button, Color color) {
        button.setFont(new Font("Segoe UI", Font.BOLD, 12));
        button.setBackground(color);
        button.setForeground(Color.BLACK);
        button.setFocusPainted(false);
        button.setBorder(new EmptyBorder(10, 20, 10, 20));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(100, 40));
    }

    private void layoutComponents() {
        setLayout(new BorderLayout(10, 10));
        
        // Main container
        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(BACKGROUND_COLOR);
        
        // Title panel
        JPanel titlePanel = createTitlePanel();
        
        // Left panel (song list)
        JPanel leftPanel = createSongListPanel();
        
        // Right panel (player controls and lyrics)
        JPanel rightPanel = createPlayerPanel();
        
        mainPanel.add(titlePanel, BorderLayout.NORTH);
        mainPanel.add(leftPanel, BorderLayout.WEST);
        mainPanel.add(rightPanel, BorderLayout.CENTER);
        
        add(mainPanel);
    }

    private JPanel createTitlePanel() {
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(BACKGROUND_COLOR);
        
        JLabel titleLabel = new JLabel("JAVA Music Player");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(PRIMARY_COLOR);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        titlePanel.add(titleLabel);
        return titlePanel;
    }

    private JPanel createSongListPanel() {
        JPanel songListPanel = new JPanel(new BorderLayout());
        songListPanel.setPreferredSize(new Dimension(400, 500));
        songListPanel.setBorder(BorderFactory.createCompoundBorder(
            new TitledBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)), 
                           "Song Library", 
                           TitledBorder.LEFT, 
                           TitledBorder.TOP, 
                           new Font("Segoe UI", Font.BOLD, 14), 
                           PRIMARY_COLOR),
            new EmptyBorder(10, 10, 10, 10)
        ));
        
        JScrollPane tableScrollPane = new JScrollPane(songTable);
        tableScrollPane.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));
        songListPanel.add(tableScrollPane);
        
        return songListPanel;
    }

    private JPanel createPlayerPanel() {
        JPanel playerPanel = new JPanel(new BorderLayout(10, 10));
        
        // Now playing section
        JPanel nowPlayingPanel = new JPanel(new BorderLayout());
        nowPlayingPanel.setBorder(BorderFactory.createCompoundBorder(
            new TitledBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)), 
                           "Now Playing", 
                           TitledBorder.LEFT, 
                           TitledBorder.TOP, 
                           new Font("Segoe UI", Font.BOLD, 14), 
                           PRIMARY_COLOR),
            new EmptyBorder(10, 10, 10, 10)
        ));
        
        nowPlayingPanel.add(nowPlayingLabel, BorderLayout.NORTH);
        nowPlayingPanel.add(albumArtLabel, BorderLayout.CENTER);
        
        // Controls section
        JPanel controlsPanel = createControlsPanel();
        
        // Lyrics section
        JPanel lyricsPanel = new JPanel(new BorderLayout());
        lyricsPanel.setBorder(BorderFactory.createCompoundBorder(
            new TitledBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)), 
                           "Lyrics", 
                           TitledBorder.LEFT, 
                           TitledBorder.TOP, 
                           new Font("Segoe UI", Font.BOLD, 14), 
                           PRIMARY_COLOR),
            new EmptyBorder(10, 10, 10, 10)
        ));
        
        JScrollPane lyricsScrollPane = new JScrollPane(lyricsArea);
        lyricsScrollPane.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));
        lyricsPanel.add(lyricsScrollPane);
        
        playerPanel.add(nowPlayingPanel, BorderLayout.NORTH);
        playerPanel.add(controlsPanel, BorderLayout.CENTER);
        playerPanel.add(lyricsPanel, BorderLayout.SOUTH);
        
        return playerPanel;
    }

    private JPanel createControlsPanel() {
        JPanel controlsPanel = new JPanel(new BorderLayout(10, 10));
        controlsPanel.setBorder(new EmptyBorder(20, 10, 20, 10));
        
        // Progress section
        JPanel progressPanel = new JPanel(new BorderLayout(5, 5));
        progressPanel.add(progressBar, BorderLayout.CENTER);
        progressPanel.add(timeLabel, BorderLayout.SOUTH);
        
        // Buttons section
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonsPanel.add(playButton);
        buttonsPanel.add(pauseButton);
        buttonsPanel.add(stopButton);
        
        controlsPanel.add(progressPanel, BorderLayout.NORTH);
        controlsPanel.add(buttonsPanel, BorderLayout.CENTER);
        
        return controlsPanel;
    }

    private void setupEventListeners() {
        // Song table selection
        songTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                loadSelectedSong();
            }
        });
        
        // Control buttons
        playButton.addActionListener(e -> playCurrentSong());
        pauseButton.addActionListener(e -> pauseCurrentSong());
        stopButton.addActionListener(e -> stopCurrentSong());
    }

    private void populateSongTable() {
        tableModel.setRowCount(0); // Clear existing rows
        for (Song song : songList) {
            Object[] row = {song.getTitle(), song.getArtist(), song.getDuration()};
            tableModel.addRow(row);
        }
    }

    private void loadSelectedSong() {
        int selectedRow = songTable.getSelectedRow();
        if (selectedRow >= 0 && selectedRow < songList.size()) {
            Song selectedSong = songList.get(selectedRow);
            
            // Update UI
            nowPlayingLabel.setText(selectedSong.getTitle() + " - " + selectedSong.getArtist());
            lyricsArea.setText(selectedSong.getLyrics());
            loadAlbumArt(selectedSong.getAlbumArtPath());
            
            // Reset progress
            progressBar.setValue(0);
            progressBar.setString("Ready to play: " + selectedSong.getTitle());
            timeLabel.setText("00:00 / " + selectedSong.getDuration());
            
            // Stop current song if playing
            stopCurrentSong();
        }
    }

    private void loadAlbumArt(String imagePath) {
        try {
            // Try to load from resources or use placeholder
            ImageIcon icon = new ImageIcon(getClass().getResource("/images/" + imagePath));
            if (icon.getIconWidth() > 0) {
                Image img = icon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
                albumArtLabel.setIcon(new ImageIcon(img));
                albumArtLabel.setText("");
            } else {
                setPlaceholderAlbumArt();
            }
        } catch (Exception e) {
            setPlaceholderAlbumArt();
        }
    }

    private void setPlaceholderAlbumArt() {
        albumArtLabel.setIcon(null);
        albumArtLabel.setText("<html><center>🎵<br>Album Art<br>Not Available</center></html>");
        albumArtLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        albumArtLabel.setForeground(Color.GRAY);
    }

    private void playCurrentSong() {
        int selectedRow = songTable.getSelectedRow();
        if (selectedRow >= 0 && selectedRow < songList.size()) {
            Song selectedSong = songList.get(selectedRow);
            
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
                    String audioPath = "/audio/" + selectedSong.getAudioFilePath();
                    var audioUrl = getClass().getResource(audioPath);
                    
                    if (audioUrl != null) {
                        audioInputStream = AudioSystem.getAudioInputStream(audioUrl);
                        currentClip = AudioSystem.getClip();
                        currentClip.open(audioInputStream);
                        currentClip.start();
                        
                        // Start real progress tracking
                        startProgressTracking();
                    } else {
                        throw new IOException("Audio file not found: " + audioPath);
                    }
                }
                
                playButton.setEnabled(false);
                pauseButton.setEnabled(true);
                progressBar.setString("Playing: " + selectedSong.getTitle());
                
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
                    progressBar.setValue(progress);
                    
                    // Update time display
                    String currentTime = formatTime(currentPosition / 1000000);
                    String totalTime = formatTime(totalLength / 1000000);
                    timeLabel.setText(currentTime + " / " + totalTime);
                }
                
                // Check if song finished
                if (!currentClip.isRunning() && !isPaused) {
                    stopCurrentSong();
                    ((Timer) e.getSource()).stop();
                }
            }
        });
        
        progressBar.setMaximum(100);
        progressTimer.start();
    }

    private String formatTime(long seconds) {
        long minutes = seconds / 60;
        long secs = seconds % 60;
        return String.format("%02d:%02d", minutes, secs);
    }

    private void pauseCurrentSong() {
        if (currentClip != null && currentClip.isRunning()) {
            pausePosition = currentClip.getMicrosecondPosition();
            currentClip.stop();
            isPaused = true;
            
            playButton.setEnabled(true);
            pauseButton.setEnabled(false);
            progressBar.setString("Paused");
        }
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
        playButton.setEnabled(true);
        pauseButton.setEnabled(false);
        progressBar.setValue(0);
        
        int selectedRow = songTable.getSelectedRow();
        if (selectedRow >= 0 && selectedRow < songList.size()) {
            Song selectedSong = songList.get(selectedRow);
            progressBar.setString("Stopped: " + selectedSong.getTitle());
            timeLabel.setText("00:00 / 00:00");
        } else {
            progressBar.setString("No song selected");
            timeLabel.setText("00:00 / 00:00");
        }
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            // Use default look and feel
        }
        
        SwingUtilities.invokeLater(MusicPlayerUI_JPA::new);
    }
}