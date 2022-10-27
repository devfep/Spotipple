package com.devfep.spotipple.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto implements Serializable {
    private static final long serialVersionUID = -2113237761900639091L;
    private long id; //from DB - autoincremented
    private String userId;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String encryptedPassword;
//    private String emailVerificationToken;
//    private Boolean emailVerificationStatus;


}
