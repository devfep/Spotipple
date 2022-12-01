package com.devfep.spotipple.ui.model.request;

//This will serve as the requestBody object for th
//feature controller. User is req to fill out the fields in the UI
//which will be mapped to this object

import lombok.Data;

@Data
public class FeatureRequestModel {
    private String name;
    private String phoneNumber;
    private String email;
    private String request;
}
