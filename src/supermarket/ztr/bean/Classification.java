package supermarket.ztr.bean;

public class Classification {
    int id;
    String  name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Classification() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Classification(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
