package sales_app.com.sales_app.models;

public class sales_manager {
    private int s_id;
    private String Name;
    private long Phone;
    private String address;
    private String email;
    private String password;
    private String device_id;
    private String role;

    public sales_manager(int s_id, String name, long phone, String address, String email, String password, String device_id, String role) {
        this.s_id = s_id;
        Name = name;
        Phone = phone;
        this.address = address;
        this.email = email;
        this.password = password;
        this.device_id = device_id;
        this.role = role;
    }

    public int getS_id() {
        return s_id;
    }

    public void setS_id(int s_id) {
        this.s_id = s_id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public long getPhone() {
        return Phone;
    }

    public void setPhone(long phone) {
        Phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDevice_id() {
        return device_id;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
