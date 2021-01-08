package supermarket.ztr.controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;
import supermarket.ztr.bean.Classification;
import supermarket.ztr.bean.Goods;
import supermarket.ztr.bean.User;
import supermarket.ztr.model.*;
import supermarket.ztr.model.JDBC.IDU;
import supermarket.ztr.model.JDBC.SelectAll;

import java.net.URL;
import java.util.ResourceBundle;

import static supermarket.ztr.model.Type.defaultClass;

/**
开心超市主界面（用户） 的控制器
*/
public class Main  implements Initializable {

    @FXML
    private ListView<Goods> goodsList;       //显示商品的列表控件

    @FXML
    private ImageView returnLogin;          //单击返回登录的图标

    @FXML
    private Label userName;                 //左上角显示用户名

    @FXML
    private ChoiceBox<Classification> classList;

    private User user ;

    private String where="";

    public ObservableList<Goods> glist = FXCollections.observableArrayList();       //存储商品列表的list

    public ObservableList<Classification> clist = FXCollections.observableArrayList();       //存储商品列表的list



    /**
    功能描述 :购买记录，显示自己买的东西，按时间顺序排序
    */
    @FXML
    void MyOrder(ActionEvent event) throws Exception{
        new windows_screen().NewWindows(new Stage(), "../view/order.fxml", "购买记录", 592, 435);

    }

    /**
    功能描述 :返回登录，关闭该窗口
    */
    @FXML
    void returnLogin(MouseEvent event) throws Exception{
        Stage stage = (Stage) returnLogin.getScene().getWindow(); //获取到当前窗口
        stage.close();  //关闭当前窗口
        new windows_screen(). NewWindows(new Stage(),"../view/login.fxml","登录",600,384);
    }



    @Override
    /**初始化,自定义列表格式+显示出商品列表*/
    public void initialize(URL url, ResourceBundle rb) {
        try {
            goodsList.setCellFactory(new Callback<ListView<Goods>, ListCell<Goods>>() {

                int index = 0;
                Goods temp = null;

                @Override
                public ListCell<Goods> call(ListView<Goods> param) {

                    param.setOnEditStart(new EventHandler<ListView.EditEvent<Goods>>(){

                        @Override
                        public void handle(ListView.EditEvent<Goods> event){
                            index = event.getIndex();
                            temp = param.getItems().get(index);
                        }

                    });

                    ListCell<Goods> listcell  = new ListCell<Goods>(){

                        @Override
                        //只定义编辑单元格一定要重写的方法
                        protected void updateItem(Goods item, boolean empty) {
                            super.updateItem(item, empty);

                            if(!empty && item != null){
                                HBox hbox = new HBox(40);       //水平布局
                                hbox.setAlignment(Pos.BASELINE_CENTER); //居中对齐

                                Label name =new Label(item.getName());  //商品名
                                name.setMinWidth(100);
                                Label number =new Label(""+item.getNumber());//数量
                                number.setMinWidth(100);
                                Label classification = null;//分类
                                try {
                                    classification = new Label(SelectAll.selectClassification(item.getClassification()).getName());
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                classification.setMinWidth(100);
                                Label price =new Label(String.format("%.2f 元", item.getPrice()));//价格
                                price.setMinWidth(100);

                                TextField buyNumber = new TextField();//购买数量
                                buyNumber.setMaxWidth(100);

                                Button buy =new Button();       //购买按钮
                                buy.setMinWidth(100);
                                buy.setText("购买");

                                buy.setOnAction(new EventHandler<ActionEvent>() {
                                    @Override
                                    public void handle(ActionEvent event) {
                                        char[] number = buyNumber.getText().toCharArray();
                                        int num=0;
                                        boolean fag=true;
                                        for (int i = 0; i < number.length; i++) {
                                            if('0'>number[i]||number[i]>'9')
                                                fag=false;
                                            num=num*10+number[i]-'0';
                                        }
                                        if(fag==false||num==0||num>item.getNumber()){       //如果输入不合法，弹出输入有误对话框
                                            Type.prompt="你的输入有误！";
                                            Type.prompt_add="请重新输入！";
                                            Type.prompt_type=false;
                                            try {
                                                new windows_screen(). NewWindows(new Stage(),"../view/prompt.fxml","提示",445,235);
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                        }else{              //输入合法，则更改商品数量
                                            String sql = "update goods set number="+(item.getNumber()-num)+" where id="+item.getId();
                                            String insertOrder = "insert into orders(goodsId,userId,number,totalPrice)"
                                                    + " values(" + item.getId() + "," + Type.Oline_user + "," + num + ","+item.getPrice()*num+")";

                                            try {
                                                IDU.idu(sql);      //更改商品数量
                                                IDU.idu(insertOrder);  //添加订单
                                                Resfresh.refreshGoodsList(glist,"");              //刷新商品列表
                                                Type.prompt="成功购买"+num+"个"+item.getName()+"!";
                                                Type.prompt_add="谢谢惠顾！";
                                                Type.prompt_type=true;
                                                new windows_screen(). NewWindows(new Stage(),"../view/prompt.fxml","提示",445,235);
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }
                                });
                                hbox.getChildren().addAll(name,number,classification,price,buyNumber,buy);  //在横向布局中添加控件
                                this.setGraphic(hbox);          //应用该横向布局
                            }else if(empty){
                                setText(null);
                                setGraphic(null);
                            }
                        }
                    };
                    return listcell;
                }
            });

            goodsList.setItems(glist);
            Resfresh.refreshGoodsList(glist,where);                   //刷新商品列表

            user = SelectAll.selectUser(Type.Oline_user);
            userName.setText(user.getName());               //左上角显示用户名

            classList.setValue(defaultClass);
            classList.setItems(clist);
            Resfresh.refreshClassChoiceBox(clist,defaultClass);

            /**
            功能描述 ：设置显示内容
            */
            classList.setConverter(new StringConverter<Classification>() {
                @Override
                public String toString(Classification object) {
                    return "分类："+object.getName();
                }
                @Override
                public Classification fromString(String string) {
                    return null;
                }
            });

            classList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {

                @Override
                public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                    if(newValue!=null){
                        try {
                            if(((Classification)newValue).getId()==-1){
                                where="";
                            }else{
                                where=" where classification="+((Classification)newValue).getId();
                            }
                            Resfresh.refreshGoodsList(glist,where);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            });


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
