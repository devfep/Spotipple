package com.devfep.spotipple.controller;

import com.devfep.spotipple.dto.PlaylistItemDto;
import com.devfep.spotipple.service.Impl.PlaylistServiceImpl;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import lombok.Data;
import okhttp3.*;
import org.apache.hc.core5.net.URIBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Controller
@RequestMapping("deezer")
public class DeezerAuthenticationController {
    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    Gson gson;

    @Autowired
    OkHttpClient okHttpClient;

    DeezerResponse deezerResponse;
    static String secret = "d3d123ba8459311c8722bd2e8563626d";
    private static String redirect_uri = "http://localhost:8080/deezer/callback";
    static String app_id = "565462";
    static String perms = "manage_library,basic_access,email,offline_access, delete_library";

    private String code = "";

    private static DeezerResponse deezerTokenResponse;

    private static DeezerSongId deezerSongId;

    private static String accessToken;


    @GetMapping("/login")
    @ResponseBody
    public String deezerLogin() {
        return "https://connect.deezer.com/oauth/auth.php?app_id=" + app_id + "&redirect_uri=" + redirect_uri + "&perms=" + perms;
    }


    @GetMapping(value = "/callback")
    @ResponseBody
    public void getDeezerCode(@RequestParam("code") String userCode, HttpServletResponse servletResponse) throws IOException, InterruptedException {
        code = userCode;
        String deezerAccessTokenUrl = "https://connect.deezer.com/oauth/access_token.php?"
                + "app_id=" + app_id + "&secret=" + secret + "&code=" + code + "&output=json";


        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .header("accept", "application/json")
                .uri(URI.create(deezerAccessTokenUrl))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println(response);
        deezerTokenResponse = objectMapper.readValue(response.body(), DeezerResponse.class);

        System.out.println(deezerTokenResponse);
        System.out.println(deezerTokenResponse.getAccessToken());
        accessToken = deezerTokenResponse.getAccessToken();
        System.out.println(accessToken);


//        createPlaylist(accessToken);
        servletResponse.sendRedirect("http://localhost:8080/deezer/playlist");


    }


    @GetMapping(value = "/playlist")
    public String createPlaylist() throws IOException, InterruptedException, ExecutionException, URISyntaxException {

        for (PlaylistItemDto playlist : PlaylistServiceImpl.listOfPlaylists) {
            String playlistTitle = String.format("%s", playlist.getName().replace(" ", ""));

            final String POST_PLAYLIST_URL = "https://api.deezer.com/user/me/playlists?access_token=" + accessToken + "&title=" + playlistTitle + "&request_method=POST";

            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder().GET().header("accept", "application/json")
                    .uri(URI.create(POST_PLAYLIST_URL))
                    .build();

            CompletableFuture<HttpResponse<String>> response = client.sendAsync(request, HttpResponse.BodyHandlers.ofString());

            CreatedPlaylist createdPlaylist = objectMapper.readValue(response.get().body(), CreatedPlaylist.class);
            long createdPlaylistId = createdPlaylist.getId();

            List<String> songIds = new ArrayList<>();

            for (String isrc : playlist.getListOfIsrc()) {
                songIds.add(getSongId(isrc));
            }
            playlist.setListOfSongIds(songIds);
            System.out.println(playlist);

            System.out.println(createdPlaylistId + Arrays.toString(playlist.getListOfSongIds().toArray()));

//            createPlaylist(createdPlaylistId, playlist.getListOfSongIds());
        }


        return "playlist";

    }


    public String getSongId(String isrc) throws IOException, InterruptedException {
        String returnVal = "";

        final String GET_DEEZER_SONG_ID = "https://api.deezer.com/track/isrc:" + isrc;
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .header("accept", "application/json")
                .uri(URI.create(GET_DEEZER_SONG_ID))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
//        CompletableFuture<HttpResponse<String>> response = client.sendAsync(request, HttpResponse.BodyHandlers.ofString());

//        System.out.println(response.body());

        deezerSongId = objectMapper.readValue(response.body(), DeezerSongId.class);
//        System.out.println(deezerSongId.getId());
        if (deezerSongId.getId() != null) {
            returnVal = deezerSongId.getId();
        }
        return returnVal;
    }


    public void createPlaylist(long playlistId, List<String> songIds) throws IOException, InterruptedException, URISyntaxException {

        final String POST_PLAYLIST_MAKER = "https://api.deezer.com/playlist" + playlistId + "/tracks" + "&request_method=POST";
        URIBuilder uribuilder = new URIBuilder(POST_PLAYLIST_MAKER);
        String paramStringVal = "";
        for (int i = 0; i < songIds.size(); i++) {
            if (i <= songIds.size() - 2 && !songIds.get(i).isEmpty()) {
                paramStringVal += songIds.get(i) + ",";
            } else {
                paramStringVal += songIds.get(i);
            }
        }
        URI uri = uribuilder.addParameter("songs", paramStringVal).build();

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .header("accept", "application/json")
                .uri(uri)
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

    }


}

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
class CreatedPlaylist {
    private long id;
}

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
class DeezerSongId {

    private String id;
}

@Data
class DeezerResponse {
    private String accessToken;
    private int expires;

    @JsonProperty("access_token")
    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    @JsonProperty("expires")
    public int getExpires() {
        return expires;
    }

    public void setExpires(int expires) {
        this.expires = expires;
    }
}