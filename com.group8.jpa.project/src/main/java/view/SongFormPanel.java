package view;

import model.Song;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import javax.sound.sampled.*;
import javax.swing.*;

public class SongFormPanel extends JPanel {
	private File songFile = null;
	private File artFile = null;
	private File lyricsFile = null;
	private String duration = null;
	private JTextField titleField = new JTextField();
	private JTextField artistField = new JTextField();

	private JButton uploadSong = new JButton("Upload Song");
	private JButton uploadArt = new JButton("Upload Album Art");
	private JButton uploadLyrics = new JButton("Upload Lyrics");
	
	private JButton addButton = new JButton("Add");
	private JButton cancelButton = new JButton("Cancel");
	
	private FormListener formListener;

	public SongFormPanel() {
		setLayout(new BorderLayout());
		
		uploadSong.setFocusable(false);
		uploadArt.setFocusable(false);
		uploadLyrics.setFocusable(false);
		addButton.setFocusable(false);
		cancelButton.setFocusable(false);
		
		JPanel northPanel = new JPanel(new BorderLayout());
    	JLabel panelTitle = new JLabel("Add New Song");
    	panelTitle.setFont(new Font(panelTitle.getFont().getName(), Font.BOLD, 16));
    	northPanel.add(panelTitle, BorderLayout.CENTER);
    	
    	// center - form
    	JPanel centerPanel = new JPanel(new GridBagLayout());
    	GridBagConstraints gbc = new GridBagConstraints();
    	gbc.fill = GridBagConstraints.HORIZONTAL;
    	gbc.anchor = GridBagConstraints.CENTER;
    	gbc.insets = new Insets(0,5,5,5);
        
        // title name
    	gbc.gridx = 0;
    	gbc.gridy = 0;
    	gbc.gridheight = 1;
    	gbc.gridwidth = 1;
    	gbc.weightx = 1.0;
        centerPanel.add(new JLabel("Title Name"), gbc);
        gbc.gridx = 1;
        gbc.gridwidth = 1;
        centerPanel.add(titleField, gbc);
        
        // artist name
        gbc.gridx = 0;
    	gbc.gridy = 1;
    	gbc.gridheight = 1;
    	gbc.gridwidth = 1;
    	gbc.weightx = 1.0;
        centerPanel.add(new JLabel("Artist Name"), gbc);
        gbc.gridx = 1;
        gbc.gridwidth = 1;
        centerPanel.add(artistField, gbc);
    	
        // song file
    	gbc.gridx = 0;
    	gbc.gridy = 2;
    	gbc.gridheight = 1;
    	gbc.gridwidth = 1;
    	gbc.weightx = 1.0;
        centerPanel.add(new JLabel("Song File (wav/mp3 only)"), gbc);
        gbc.gridx = 1;
        gbc.gridwidth = 1;
        centerPanel.add(uploadSong, gbc);
        
        // art file
    	gbc.gridx = 0;
    	gbc.gridy = 3;
    	gbc.gridheight = 1;
    	gbc.gridwidth = 1;
    	gbc.weightx = 1.0;
        centerPanel.add(new JLabel("Album Art File (jpg/png only)"), gbc);
        gbc.gridx = 1;
        gbc.gridwidth = 1;
        centerPanel.add(uploadArt, gbc);
        
        // lyrics file
    	gbc.gridx = 0;
    	gbc.gridy = 4;
    	gbc.gridheight = 1;
    	gbc.gridwidth = 1;
    	gbc.weightx = 1.0;
        centerPanel.add(new JLabel("Lyrics File (txt only)"), gbc);
        gbc.gridx = 1;
        gbc.gridwidth = 1;
        centerPanel.add(uploadLyrics, gbc);
        
        // south - add/cancel button
        JPanel southPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        southPanel.add(addButton);
        southPanel.add(cancelButton);
        
        // connect buttons to filechooser
        uploadSong.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	try {
            		File file = getFileFromFileChooser();

            		// check file extension
            		if (getFileExtension(file).equals("wav")) {
            			songFile = file;
            			uploadSong.setText(file.getName());
            		} else {
            			throw new IOException("Wrong file format. It needs to be a wav file");
            		}
             	} catch (Exception err) {
            		JOptionPane.showMessageDialog(SongFormPanel.this, 
                            err, 
                            "Error", 
                            JOptionPane.WARNING_MESSAGE);
            	}
            	
            }
        });
        
        uploadArt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	try {
            		File file = getFileFromFileChooser();

            		// check file extension
            		if (getFileExtension(file).equals("jpg") || 
        				getFileExtension(file).equals("png")) {
            			artFile = file;
            			uploadArt.setText(file.getName());
            		} else {
            			throw new IOException("Wrong file format. It needs to be a jpg/png");
            		}
             	} catch (Exception err) {
            		JOptionPane.showMessageDialog(SongFormPanel.this, 
                            err, 
                            "Error", 
                            JOptionPane.WARNING_MESSAGE);
            	}
            	
            }
        });
        
        uploadLyrics.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	try {
            		File file = getFileFromFileChooser();

            		// check file extension
            		if (getFileExtension(file).equals("txt")) {
            			lyricsFile = file;
            			uploadLyrics.setText(file.getName());
            		} else {
            			throw new IOException("Wrong file format. It needs to be a txt file");
            		}
             	} catch (Exception err) {
            		JOptionPane.showMessageDialog(SongFormPanel.this, 
                            err, 
                            "Error", 
                            JOptionPane.WARNING_MESSAGE);
            	}
            	
            }
        });

        // add song button
        addButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent evt) {
        		try {
        			// check if textfields are empty
        			if (titleField.getText().trim().isEmpty() ||
    					artistField.getText().trim().isEmpty()	) {
        				throw new IOException("Please fill out all fields.");
        			}
        			
        			// get song duration
        			duration = getDuration(songFile);
        			
        			FormEvent formEvent = new FormEvent(
        					this,
        					titleField.getText(),
        					artistField.getText(),
        					duration,
        					lyricsFile.getName().substring(0, lyricsFile.getName().lastIndexOf('.')), // remove extension from lyrics
        					artFile.getName(),
        					songFile.getName()
        					);
        			
        			// create copy of files to resources
        			saveToResourceFolder("lyrics", lyricsFile);
        			saveToResourceFolder("images", artFile);
        			saveToResourceFolder("audio", songFile);
                    
					if (formListener != null) {
						formListener.formEventOccured(formEvent);
					}
					
					JOptionPane.showMessageDialog(SongFormPanel.this, 
                            "Successfully added " + titleField.getText() + " - " + artistField.getText(), 
                            "Added Song", 
                            JOptionPane.INFORMATION_MESSAGE);
					  
					// clear fields
					clearInputs();

        		} catch (Exception err) {
        			JOptionPane.showMessageDialog(SongFormPanel.this, 
                            err, 
                            "Error", 
                            JOptionPane.WARNING_MESSAGE);
            	}
            }
        });
        
        // cancel, go back to song list
        cancelButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent evt) {
        		// clear fields
                clearInputs();
        	}
        });

        add(northPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(southPanel, BorderLayout.SOUTH);
		
	}
	
	public FormListener getFormListener() {
		return formListener;
	}

	public void setFormListener(FormListener formListener) {
		this.formListener = formListener;
	}

	public JButton getCancelButton() {
		return cancelButton;
	}

	private File getFileFromFileChooser() {
		JFileChooser chooser = new JFileChooser();
		File file = null;
        int result = chooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            file = chooser.getSelectedFile();
        }
        
        return file;
	}
	
	private String getFileExtension(File file) {
		String fileName = file.getName();
		int lastDot = fileName.lastIndexOf('.');
		if (lastDot > 0 && lastDot < fileName.length() - 1) {
			return fileName.substring(lastDot + 1).toLowerCase();
		} else {
			return "";
		}
	}
	
	private String getDuration(File songFile) {
		int durationInSeconds = 0;
		try {
			AudioInputStream audioInputStream;
			audioInputStream = AudioSystem.getAudioInputStream(songFile);
			AudioFormat format = audioInputStream.getFormat();
	        long frames = audioInputStream.getFrameLength();
	        float frameRate = format.getFrameRate();

	        durationInSeconds = (int) (frames / frameRate);	
		} catch (UnsupportedAudioFileException | IOException e) {
			e.printStackTrace();
		}
		
		int minutes = durationInSeconds / 60;
        int seconds = durationInSeconds % 60;
        return String.format("%02d:%02d", minutes, seconds);
	}
	
	private void saveToResourceFolder(String type, File file) {
		String folderPath = System.getProperty("user.dir") + "/resource/" + type + "/";
		File destFile = new File(folderPath, file.getName());
		
		try {
			Files.copy(file.toPath(), destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void clearInputs() {
		titleField.setText("");
		artistField.setText("");
		duration = null;
		lyricsFile = null;
		artFile = null;
		songFile = null;
		
		uploadSong.setText("Upload Song");
		uploadArt.setText("Upload Album Art");
		uploadLyrics.setText("Upload Lyrics");
	}
}
