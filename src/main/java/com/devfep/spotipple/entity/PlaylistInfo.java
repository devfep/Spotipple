//package com.devfep.spotipple.entity;
//
//
//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
//import lombok.Data;
//
//import java.util.ArrayList;
//
//@JsonIgnoreProperties(ignoreUnknown = true)
//@Data
//class Artist{
//    public String name;
//}
//
//@JsonIgnoreProperties(ignoreUnknown = true)
//@Data
//class ExternalIds{
//    public ExternalIds externalIds;
//    public String isrc;
//
//    public String getExternalIds() {
//        return externalIds.getIsrc();
//    }
//}
//
//@JsonIgnoreProperties(ignoreUnknown = true)
//@Data
//class Image{
//    public String url;
//}
//
//@JsonIgnoreProperties(ignoreUnknown = true)
//@Data
//class Items{
//    public Track track;
//
//    public String getTrack() {
//        return track.getName() + "+" + track.artists.get(0);
//    }
//}
//
//@JsonIgnoreProperties(ignoreUnknown = true)
//@Data
//public class PlaylistInfo{
//    public ArrayList<Items> items;
//    public int total;
//    public String isrc;
//
//    public ArrayList<Items> getItems() {
//        return items;
//    }
//
//}
//
//@JsonIgnoreProperties(ignoreUnknown = true)
//@Data
//class Track{
//    public ArrayList<Artist> artists;
//    public String name;
//    public ExternalIds externalIds;
//
//
//
//    public String getArtists() {
//        return artists.get(0).getName();
//    }
//
//    public String getName() {
//        return songNameExtractor(name);
//    }
//
//    public static String songNameExtractor(String songName){
//        if (songName.contains("(feat. ")) {
//            return songName.substring(0, songName.indexOf("(feat. "));
//        }
//        else if (songName.contains("(Feat. ")) {
//            return songName.substring(0, songName.indexOf("(Feat. "));
//        }
//        //more conditions here when I come across them
//
//        return songName;
//    }
//}