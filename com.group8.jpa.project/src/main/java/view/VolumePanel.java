package view;

import javax.swing.*;
import java.awt.*;
import javax.sound.sampled.*;

public class VolumePanel extends JPanel  {
	private JSlider volumeSlider;
    private Clip audioClip;
    private FloatControl volumeControl;

    public VolumePanel() {
    	setLayout(new BorderLayout());

        // Slider setup
        volumeSlider = new JSlider(0, 100, 70);
        volumeSlider.setMajorTickSpacing(25);

        // Slider listener
        volumeSlider.addChangeListener(e -> {
            if (volumeControl != null) {
                setVolume(volumeSlider.getValue());
            }
        });
        
        add(volumeSlider, BorderLayout.CENTER);
    }

	public void setAudioClip(Clip audioClip) {
		this.audioClip = audioClip;
		volumeControl = (FloatControl) audioClip.getControl(FloatControl.Type.MASTER_GAIN);
        setVolume(volumeSlider.getValue());
	}

	private void setVolume(int sliderValue) {
        float min = volumeControl.getMinimum();
        float max = volumeControl.getMaximum();
        float volume = min + (sliderValue / 100f) * (max - min);
        volumeControl.setValue(volume);
    }
}
