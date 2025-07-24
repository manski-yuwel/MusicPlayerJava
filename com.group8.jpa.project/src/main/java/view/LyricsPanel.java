package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

public class LyricsPanel extends JPanel {
	private final JTextArea lyricsArea = new JTextArea();
	
	private JButton switchButton = new JButton("");

	public LyricsPanel() {
		setLayout(new BorderLayout(0, 10));
		setBorder(new EmptyBorder(10, 10, 10, 10));
		
		JPanel northPanel = new JPanel(new BorderLayout());
    	JLabel panelTitle = new JLabel("Lyrics");
    	panelTitle.setFont(new Font(panelTitle.getFont().getName(), Font.BOLD, 16));
    	
    	northPanel.add(panelTitle, BorderLayout.CENTER);
    	northPanel.add(switchButton, BorderLayout.EAST);
		
		// textArea style
		lyricsArea.setFocusable(false);
		lyricsArea.setLineWrap(true);
		lyricsArea.setWrapStyleWord(true);

		JScrollPane lyricsScrollPane = new JScrollPane(lyricsArea);
		lyricsScrollPane.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));
        
		add(northPanel, BorderLayout.NORTH);
		add(lyricsScrollPane, BorderLayout.CENTER);
	}
	
    public JButton getSwitchButton() {
		return switchButton;
	}

	public JTextArea getLyricsArea() {
		return lyricsArea;
	}
	
}
