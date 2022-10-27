package com.devfep.spotipple.dto;

import lombok.Data;

@Data
public class PlaylistDto {
    private String href;
    private Object[] items;
    private int limit;
    private int total;
    private String next;
    private int offset;
    private String previous;


}
