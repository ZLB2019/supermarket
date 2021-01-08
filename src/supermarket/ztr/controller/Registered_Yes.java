package supermarket.ztr.controller;

import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import supermarket.ztr.model.windows_screen;

import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

public class Registered_Yes implements Initializable {
    @FXML
    private Button registered_Yes_return_load;                  /**注册成功界面的‘确定’按钮*/
    @FXML
    private Label UserName;                                 /**注册成功界面的‘UserName’标签*/


    public static int NameInt = 0;                      //注册成功生成的账号

    /**注册成功界面单击‘确定’：
     * 打开登录界面，并且关闭注册成功界面*/
    @FXML
    public void registered_Yes_return_load() {
        try {
            new windows_screen(). NewWindows(new Stage(),"../view/login.fxml","登录",600,384);
            Stage stage;
            stage = (Stage) registered_Yes_return_load.getScene().getWindow();
            stage.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    /**初始化,生成一个五位数的账号、判重*/
    public void initialize(URL url, ResourceBundle rb) {
        try {
            Random random = new Random();
            NameInt = random.nextInt(90000)+10000;
            UserName.setText(NameInt+"");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}