package supermarket.ztr.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import supermarket.ztr.bean.User;
import supermarket.ztr.model.JDBC.IDU;
import supermarket.ztr.model.Resfresh;
import supermarket.ztr.model.Type;
import supermarket.ztr.model.windows_screen;

import java.net.URL;
import java.util.ResourceBundle;

/***
用户管理页面 控制器
*/
public class UserManage  implements Initializable{

    @FXML
    private ListView<User> userList;            //用户列表

    public ObservableList<User> ulist = FXCollections.observableArrayList();       //存储商品列表的list


    /**
    初始化用户列表
    */
    public void initialize(URL location, ResourceBundle resources){
        userList.setCellFactory(new Callback<ListView<User>, ListCell<User>>() {

            int index = 0;
            User temp = null;

            @Override
            public ListCell<User> call(ListView<User> param) {

                param.setOnEditStart(new EventHandler<ListView.EditEvent<User>>(){

                    @Override
                    public void handle(ListView.EditEvent<User> event){
                        index = event.getIndex();
                        temp = param.getItems().get(index);
                    }

                });

                ListCell<User> listcell  = new ListCell<User>(){

                    @Override
                    //只定义编辑单元格一定要重写的方法
                    protected void updateItem(User item, boolean empty) {
                        super.updateItem(item, empty);

                        if(!empty && item != null){
                            HBox hbox = new HBox(20);       //水平布局
                            hbox.setAlignment(Pos.BASELINE_CENTER); //居中对齐

                            Label id =new Label(item.getId()+"");  //用户ID
                            id.setStyle("-fx-font-size : 15");
                            id.setMinWidth(50);

                            TextField password =new TextField(item.getPassword());  //用户密码
                            password.setStyle("-fx-font-size : 15");
                            password.setMaxWidth(80);

                            TextField name =new TextField(item.getName());  //用户密码
                            name.setStyle("-fx-font-size : 15");
                            name.setMaxWidth(70);


                            Button update =new Button();        //修改用户信息按钮
                            //update.setMinWidth(70);
                            update.setText("修改");
                            update.setStyle("-fx-font-size : 15");


                            Button del =new Button();           //删除商品 按钮
                           // del.setMinWidth(70);
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
                                        if(name.getText().equals("")||password.getText().equals("")||password.getText().length()<6||password.getText().length()>16){ //只要有用户名和密码输入为空
                                            Type.prompt="你的输入有误！";
                                            Type.prompt_add="注意密码是6-16位";
                                            Type.prompt_type=false;
                                            try {
                                                new windows_screen(). NewWindows(new Stage(),"../view/prompt.fxml","提示",445,235);  //打开提示窗口
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                        }else{ //全部输入合法，则可以修改商品的信息
                                            String sql = "update user set name='"+name.getText()+"',password='"+password.getText()+"' where id="+item.getId();
                                            System.out.println(sql);
                                            try {
                                                IDU.idu(sql);      //更改分类名
                                                Resfresh.refreshUserList(ulist);              //刷新分类列表

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
                                        String sql = "delete from user where id="+item.getId();
                                        IDU.idu(sql);                      //执行删除操作
                                        Resfresh.refreshUserList(ulist);              //刷新商品列表

                                        /**
                                         功能描述 :设置删除成功的提示信息，并打开提示窗口
                                         */
                                        Type.prompt="删除该用户成功！";
                                        Type.prompt_add="";
                                        Type.prompt_type=true;
                                        Resfresh.refreshClassChoiceBox(Type.clist,Type.defaultClass);

                                        new windows_screen(). NewWindows(new Stage(),"../view/prompt.fxml","提示",445,235);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                            hbox.getChildren().addAll(name,password,update,del);  //在横向布局中添加控件
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
            userList.setItems(ulist);
            Resfresh.refreshUserList(ulist);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
