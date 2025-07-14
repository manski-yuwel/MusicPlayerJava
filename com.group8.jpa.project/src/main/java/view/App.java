package view;

import java.awt.Color;
import java.awt.Font;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

public class App {
	public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable(){
            @Override
            public void run() {
            	// style setting
            	Color PRIMARY_COLOR = new Color(138, 43, 226);
            	Color ACCENT_COLOR = new Color(255, 20, 147);
            	Color BACKGROUND_COLOR = new Color(240, 240, 250);
            	
            	String FONT_NAME = "Segoe UI";
            	int FONT_SIZE = 12;
            	
                // bulk change themes
            	// TitledBorder
            	UIManager.put("TitledBorder.font", new Font(FONT_NAME, Font.BOLD, 14));
            	UIManager.put("TitledBorder.titleColor", PRIMARY_COLOR);
            	UIManager.put("TitledBorder.titlePosition", TitledBorder.LEFT);
            	UIManager.put("TitledBorder.titleJustification", TitledBorder.LEFT);
            	
            	// Table
            	UIManager.put("Table.font", new Font(FONT_NAME, Font.PLAIN, FONT_SIZE));
            	UIManager.put("Table.gridColor", new Color(220, 220, 220));
            	UIManager.put("Table.selectionBackground", PRIMARY_COLOR.brighter());
            	UIManager.put("Table.selectionForeground", Color.BLACK);

            	// TableHeader
            	UIManager.put("TableHeader.font", new Font(FONT_NAME, Font.BOLD, FONT_SIZE));
            	UIManager.put("TableHeader.background", PRIMARY_COLOR);
            	UIManager.put("TableHeader.foreground", Color.BLACK);
            	
            	// Button
            	UIManager.put("Button.font", new Font(FONT_NAME, Font.BOLD, FONT_SIZE));
            	UIManager.put("Button.foreground", Color.BLACK);
            	UIManager.put("Button.background", Color.CYAN);
            	UIManager.put("Button.border", new EmptyBorder(10, 20, 10, 20));
            	
        		// textArea
            	UIManager.put("TextArea.font", new Font(FONT_NAME, Font.PLAIN, FONT_SIZE));
            	UIManager.put("TextArea.background", BACKGROUND_COLOR.brighter());
            	UIManager.put("TextArea.border", new EmptyBorder(15, 15, 15, 15));

            	// Progress bar
            	UIManager.put("ProgressBar.background", Color.WHITE);
            	UIManager.put("ProgressBar.foreground", ACCENT_COLOR);
            	
            	// Label
            	UIManager.put("Label.foreground", PRIMARY_COLOR);
            	UIManager.put("Label.font", new Font(FONT_NAME, Font.PLAIN, FONT_SIZE));
            	
            	// JPanel
            	UIManager.put("Panel.background", BACKGROUND_COLOR);
            	
                 new MusicPlayerUI_JPA();
            }
        });
	}
}
