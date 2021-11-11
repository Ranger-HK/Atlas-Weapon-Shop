package view.tm;

public class TrainingGroundTM {
    private String userId;
    private int rowNumber;
    private String rowUserName;
    private String usingGunName;
    private String usingGunType;

    public TrainingGroundTM() {
    }

    public TrainingGroundTM(String userId, int rowNumber, String rowUserName, String usingGunName, String usingGunType) {
        this.setUserId(userId);
        this.setRowNumber(rowNumber);
        this.setRowUserName(rowUserName);
        this.setUsingGunName(usingGunName);
        this.setUsingGunType(usingGunType);
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getRowNumber() {
        return rowNumber;
    }

    public void setRowNumber(int rowNumber) {
        this.rowNumber = rowNumber;
    }

    public String getRowUserName() {
        return rowUserName;
    }

    public void setRowUserName(String rowUserName) {
        this.rowUserName = rowUserName;
    }

    public String getUsingGunName() {
        return usingGunName;
    }

    public void setUsingGunName(String usingGunName) {
        this.usingGunName = usingGunName;
    }

    public String getUsingGunType() {
        return usingGunType;
    }

    public void setUsingGunType(String usingGunType) {
        this.usingGunType = usingGunType;
    }

    @Override
    public String toString() {
        return "TrainingGroundTM{" +
                "userId='" + userId + '\'' +
                ", rowNumber=" + rowNumber +
                ", rowUserName='" + rowUserName + '\'' +
                ", usingGunName='" + usingGunName + '\'' +
                ", usingGunType='" + usingGunType + '\'' +
                '}';
    }
}
