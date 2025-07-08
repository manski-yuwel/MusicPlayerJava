package view;

import java.util.EventListener;

public interface SongListener extends EventListener {
    public void songEventOccured (SongEvent evt);
}
