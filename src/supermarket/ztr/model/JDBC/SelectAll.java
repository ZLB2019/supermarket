package supermarket.ztr.model.JDBC;

import supermarket.ztr.bean.Classification;
import supermarket.ztr.bean.Goods;
import supermarket.ztr.bean.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class SelectAll {

    //定义MySQL数据库的驱动程序
    public static final String driver="com.mysql.cj.jdbc.Driver";

    /**@定义MySQL数据库的连接地址
    /**serverTimezone=GMT : 是为了消除关于‘时区’的错误
     * 由于MySQL在高版本需要指明是否进行SSL(保障Internet数据传输安全利用数据加密)，所以要加下面这行代码消除警告
     *        useSSL=false ：是为了消除关于 ‘是否进行SSL’的警告
     * */
    public static final String url = "jdbc:mysql://localhost:3306/supermarket?serverTimezone=GMT&useSSL=false";

    //定义MySQL数据库的连接用户名
    public static final String user="root";
    //定义MySQL数据库的连接密码
    public static final String pass="zlb19991111";

    /**查询
     * 返回一个Data（商品）对象
     * */
    public static Goods selectGoods(int id)throws Exception{
        Goods goods = new Goods();
        Connection conn = null;						    //数据库连接
        Statement stmt = null;							//数据库操作
        ResultSet rs = null;                                 //保存查询结果
        String sql =sql = "select * from goods where id="+id;
        //连接MySQL数据库时，要写上连接的用户名和密码
        Class.forName(driver);							//加载驱动程序
        //连接MySQL数据库时，要写上连接的用户名和密码
        conn = DriverManager.getConnection(url, user, pass);
        stmt = conn.createStatement();					            //实例化Statement对象
        rs = stmt.executeQuery(sql);								//执行数据库的操作
        if(rs.next()) {
            goods.setId(rs.getInt("id"));
            goods.setClassification(rs.getInt("classification"));
            goods.setName(rs.getString("name"));
            goods.setPrice( rs.getDouble(4));
            goods.setNumber(rs.getInt(3));
        }

        stmt.close();								        	//数据库关闭
        conn.close();
        return goods;
    }

    /**查询
     * 返回一个User对象
     * */
    public static User selectUser(int id)throws Exception{
        User UserMessage = new User();
        Connection conn = null;						    //数据库连接
        Statement stmt = null;							//数据库操作
        ResultSet rs = null;                                 //保存查询结果
        String sql =sql = "select * from user where id="+id;
        //连接MySQL数据库时，要写上连接的用户名和密码
        Class.forName(driver);							//加载驱动程序
        //连接MySQL数据库时，要写上连接的用户名和密码
        conn = DriverManager.getConnection(url, user, pass);
        stmt = conn.createStatement();					            //实例化Statement对象
        rs = stmt.executeQuery(sql);								//执行数据库的操作
        if(rs.next()) {
            UserMessage.setId(rs.getInt("id"));
            UserMessage.setPassword(rs.getString("password"));
            UserMessage.setName(rs.getString("name"));
            UserMessage.setType(rs.getInt("type"));
        }

        stmt.close();								        	//数据库关闭
        conn.close();
        return UserMessage;
    }

    /**查询
     * 返回一个Classification对象
     * */
    public static Classification selectClassification(int id)throws Exception{
        Classification classification = new Classification();
        Connection conn = null;						    //数据库连接
        Statement stmt = null;							//数据库操作
        ResultSet rs = null;                                 //保存查询结果
        String sql =sql = "select * from classification where id="+ id;
        //连接MySQL数据库时，要写上连接的用户名和密码
        Class.forName(driver);							//加载驱动程序
        //连接MySQL数据库时，要写上连接的用户名和密码
        conn = DriverManager.getConnection(url, user, pass);
        stmt = conn.createStatement();					            //实例化Statement对象
        rs = stmt.executeQuery(sql);								//执行数据库的操作
        if(rs.next()) {
            classification.setId(rs.getInt("id"));
            classification.setName(rs.getString("name"));
        }

        stmt.close();								        	//数据库关闭
        conn.close();
        return classification;
    }

    /**查询
     * 返回一个Classification对象
     * */
    public static Classification selectClassification(String name)throws Exception{
        Classification classification = new Classification();
        Connection conn = null;						    //数据库连接
        Statement stmt = null;							//数据库操作
        ResultSet rs = null;                                 //保存查询结果
        String sql =sql = "select * from classification where name='"+ name+"'";
        //连接MySQL数据库时，要写上连接的用户名和密码
        Class.forName(driver);							//加载驱动程序
        //连接MySQL数据库时，要写上连接的用户名和密码
        conn = DriverManager.getConnection(url, user, pass);
        stmt = conn.createStatement();					            //实例化Statement对象
        rs = stmt.executeQuery(sql);								//执行数据库的操作
        if(rs.next()) {
            classification.setId(rs.getInt("id"));
            classification.setName(rs.getString("name"));
        }

        stmt.close();								        	//数据库关闭
        conn.close();
        return classification;
    }

    /**查询、
     * 返回一个列表
     * */
    public static ResultSet selectList(String sql)throws Exception{
        ResultSet rs;                                 //保存查询结果

        Connection conn = null;						    //数据库连接
        Statement stmt = null;							//数据库操作
        User UserMessage = null;
        rs = null;
        //连接MySQL数据库时，要写上连接的用户名和密码
        Class.forName(driver);							//加载驱动程序
        //连接MySQL数据库时，要写上连接的用户名和密码
        conn = DriverManager.getConnection(url, user, pass);
        stmt = conn.createStatement();					            //实例化Statement对象
        rs = stmt.executeQuery(sql);								//执行数据库的操作
        return  rs;
    }
}
