package supermarket.ztr.model;

import javafx.collections.ObservableList;
import supermarket.ztr.bean.Classification;
import supermarket.ztr.bean.Goods;
import supermarket.ztr.bean.Orders;
import supermarket.ztr.bean.User;
import supermarket.ztr.model.JDBC.SelectAll;

import java.sql.ResultSet;

/**
刷新列表的类，内置刷新列表的方法
*/
public class Resfresh {

    /**
     功能描述 :用于刷新用户列表
     */
    public static void refreshUserList(ObservableList<User> list) throws Exception{
        list.clear();
        String sql="select * from user where type=0";                   //查找分类的sql语句
        ResultSet resultSet = SelectAll.selectList(sql);     //得到返回结果集
        while(resultSet.next()){                            //遍历结果集
            User user = new User();
            user.setId(resultSet.getInt("id"));
            user.setName(resultSet.getString("name"));
            user.setPassword(resultSet.getString("password"));
            list.add(user);                               //添加该分类数据到下拉列表中
        }
    }

    /**
     功能描述 :用于刷新分类列表
     */
    public static void refreshClassList(ObservableList<Classification> list) throws Exception{
        list.clear();
        String sql="select * from classification";                   //查找分类的sql语句
        ResultSet resultSet = SelectAll.selectList(sql);     //得到返回结果集
        while(resultSet.next()){                            //遍历结果集
            Classification classification = new Classification();
            classification.setId(resultSet.getInt("id"));
            classification.setName(resultSet.getString("name"));
            list.add(classification);                               //添加该分类数据到下拉列表中
        }
    }

    /**
     功能描述 :用显示分类下拉列表
     */
    public static void refreshClassChoiceBox(ObservableList<Classification> list) throws Exception{
        list.clear();
        String sql="select * from classification";                   //查找分类的sql语句
        ResultSet resultSet = SelectAll.selectList(sql);     //得到返回结果集
        while(resultSet.next()){                            //遍历结果集
            Classification classification = new Classification();
            classification.setId(resultSet.getInt("id"));
            classification.setName(resultSet.getString("name"));
            list.add(classification);                               //添加该分类数据到下拉列表中
        }
    }

    /**
     功能描述 :用显示分类下拉列表
     */
    public static void refreshClassChoiceBox(ObservableList<Classification> list,Classification defaultClass) throws Exception{
        list.clear();
        String sql="select * from classification";                   //查找分类的sql语句
        list.add(defaultClass);
        ResultSet resultSet = SelectAll.selectList(sql);     //得到返回结果集
        while(resultSet.next()){                            //遍历结果集
            Classification classification = new Classification();
            classification.setId(resultSet.getInt("id"));
            classification.setName(resultSet.getString("name"));
            list.add(classification);                               //添加该分类数据到下拉列表中
        }
    }

    /**
     功能描述 :用于刷新商品列表
     */
    public static void refreshGoodsList(ObservableList<Goods> list,String where) throws Exception{
        list.clear();
        String sql="select * from goods"+where;                   //查找商品库的sql语句
        ResultSet resultSet = SelectAll.selectList(sql);     //得到返回结果集
        while(resultSet.next()){                            //遍历结果集
            Goods goods = new Goods();
            goods.setId(resultSet.getInt("id"));
            goods.setClassification(resultSet.getInt("classification"));
            goods.setName(resultSet.getString("name"));
            goods.setPrice( resultSet.getDouble(4));
            goods.setNumber(resultSet.getInt(3));

            list.add(goods);                               //添加该商品数据到列表中
        }
    }

    /**
     功能描述 :用于刷新订单列表
     */
    public static void refreshOrdersList(ObservableList<Orders> list,String where) throws Exception{
            list.clear();
        String sql="select * from orders "+where+" order by time desc ";                   //查找商品库的sql语句
        ResultSet resultSet = SelectAll.selectList(sql);     //得到返回结果集
        while(resultSet.next()){                            //遍历结果集
            Orders orders = new Orders();
            orders.setId(resultSet.getInt("id"));
            orders.setGoodsId(resultSet.getInt("goodsId"));
            orders.setUserId(resultSet.getInt("userId"));
            orders.setNumber(resultSet.getInt("number"));
            orders.setPrice(resultSet.getDouble("totalPrice"));
            orders.setTime(resultSet.getString("time"));
            list.add(orders);                               //添加该商品数据到列表中
        }
    }
}
