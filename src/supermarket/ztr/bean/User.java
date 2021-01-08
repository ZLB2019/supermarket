package supermarket.ztr.bean;

public class User {
    private int id;             //用户id/账号
    private String name;        //用户名
    private String password;    //密码
    private int type;           //用户类别 是否为管理员 0否 1是

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
