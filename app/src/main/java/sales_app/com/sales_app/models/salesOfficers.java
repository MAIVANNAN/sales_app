package sales_app.com.sales_app.models;

import android.provider.ContactsContract;

public class salesOfficers {
    private String EName;
    private String EPhonenum;
    private String Email_id;
    private String areasales;
    private String passwordsales;
    private String E_id;
    private String Address;

    public salesOfficers() {
    }

    public salesOfficers(String E_id,String Ename, String Ephone,String Address, String Email_id, String areasales, String passwordsales) {

        this.E_id=E_id;
        this.EName = Ename;
        this.EPhonenum=Ephone;
        this.Email_id=Email_id;
        this.areasales=areasales;
        this.passwordsales=passwordsales;
        this.Address=Address;
        }

    public String getE_id() {
        return E_id;
    }

    public void setE_id(String e_id) {
        E_id = e_id;
    }

    public String getEName() {
        return EName;
    }

    public void setEName(String EName) {
        this.EName = EName;
    }



    public String getEPhonenum() {
        return EPhonenum;
    }

    public void setEPhonenum(String EPhonenum) {
        this.EPhonenum = EPhonenum;
    }

    public String getEmail_id() {
        return Email_id;
    }

    public void setEmail_id(String email_id) {
        Email_id = email_id;
    }

    public String getAreasales() {
        return areasales;
    }

    public void setAreasales(String areasales) {
        this.areasales = areasales;
    }

    public String getPasswordsales() {
        return passwordsales;
    }

    public void setPasswordsales(String passwordsales) {
        this.passwordsales = passwordsales;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }
}
