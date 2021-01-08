package supermarket.ztr.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import supermarket.ztr.bean.Orders;
import supermarket.ztr.bean.User;
import supermarket.ztr.model.*;
import supermarket.ztr.model.JDBC.SelectAll;

import java.net.URL;
import java.util.ResourceBundle;

public class Order implements Initializable {

    @FXML
    private ListView<Orders> orderList;     //列表控件

    @FXML
    private Label title;

    private ObservableList<Orders> olist = FXCollections.observableArrayList();       //存储商品列表的list

    private User user;



    @Override
    /**初始化,自定义列表格式+显示出商品列表*/
    public void initialize(URL url, ResourceBundle rb) {
        try {
            orderList.setCellFactory(new Callback<ListView<Orders>, ListCell<Orders>>() {

                int index = 0;
                Orders temp = null;

                @Override
                public ListCell<Orders> call(ListView<Orders> param) {

                    param.setOnEditStart(new EventHandler<ListView.EditEvent<Orders>>(){

                        @Override
                        public void handle(ListView.EditEvent<Orders> event){
                            index = event.getIndex();
                            temp = param.getItems().get(index);
                        }

                    });

                    ListCell<Orders> listcell  = new ListCell<Orders>(){

                        @Override
                        //只定义编辑单元格一定要重写的方法
                        protected void updateItem(Orders item, boolean empty) {
                            super.updateItem(item, empty);

                            try {
                                if(!empty && item != null){
                                    HBox hbox = new HBox(30);       //水平布局
                                    hbox.setAlignment(Pos.BASELINE_CENTER); //居中对齐

                                    Label name =new Label(SelectAll.selectGoods(item.getGoodsId()).getName());  //商品名
                                    name.setMinWidth(100);
                                    Label number =new Label(""+item.getNumber());//数量
                                    number.setMinWidth(80);
                                    Label price =new Label(String.format("%.2f 元", item.getPrice()));//价格
                                    price.setMinWidth(80);

                                    Label time =new Label(item.getTime()+"");//购买时间
                                    price.setMinWidth(100);

                                    if(user.getType()==1){
                                        Label userId =new Label(item.getUserId()+"");  //用户id
                                        name.setMinWidth(75);
                                        Label userName =new Label(SelectAll.selectUser(item.getUserId()).getName());//用户名
                                        number.setMinWidth(75);
                                        name.setMinWidth(75);
                                        number.setMinWidth(20);
                                        price.setMinWidth(75);
                                        hbox.getChildren().addAll(userId,userName,name,number,price,time);  //在横向布局中添加控件
                                    }else
                                        hbox.getChildren().addAll(name,number,price,time);  //在横向布局中添加控件
                                    this.setGraphic(hbox);          //应用该横向布局
                                }else if(empty){
                                    setText(null);
                                    setGraphic(null);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    };
                    return listcell;
                }
            });

            orderList.setItems(olist);
            user=SelectAll.selectUser(Type.Oline_user);      //得到这个人的信息
            if(user.getType()==0){ //非管理员
                Resfresh.refreshOrdersList(olist,"where userId="+Type.Oline_user);                   //刷新订单列表
                title.setText("     商品名          购买数量             总价格                        购买时间  ");
            }
            else
                Resfresh.refreshOrdersList(olist,"");                   //刷新订单列表

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
