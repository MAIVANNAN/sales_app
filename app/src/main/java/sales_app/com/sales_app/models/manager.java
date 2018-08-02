package sales_app.com.sales_app.models;

public class manager {
    private String name;
    private String lname;
    private String phone;
    private String gender;
    private String userEmail;
    private String userPass;
    private String device_id;

    public manager(String name, String lname, String phone, String gender, String userEmail, String userPass, String device_id) {
        this.name = name;
        this.lname = lname;
        this.phone = phone;
        this.gender = gender;
        this.userEmail = userEmail;
        this.userPass = userPass;
        this.device_id = device_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPass() {
        return userPass;
    }

    public void setUserPass(String userPass) {
        this.userPass = userPass;
    }

    public String getDevice_id() {
        return device_id;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }
}
