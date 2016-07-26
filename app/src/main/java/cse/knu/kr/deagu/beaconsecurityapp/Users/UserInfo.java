package cse.knu.kr.deagu.beaconsecurityapp.Users;

import java.io.Serializable;

/**
 * Created by juhee on 2016-07-15.
 */
public class UserInfo implements Serializable {

    private String userID;
    private String userPW;
    private String userSign;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    private String userName;

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserPW() {
        return userPW;
    }

    public void setUserPW(String userPW) {
        this.userPW = userPW;
    }

    public String getUserSign() {
        return userSign;
    }

    public void setUserSign(String userSign) {
        this.userSign = userSign;
    }

    public UserInfo(String userID, String userPW, String userSign) {
        this.userID = userID;
        this.userPW = userPW;
        this.userSign = userSign;
    }

    public UserInfo(String userID, String userPW) {
        this.userID = userID;
        this.userPW = userPW;
    }
}
