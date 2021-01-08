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
import supermarket.ztr.model.JDBC.IDU;
import supermarket.ztr.model.JDBC.SelectAll;
import supermarket.ztr.model.Resfresh;
import supermarket.ztr.model.Type;
import supermarket.ztr.model.windows_screen;

import java.net.URL;
import java.util.ResourceBundle;

import static supermarket.ztr.model.Type.defaultClass;

/**
超市后台管理页面 控制器
*/
public class Administrator implements Initializable {

    @FXML
    private ListView<Goods> goodsList;           //列表控件

    public ObservableList<Goods> glist = FXCollections.observableArrayList();        //列表控件内存储的列表

    @FXML
    private Button addGoods;                    //添加商品按钮

    @FXML
    private ImageView returnLogin;              //单击就会 返回登录 的图标

    @FXML
    private ChoiceBox<Classification> classList;

    private String where="";

    public ObservableList<Classification> clist = FXCollections.observableArrayList();       //存储商品列表的list


    /**
    功能描述 :鼠标进入改变按钮背景元素
    */
    @FXML
    void addGoods_Entered(MouseEvent event) {
        addGoods.setStyle("-fx-background-color : #00BFFF ; -fx-background-radius : 25;");
    }

    /**
     功能描述 :鼠标移出改变按钮背景元素
     */
    @FXML
    void addGoods_Exited(MouseEvent event) {
        addGoods.setStyle("-fx-background-color : #87CEFA ; -fx-background-radius : 25;");
    }


    /***
    功能描述 :单击添加商品按钮，打开添加商品页面
    */
    @FXML
    void addGoods(ActionEvent event) throws Exception{
        Type.glist=glist;
        new windows_screen(). NewWindows(new Stage(),"../view/addGoods.fxml","添加商品",470,582);
    }


    /**
    功能描述 :关闭后台管理页面，回到登录页面
    */
    @FXML
    void returnLogin(MouseEvent event) throws Exception{
        Stage stage = (Stage) returnLogin.getScene().getWindow(); //获取到当前窗口
        stage.close();  //关闭当前窗口
        new windows_screen(). NewWindows(new Stage(),"../view/login.fxml","登录",600,384);//打开登录界面
    }


    /**
    功能描述 :查看所有用户订单
    */
    @FXML
    void allOrder(ActionEvent event) throws Exception{
        new windows_screen().NewWindows(new Stage(), "../view/order.fxml", "全部订单", 592, 435);
    }

    /**
     功能描述 :分类管理
     */
    @FXML
    void classManage(ActionEvent event) throws Exception {
        Type.clist=clist;
        new windows_screen(). NewWindows(new Stage(),"../view/classManage.fxml","添加商品",470,582);
    }


    /***
    功能描述 :用户管理
    */
    @FXML
    void userMange(ActionEvent event) throws Exception{
        new windows_screen(). NewWindows(new Stage(),"../view/userManage.fxml","用户管理",470,582);
    }

    @Override
    /**初始化,自定义列表格式+初始化显示商品列表*/
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
                                HBox hbox = new HBox(55);       //水平布局
                                hbox.setAlignment(Pos.BASELINE_CENTER); //居中对齐

                                TextField name =new TextField(item.getName());  //商品名
                                name.setMaxWidth(130);
                                TextField number =new TextField(""+item.getNumber());//数量
                                number.setMaxWidth(75);
                                Label classification = null;//分类
                                try {
                                    classification = new Label(SelectAll.selectClassification(item.getClassification()).getName());
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                classification.setMinWidth(130);
                                TextField price =new TextField(String.format("%.2f", item.getPrice()));//价格
                                price.setMaxWidth(75);

                                Button update =new Button();        //修改商品信息按钮
                                update.setMinWidth(100);
                                update.setText("修改");

                                Button del =new Button();           //删除商品 按钮
                                del.setMinWidth(100);
                                del.setText("删除");

                                /**
                                功能描述 :设置修改按钮的单击事件
                                */
                                update.setOnAction(new EventHandler<ActionEvent>() {
                                    @Override
                                    public void handle(ActionEvent event) {
                                        Boolean fag = price.getText().matches("^\\d+(\\.\\d+)?$");  //判断价格是否核发
                                        char[] numberChar = number.getText().toCharArray();
                                        int num=0;
                                        for (int i = 0; i < numberChar.length; i++) {
                                            if('0'>numberChar[i]||numberChar[i]>'9')        //如果遇到非数字，直接判断不合法
                                                fag=false;
                                            num=num*10+numberChar[i]-'0';
                                        }
                                        if(fag==false||num<0||name.getText().equals("")){ //只要有一样不合法，就提示输入有误！
                                            Type.prompt="你的输入有误！";
                                            Type.prompt_add="请重新输入！";
                                            Type.prompt_type=false;
                                            try {
                                                new windows_screen(). NewWindows(new Stage(),"../view/prompt.fxml","提示",445,235);  //打开提示窗口
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                        }else{ //全部输入合法，则可以修改商品的信息
                                            String sql = "update goods set number="+number.getText()+",name='"+name.getText()+"',price="+price.getText()+" where id="+item.getId();
                                            try {
                                                System.out.println(sql);
                                                IDU.idu(sql);      //更改商品数量
                                                Resfresh.refreshGoodsList(glist,where);              //刷新商品列表

                                                /**
                                                设置提示信息，并弹出提示窗口
                                                */
                                                Type.prompt="修改成功!";
                                                Type.prompt_add="";
                                                Type.prompt_type=true;
                                                new windows_screen(). NewWindows(new Stage(),"../view/prompt.fxml","提示",445,235);
                                            } catch (Exception e) {
                                                e.printStackTrace();

                                            }
                                        }
                                    }
                                });

                                /**
                                功能描述 :设置删除商品的单击事件
                                */
                                del.setOnAction(new EventHandler<ActionEvent>() {
                                    @Override
                                    public void handle(ActionEvent event) {
                                        try {
                                            String sql = "delete from goods where id="+item.getId();
                                            IDU.idu(sql);                      //执行删除操作
                                            Resfresh.refreshGoodsList(glist,where);              //刷新商品列表

                                            /**
                                            功能描述 :设置删除成功的提示信息，并打开提示窗口
                                            */
                                            Type.prompt="删除该商品成功！";
                                            Type.prompt_add="";
                                            Type.prompt_type=true;
                                            new windows_screen(). NewWindows(new Stage(),"../view/prompt.fxml","提示",445,235);
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                                hbox.getChildren().addAll(name,number,classification,price,update,del);  //在横向布局中添加控件
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

            goodsList.setItems(glist);                //将该列表应用到ListView控件
            Resfresh.refreshGoodsList(glist,where);              //刷新商品列表

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
