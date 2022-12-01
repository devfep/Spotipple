package com.devfep.spotipple.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.ArrayList;

@Data
class ExternalUrls {
    public ExternalUrls externalUrls;
    public String spotify;
}

@Data
class Image {
    public int height;
    public String url;
    public int width;
}


@Data
@JsonIgnoreProperties(ignoreUnknown = true)
class Item {
    public ExternalUrls externalUrls;
    public String href;
    public String id;
    public ArrayList<Image> images;
    public String name;
    public Owner owner;
    public String snapshotId;
    public Tracks tracks;
    public String type;
    public String uri;
    @JsonIgnore
    public boolean isCollaborative;
    @JsonIgnore
    public boolean isPublicAccess;
}

@Data
class Owner {
    public Object birthdate;
    public Object country;
    public String displayName;
    public Object email;
    public ExternalUrls externalUrls;
    public Object followers;
    public String href;
    public String id;
    public Object images;
    public Object product;
    public String type;
    public String uri;
}

@Data
public class Root {
    public String href;
    public ArrayList<Item> items;
    public int limit;
    public Object next;
    public int offset;
    public Object previous;
    public int total;
}

@Data
class Tracks {
    public String href;
    public int total;
}
