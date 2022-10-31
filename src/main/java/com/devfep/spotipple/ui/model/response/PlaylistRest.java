package com.devfep.spotipple.ui.model.response;

import lombok.Data;
import se.michaelthelin.spotify.model_objects.specification.Playlist;

import java.util.ArrayList;

@Data
public class PlaylistRest {
    ArrayList<String> playlistId;
    ArrayList<String> playlistName;
    private int total;

    public int getTotal() {
        return playlistId.size();
    }
}
