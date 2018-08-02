package sales_app.com.sales_app.models;



public class Product {

    private int P_id;
    private String p_name;
    private int price ;
    private String s_id;
    private String instock;

    public Product(int p_id, String p_name, int price, String instock,String s_id) {
        P_id = p_id;
        this.p_name = p_name;
        this.price = price;
        this.s_id = s_id;
        this.instock=instock;

    }

    public String getS_id() {
        return s_id;
    }

    public void setS_id(String s_id) {
        this.s_id = s_id;
    }

    public int getP_id() {
        return P_id;
    }

    public void setP_id(int p_id) {
        P_id = p_id;
    }

    public String getP_name() {
        return p_name;
    }

    public void setP_name(String p_name) {
        this.p_name = p_name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }



    public String getInstock() {
        return instock;
    }

    public void setInstock(String instock) {
        this.instock = instock;
    }
}
