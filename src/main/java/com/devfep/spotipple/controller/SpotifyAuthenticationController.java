package com.devfep.spotipple.controller;


import com.devfep.spotipple.secret.Keys;
import org.apache.hc.core5.http.ParseException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.SpotifyHttpManager;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.credentials.AuthorizationCodeCredentials;
import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeUriRequest;


import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;


@Controller
@RequestMapping("spotify")
public class SpotifyAuthenticationController {

    @Autowired
    private ModelMapper mapper;


    static String clientId = Keys.CLIENT_ID.getClientId();


    static String clientSecret = Keys.CLIENT_SECRET.getClientSecret();


    static String scope = "user-read-private user-read-email user-top-read playlist-read-private";


    private static final URI redirectUri = SpotifyHttpManager.makeUri("http://localhost:8080/spotify/callback");
    private String code = "";

    public static final SpotifyApi spotifyApi = new SpotifyApi.Builder()
            .setClientId(clientId)
            .setClientSecret(clientSecret)
            .setRedirectUri(redirectUri)
            .build();


    @GetMapping("/login")
    @ResponseBody
    public String spotifyLogin() {
        AuthorizationCodeUriRequest authorizationCodeUriRequest = spotifyApi.authorizationCodeUri()
                .scope(scope)
                .show_dialog(true)
                .build();

        final URI uri = authorizationCodeUriRequest.execute();
        return uri.toString();
    }


    @GetMapping(value = "/callback")
    @ResponseBody
    public void getSpotifyUserCode(@RequestParam("code") String userCode, HttpServletResponse response) throws IOException {
        code = userCode;
        var authorizationCodeRequest = spotifyApi.authorizationCode(code).build();

        try {
            final AuthorizationCodeCredentials authorizationCodeCredentials = authorizationCodeRequest.execute();


            spotifyApi.setAccessToken(authorizationCodeCredentials.getAccessToken());
            spotifyApi.setRefreshToken(authorizationCodeCredentials.getRefreshToken());

            System.out.println(authorizationCodeCredentials.getAccessToken());

            System.out.println("Expires in " + authorizationCodeCredentials.getExpiresIn());


        } catch (ParseException | SpotifyWebApiException | IOException e) {
            System.out.println("Error: " + e.getMessage());


        }


        response.sendRedirect("http://localhost:8080/dashboard");
    }


}


