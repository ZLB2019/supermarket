package supermarket.ztr.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import supermarket.ztr.model.JDBC.IDU;
import supermarket.ztr.model.Type;
import supermarket.ztr.model.windows_screen;

/**
注册界面 的控制器
*/
public class Registered {

    @FXML
    private PasswordField Password;     //密码输入框

    @FXML
    private PasswordField ConfirmPassword;      //确认密码输入框

    @FXML
    private TextField userName;         //用户名输入框

    @FXML
    private Button registered_YesOrNo;      //注册按钮

    @FXML
    private Hyperlink ReturnLogin;      //返回登录 的超链接

    /**
    功能描述 :单击超链接，关闭该窗口，打开登录界面
    */
    @FXML
    void ReturnLogin_Action(ActionEvent event) throws Exception {
        Stage stage;
        stage = (Stage) ReturnLogin.getScene().getWindow();
        stage.close();
        new windows_screen().NewWindows(new Stage(), "../view/login.fxml", "登录", 600, 384);
    }

    @FXML
    void registered(ActionEvent event) throws Exception {
        /**特殊判断：  当              密码位数不是6-16                 或           密码为空         或             确认密码为空             或          邮箱格式错误
         * 则调用注册失败窗口
         * ps ：为了减少下面 if（）语句里的内容
         */
        if (Password.getText().length() < 6 || Password.getText().length() > 16 || Password.getText().equals("") || ConfirmPassword.getText().equals("")||userName.getText().equals("")) {
            Type.prompt_type=false;
            Type.prompt="您的输入有误!";
            Type.prompt_add="请重新输入!";
            new windows_screen().NewWindows(new Stage(), "../view/prompt.fxml", "输入有误",445,235);
            return;                                      //  提前结束此方法，因为已经判定输入错误
        }

        /**
         * 显示注册成功窗口
         * */
        new windows_screen().NewWindows(new Stage(), "../view/registered_Yes.fxml", "注册成功", 400, 200);
        Stage stage;
        stage = (Stage) registered_YesOrNo.getScene().getWindow();
        stage.close();
        String sql = "insert into user(id,password,name,type)"
                + " values(" + Registered_Yes.NameInt + ",'" + Password.getText() + "','" + userName.getText() + "',0)";
        IDU.idu(sql);                     /**注册成功，将账号、密码存入数据库,将网名、出生日期、和性别初始化*/
    }

    /***
    功能描述 :鼠标进入该按钮，改变背景颜色
    */
    @FXML
    void registered_YesOrNo_Entered(MouseEvent event) {
        registered_YesOrNo.setStyle("-fx-background-color :  #FF4040 ;");
    }

    /***
     功能描述 :鼠标移出该按钮，改变背景颜色
     */
    @FXML
    void registered_YesOrNo_Exited(MouseEvent event) {
        registered_YesOrNo.setStyle("-fx-background-color :  #FF0000 ;");
    }

}
