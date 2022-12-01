package com.devfep.spotipple.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.ArrayList;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TrackInfo {
    private String name;
    public ArrayList<Artist> artists;

    public String getArtists() {
        return artists.get(0).getName();
    }

    public String getName() {
        return songNameExtractor(name);
    }

    public static String songNameExtractor(String songName) {
        if (songName.contains("(feat. ")) {
            return songName.substring(0, songName.indexOf("(feat. "));
        } else if (songName.contains("(Feat. ")) {
            return songName.substring(0, songName.indexOf("(Feat. "));
        }
        //more conditions here when I come across them

        return songName;
    }
}


@Data
@JsonIgnoreProperties(ignoreUnknown = true)
class Artist {
    public String name;
}