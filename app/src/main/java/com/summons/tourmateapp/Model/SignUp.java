package com.summons.tourmateapp.Model;

/**
 * Created by engrb on 14-Nov-16.
 */

public class SignUp {
    private String fullName;
    private String userName;
    private String password;
    private String eNumber;
    private String address;
    private String profilePic;

    public SignUp(String fullName, String userName, String password) {
        this.fullName = fullName;
        this.userName = userName;
        this.password = password;
    }

    public SignUp(String fullName, String userName, String password, String eNumber, String address, String profilePic) {
        this.fullName = fullName;
        this.userName = userName;
        this.password = password;
        this.eNumber = eNumber;
        this.address = address;
        this.profilePic = profilePic;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public String getFullName() {
        return fullName;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String geteNumber() {
        return eNumber;
    }

    public String getAddress() {
        return address;
    }


}
