package model;

public class User {
    private String userName;
    private String userPassword;
    private String name;
    private String address;
    private String role;

    public User() {
    }

    public User(String userName, String userPassword, String name, String address, String role) {
        this.setUserName(userName);
        this.setUserPassword(userPassword);
        this.setName(name);
        this.setAddress(address);
        this.setRole(role);
    }

    public User(String userName, String userPassword){
        this.setUserName(userName);
        this.setUserPassword(userPassword);
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "User{" +
                "userName='" + userName + '\'' +
                ", userPassword='" + userPassword + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
