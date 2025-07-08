package view;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

public class LyricsPanel extends JPanel {
	private final JTextArea lyricsArea = new JTextArea();

	public LyricsPanel() {
		setLayout(new BorderLayout());
		setBorder(BorderFactory.createCompoundBorder(
	            new TitledBorder("Lyrics"),
	            new EmptyBorder(10, 10, 10, 10)
	        ));
		
		// textArea style
		lyricsArea.setFocusable(false);
		lyricsArea.setLineWrap(true);
		lyricsArea.setWrapStyleWord(true);

		JScrollPane lyricsScrollPane = new JScrollPane(lyricsArea);
		lyricsScrollPane.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));
        
		add(lyricsScrollPane, BorderLayout.CENTER);
	}

	public JTextArea getLyricsArea() {
		return lyricsArea;
	}
	
}
