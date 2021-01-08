package supermarket.ztr.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import supermarket.ztr.bean.Classification;
import supermarket.ztr.bean.Goods;
import supermarket.ztr.model.JDBC.IDU;
import supermarket.ztr.model.JDBC.SelectAll;
import supermarket.ztr.model.Resfresh;
import supermarket.ztr.model.Type;
import supermarket.ztr.model.windows_screen;

import java.net.URL;
import java.util.ResourceBundle;

/**
分类管理的页面处理器
*/
public class ClassManage implements Initializable {

    @FXML
    private Button addClass;        //添加按钮

    @FXML
    private TextField name;         //分类名文本框

    @FXML
    private ListView<Classification> classList;     //显示列表

    public ObservableList<Classification> clist = FXCollections.observableArrayList();       //存储商品列表的list

    /**
    功能描述 :添加商品
    */
    @FXML
    void addClass(ActionEvent event)throws Exception {
        if (name.getText().equals("")) { //如果输入不合法

            /**
             设置提示的消息
             */
            Type.prompt = "你的输入有误！";
            Type.prompt_add = "请重新输入！";
            Type.prompt_type = false;

            new windows_screen().NewWindows(new Stage(), "../view/prompt.fxml", "提示", 445, 235);   //调出提示窗帘
        } else {//输入内容合法，添加

            String sql = "insert into classification(name)"
                    + " values('" + name.getText()+"')";
            IDU.idu(sql);                        //添加分类名
            Type.clist.add(SelectAll.selectClassification(name.getText()));
            Resfresh.refreshClassList(clist);              //刷新商品列表
            Type.prompt = "添加分类成功!";
            Type.prompt_add = "";
            Type.prompt_type = true;
            new windows_screen().NewWindows(new Stage(), "../view/prompt.fxml", "提示", 445, 235);
        }
    }

    /***
     功能描述 :鼠标进入‘添加商品’按钮改变颜色
     */
    @FXML
    void add_Entered(MouseEvent event) {
        addClass.setStyle("-fx-background-color : #EEB4B4 ; -fx-background-radius : 6;");
    }

    /***
     功能描述 :鼠标移出‘添加商品’按钮改变颜色
     */
    @FXML
    void add_Exited(MouseEvent event) {
        addClass.setStyle("-fx-background-color : #FFB6C1 ; -fx-background-radius : 6;");
    }

    @Override
    /**初始化分类列表*/
    public void initialize(URL url, ResourceBundle rb) {
        classList.setCellFactory(new Callback<ListView<Classification>, ListCell<Classification>>() {

            int index = 0;
            Classification temp = null;

            @Override
            public ListCell<Classification> call(ListView<Classification> param) {

                param.setOnEditStart(new EventHandler<ListView.EditEvent<Classification>>(){

                    @Override
                    public void handle(ListView.EditEvent<Classification> event){
                        index = event.getIndex();
                        temp = param.getItems().get(index);
                    }

                });

                ListCell<Classification> listcell  = new ListCell<Classification>(){

                    @Override
                    //只定义编辑单元格一定要重写的方法
                    protected void updateItem(Classification item, boolean empty) {
                        super.updateItem(item, empty);

                        if(!empty && item != null){
                            HBox hbox = new HBox(20);       //水平布局
                            hbox.setAlignment(Pos.BASELINE_CENTER); //居中对齐

                            TextField name =new TextField(item.getName());  //分类名
                            name.setStyle("-fx-font-size : 15");
                            name.setMaxWidth(80);

                            Button update =new Button();        //修改商品信息按钮
                            update.setMinWidth(80);
                            update.setText("修改");
                            update.setStyle("-fx-font-size : 15");


                            Button del =new Button();           //删除商品 按钮
                            del.setMinWidth(80);
                            del.setText("删除");
                            del.setStyle("-fx-font-size : 15");


                            /**
                             功能描述 :设置修改按钮的单击事件
                             */
                            update.setOnAction(new EventHandler<ActionEvent>() {
                                @Override
                                public void handle(ActionEvent event) {
                                    try {
                                        int num=0;
                                        if(name.getText().equals("")){ //只要有分类名输入为空
                                            Type.prompt="你的输入有误！";
                                            Type.prompt_add="请重新输入！";
                                            Type.prompt_type=false;
                                            try {
                                                new windows_screen(). NewWindows(new Stage(),"../view/prompt.fxml","提示",445,235);  //打开提示窗口
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                        }else{ //全部输入合法，则可以修改商品的信息
                                            String sql = "update classification set name='"+name.getText()+"' where id="+item.getId();
                                            try {
                                                IDU.idu(sql);      //更改分类名
                                                Resfresh.refreshClassList(clist);              //刷新分类列表

                                                /**
                                                 设置提示信息，并弹出提示窗口
                                                 */
                                                Type.prompt="修改成功!";
                                                Type.prompt_add="";
                                                Type.prompt_type=true;
                                                Resfresh.refreshClassChoiceBox(Type.clist,Type.defaultClass);
                                                new windows_screen(). NewWindows(new Stage(),"../view/prompt.fxml","提示",445,235);
                                            } catch (Exception e) {
                                                e.printStackTrace();

                                            }
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            });

                            /**
                             功能描述 :设置删除分类的单击事件
                             */
                            del.setOnAction(new EventHandler<ActionEvent>() {
                                @Override
                                public void handle(ActionEvent event) {
                                    try {
                                        String sql = "delete from classification where id="+item.getId();
                                        IDU.idu(sql);                      //执行删除操作
                                        Resfresh.refreshClassList(clist);              //刷新商品列表

                                        /**
                                         功能描述 :设置删除成功的提示信息，并打开提示窗口
                                         */
                                        Type.prompt="删除该商品成功！";
                                        Type.prompt_add="";
                                        Type.prompt_type=true;
                                        Resfresh.refreshClassChoiceBox(Type.clist,Type.defaultClass);

                                        new windows_screen(). NewWindows(new Stage(),"../view/prompt.fxml","提示",445,235);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                            hbox.getChildren().addAll(name,update,del);  //在横向布局中添加控件
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

        try {
            classList.setItems(clist);
            Resfresh.refreshClassList(clist);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
