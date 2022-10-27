package com.devfep.spotipple.controller;

import com.devfep.spotipple.dto.PlaylistDto;
import com.devfep.spotipple.service.Impl.PlaylistServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class DashboardController {

    @Autowired
    private PlaylistServiceImpl playlistServiceImpl;
    @Autowired
    private ModelMapper mapper;


    @GetMapping("dashboard")
    @ResponseBody
    public PlaylistDto showDashboard() {
//        model.addAttribute(String.valueOf(playlistServiceImpl.getListOfCurrentUsersPlaylists_Async()));

        return mapper.map(playlistServiceImpl.getListOfCurrentUsersPlaylists_Async(), PlaylistDto.class);


    }
}
