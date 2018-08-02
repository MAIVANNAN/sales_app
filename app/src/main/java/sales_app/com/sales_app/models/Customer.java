package sales_app.com.sales_app.models;

public class Customer {
    private String C_id;

    private String C_Name, C_address,area,C_phonenum,C_Email,a_id;

    public Customer() {
    }

    public Customer(String C_id,String Name,String address,String Phonenum, String Email, String a_id ,String area ) {

        this.C_id=C_id;
        this.C_Name = Name;
        this.C_address=address;
        this.C_Email=Email;
        this.C_phonenum = Phonenum;
        this.area=area;
        this.a_id=a_id;




    }

    public String getC_id() {
        return C_id;
    }

    public void setC_id(String c_id) {
        C_id = c_id;
    }

    public String getName() {
        return C_Name;
    }

    public void setName(String name) {
        C_Name = name;
    }

    public String getAddress() {
        return C_address;
    }

    public void setAddress(String address) {
        this.C_address = address;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getEmail() {
        return C_Email;
    }

    public void setEmail(String email) {
        C_Email = email;
    }

    public String getPhonenum() {
        return C_phonenum;
    }

    public void setPhonenum(String Phonenum) {
        this.C_phonenum = Phonenum;
    }

    public String getA_id() {
        return a_id;
    }

    public void setA_id(String a_id) {
        this.a_id = a_id;
    }
}
