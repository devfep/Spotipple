package com.devfep.spotipple.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@Component
public class PlaylistItemDto {
    private String id;
    private String name;
    private List<String> listOfIsrc;
    private List<String> listOfSongIds;
}
