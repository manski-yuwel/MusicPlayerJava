package view;

import java.awt.Color;

import javax.swing.*;

public class App {
	public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable(){
            @Override
            public void run() {
                // bulk change color
            	// NOTE: Transfer the colorscheme stuff here
//            	UIManager.put("Panel.background", Color.WHITE);
//                UIManager.put("Button.background", new Color(152, 44, 210));
                
                 new Main();
            }
        });
	}
}
