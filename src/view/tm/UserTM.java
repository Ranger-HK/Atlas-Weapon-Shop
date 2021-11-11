package view.tm;

public class UserTM {
    private String userName;
    private String name;
    private String userPassword;
    private String role;
    private String address;

    public UserTM() {
    }

    public UserTM(String userName, String name, String userPassword, String role, String address) {
        this.setUserName(userName);
        this.setName(name);
        this.setUserPassword(userPassword);
        this.setRole(role);
        this.setAddress(address);
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "UserTM{" +
                "userName='" + userName + '\'' +
                ", name='" + name + '\'' +
                ", userPassword='" + userPassword + '\'' +
                ", role='" + role + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
