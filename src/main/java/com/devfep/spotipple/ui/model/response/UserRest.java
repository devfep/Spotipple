package com.devfep.spotipple.ui.model.response;

import lombok.Data;

@Data
public class UserRest {
    private String userId; //not a DB id
    private String firstName;
    private String lastName;
    private String email;
}
