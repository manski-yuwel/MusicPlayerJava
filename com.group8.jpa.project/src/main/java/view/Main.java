package view;

import controller.Controller;

import java.awt.*;
import javax.swing.*;

// NOTE: This will be the MusicPlayerUI_JPA later
// Transfer the contents from here to that file when done, and remove any duplicates
public class Main extends JFrame{
    private LyricsPanel lyricsPanel = new LyricsPanel();
    private SongListPanel songListPanel = new SongListPanel();
    private NowPlayingPanel nowPlayingPanel = new NowPlayingPanel();
    
    private Controller controller = new Controller();
    
    // colors, NOTE: move this somewhere later
 	private final Color PRIMARY_COLOR = new Color(138, 43, 226);
 	private final Color SECONDARY_COLOR = new Color(75, 0, 130);
 	private final Color ACCENT_COLOR = new Color(255, 20, 147);
 	private final Color BACKGROUND_COLOR = new Color(240, 240, 250);
 	private final Color PANEL_COLOR = Color.WHITE;
    
    public Main(){
    	setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(700, 300);
        setTitle("Music Player");
        
        // north - title
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(BACKGROUND_COLOR);
        
        JLabel titleLabel = new JLabel("JAVA Music Player");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(PRIMARY_COLOR);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        titlePanel.add(titleLabel);
        
        // center - main
        JPanel centerPanel = new JPanel(new GridLayout(1,0));
        JPanel grid_col1 = new JPanel(new BorderLayout());
        JPanel grid_col2 = new JPanel(new BorderLayout());
        
        // col1 - nowPlaying and songList
        grid_col1.add(nowPlayingPanel, BorderLayout.NORTH);
        grid_col1.add(songListPanel, BorderLayout.CENTER);
        
        // col2 - lyrics
        grid_col2.add(lyricsPanel, BorderLayout.CENTER);
        
        centerPanel.add(grid_col1);
        centerPanel.add(grid_col2);
        
        songListPanel.setData(controller.getSongs());
        
        add(titlePanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        
        setVisible(true);
    }

	private LayoutManager GridLayout(int i, int j) {
		// TODO Auto-generated method stub
		return null;
	}
    
 
    
//    public static void main(String[] args) {
//        // bulk change color
//    	UIManager.put("Panel.background", Color.WHITE);
//        UIManager.put("Button.background", new Color(152, 44, 210));
//        UIManager.put("Button.foreground", Color.WHITE);
//        
//        new Main();
//    }
}
