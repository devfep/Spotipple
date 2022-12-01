package com.devfep.spotipple.controller;

import com.devfep.spotipple.dto.PlaylistItemDto;
import com.devfep.spotipple.repository.PlaylistRepository;
import com.devfep.spotipple.service.Impl.PlaylistServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.modelmapper.MappingException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import se.michaelthelin.spotify.model_objects.specification.Paging;
import se.michaelthelin.spotify.model_objects.specification.PlaylistSimplified;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Controller
@RequestMapping("dashboard")
public class DashboardController {

    @Autowired
    private PlaylistServiceImpl playlistServiceImpl;
    @Autowired
    private ModelMapper mapper;

    @Autowired
    private PlaylistRepository playlistRepository;

    static Map finalPlaylistDto;

//    @GetMapping("all")
//    @ResponseBody
//    public Map showPlaylistDashboard() {
////        model.addAttribute(String.valueOf(playlistServiceImpl.getListOfCurrentUsersPlaylists_Async()));
//
//
//        finalPlaylistDto = mapper.map(playlistServiceImpl.getListOfCurrentUsersPlaylists_Async(), Map.class);
//        System.out.println("Let's see " + finalPlaylistDto);
//        return finalPlaylistDto;
//
//
//
//        //Get list of playlists with their names and Ids.
////        return mapper.map(playlistServiceImpl.getListOfCurrentUsersPlaylists_Async(), PlaylistRest.class);
//
//        //            modelMapper.map(playlistDto, PlaylistRest.class);
//
//
//    }


    @RequestMapping("")
    public String displayDashboard(Model model, Authentication authentication) throws JsonProcessingException, ExecutionException, InterruptedException {

//        playlistRepository.findAll();

        model.addAttribute("listOfPlaylists", playlistServiceImpl.getListOfCurrentUsersPlaylists_Async());
        model.addAttribute("username", authentication.getName());
        model.addAttribute("roles", authentication.getAuthorities().toString());
        return "dashboard.html";

    }


}
