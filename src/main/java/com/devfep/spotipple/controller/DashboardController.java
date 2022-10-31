package com.devfep.spotipple.controller;

import com.devfep.spotipple.dto.AllPlaylistsDto;
import com.devfep.spotipple.service.Impl.PlaylistServiceImpl;
import com.devfep.spotipple.ui.model.response.PlaylistRest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class DashboardController {

    @Autowired
    private PlaylistServiceImpl playlistServiceImpl;
    @Autowired
    private ModelMapper mapper;


    @GetMapping("dashboard")
    @ResponseBody
    public PlaylistRest showDashboard() {
//        model.addAttribute(String.valueOf(playlistServiceImpl.getListOfCurrentUsersPlaylists_Async()));

        //Get list of playlists with their names and Ids.
        return mapper.map(playlistServiceImpl.getListOfCurrentUsersPlaylists_Async(), PlaylistRest.class);

        //            modelMapper.map(playlistDto, PlaylistRest.class);


    }

}
