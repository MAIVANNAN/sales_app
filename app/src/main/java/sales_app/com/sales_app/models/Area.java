package sales_app.com.sales_app.models;

public class Area {

    private String AName,Aid;

    public Area() {
    }

    public Area(String AName,String Aid) {
        this.AName = AName;
        this.Aid=Aid;


    }

    public String getAName() {
        return AName;
    }

    public void setAName(String Aname) {
        AName = Aname;
    }

    public String getAid() {
        return Aid;
    }

    public void setAid(String aid) {
        Aid = aid;
    }
}
