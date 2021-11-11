package model;

public class Login {
    private String loginId;
    private String userName;
    private String userPassword;

    public Login() {
    }

    public Login(String loginId, String userName, String userPassword) {
        this.setLoginId(loginId);
        this.setUserName(userName);
        this.setUserPassword(userPassword);
    }

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    @Override
    public String toString() {
        return "Login{" +
                "loginId='" + loginId + '\'' +
                ", userName='" + userName + '\'' +
                ", userPassword='" + userPassword + '\'' +
                '}';
    }
}
