package com.devfep.spotipple.service.Impl;


import com.devfep.spotipple.dto.PlaylistItemDto;
import com.devfep.spotipple.dto.Root;
import com.devfep.spotipple.dto.TrackInfo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.michaelthelin.spotify.model_objects.specification.*;
import se.michaelthelin.spotify.requests.data.playlists.GetListOfCurrentUsersPlaylistsRequest;
import se.michaelthelin.spotify.requests.data.playlists.GetPlaylistsItemsRequest;


import java.util.*;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutionException;

import static com.devfep.spotipple.controller.SpotifyAuthenticationController.*;

@Service
public class PlaylistServiceImpl implements PlaylistService {


    public static List<PlaylistItemDto> listOfPlaylists = new ArrayList<>();

    PlaylistItemDto playlistItemDto;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    Gson gson;


    public List<PlaylistItemDto> getListOfCurrentUsersPlaylists_Async() throws JsonProcessingException, ExecutionException, InterruptedException {

        final GetListOfCurrentUsersPlaylistsRequest getListOfCurrentUsersPlaylistsRequest = spotifyApi
                .getListOfCurrentUsersPlaylists()
                .limit(20)
                .offset(0)
                .build();

        try {
            final CompletableFuture<Paging<PlaylistSimplified>> pagingFuture = getListOfCurrentUsersPlaylistsRequest.executeAsync();


            final Paging<PlaylistSimplified> playlistSimplifiedPaging = pagingFuture.get();


            System.out.println(playlistSimplifiedPaging.getTotal());


            String json = gson.toJson(playlistSimplifiedPaging);
            Root root = objectMapper.readValue(json, Root.class);

            for (int i = 0; i < root.getItems().size(); i++) {
//                System.out.println(root.getItems().get(i));

                String newJson = gson.toJson(root.getItems().get(i));
                playlistItemDto = objectMapper.readValue(newJson, PlaylistItemDto.class);
//                System.out.println("here it is " + playlistItemDto);
                listOfPlaylists.add(playlistItemDto);
            }
//            System.out.println("here we are "+ root);
//
//            for (int i = 0; i < playlistSimplifiedPaging.getItems().length; i++) {
//                playlistIds.add(playlistSimplifiedPaging.getItems()[i].getId());
//                playlistName.add(playlistSimplifiedPaging.getItems()[i].getName());
//
//            }
//            System.out.println(playlistIds);
//            System.out.println(playlistName);


            for (int i = 0; i < listOfPlaylists.size(); i++) {
                listOfPlaylists.get(i).setListOfIsrc(getPlaylistsItems_Async(listOfPlaylists.get(i).getId()));
            }

            System.out.println(listOfPlaylists.toString());


            return listOfPlaylists;


        } catch (CompletionException e) {
            System.out.println("Error: " + e.getCause().getMessage());
        } catch (CancellationException e) {
            System.out.println("Async operation cancelled.");
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return null;
    }


    public List<String> getPlaylistsItems_Async(String playlistId) throws JsonProcessingException {
        List<String> isrcList = new ArrayList<>();

        final GetPlaylistsItemsRequest getPlaylistsItemsRequest = spotifyApi
                .getPlaylistsItems(playlistId)
                //          .fields("description")
                .limit(20)
                //          .offset(0)
                //          .market(CountryCode.SE)
                .additionalTypes("track")
                .build();


        try {
            final CompletableFuture<Paging<PlaylistTrack>> pagingFuture = getPlaylistsItemsRequest.executeAsync();

            final Paging<PlaylistTrack> playlistTrackPaging = pagingFuture.get();


//            System.out.println(playlistTrackPaging.getItems()[0].getTrack());
            for (int i = 0; i < playlistTrackPaging.getItems().length; i++) {
                String json = gson.toJson(playlistTrackPaging.getItems()[i].getTrack());

                JsonNode root = objectMapper.readTree(json);
                String isrcValue = root.get("externalIds").get("externalIds").get("isrc").asText();

                isrcList.add(isrcValue);
            }


//            System.out.println(isrcList);


//
//                TrackInfo trackInfoDto = objectMapper.readValue(json, TrackInfo.class)


//            for (int i = 0; i < playlistTrackPaging.getItems().length; i++) {
//                String json = gson.toJson((Track)playlistTrackPaging.getItems()[i].getTrack());
//
//                TrackInfo trackInfoDto = objectMapper.readValue(json, TrackInfo.class);
//
//                listOfArtistSongMap.add(trackInfoDto);
//            }
//
//            for (int i = 0; i < listOfPlaylists.size(); i++) {
//
//            }
//            System.out.println(listOfArtistSongMap.toString());


            return isrcList;

        } catch (CompletionException e) {
            System.out.println("Error: " + e.getCause().getMessage());
        } catch (CancellationException e) {
            System.out.println("Async operation cancelled.");
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


        return null;
    }


}
