package sales_app.com.sales_app.models;

public class soldProduct {


    private String p_name;
    private String price ;
    private String s_name;
    private String quantity;
    private String date;
    private String c_name;


    public soldProduct(String p_name, String price, String s_name, String quantity, String date, String c_name) {
        this.p_name = p_name;
        this.price = price;
        this.s_name = s_name;
        this.quantity = quantity;
        this.date = date;
        this.c_name = c_name;
    }

    public String getP_name() {
        return p_name;
    }

    public void setP_name(String p_name) {
        this.p_name = p_name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getS_name() {
        return s_name;
    }

    public void setS_name(String s_name) {
        this.s_name = s_name;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getC_name() {
        return c_name;
    }

    public void setC_name(String c_name) {
        this.c_name = c_name;
    }
}
