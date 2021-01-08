package supermarket.ztr.model;

import javafx.collections.ObservableList;
import supermarket.ztr.bean.Classification;
import supermarket.ztr.bean.Goods;

/***
存储各种状态的类
*/
public class Type {
    public static int Oline_user;                        //存储登录的用户
    public static ObservableList<Goods> glist;             //存储添加商品后，应该刷新的列表
    public static ObservableList<Classification> clist;    //存储添加分类后，应该刷新的下拉列表
    public static String prompt;                         //提示消息
    public static boolean prompt_type;                   //提示消息 好/坏，会根据这个改变提示的颜色  红/绿
    public static String prompt_add;                     //提示消息补充
    public static Classification defaultClass=new Classification(-1,"所有");
}
