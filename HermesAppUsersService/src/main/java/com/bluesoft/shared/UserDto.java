package com.bluesoft.shared;

import com.bluesoft.ui.model.AlbumResponseModel;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class UserDto implements Serializable {

    private static final long serialVersionUID = -2456572413665263743L;

    private String userId;
    private String firstName;
    private String lastName;
    private String password;
    private String encryptedPassword;
    private String email;
    private List<AlbumResponseModel> albums;

}
