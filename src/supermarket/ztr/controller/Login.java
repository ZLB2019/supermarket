package supermarket.ztr.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import supermarket.ztr.bean.User;
import supermarket.ztr.model.JDBC.IDU;
import supermarket.ztr.model.JDBC.SelectAll;
import supermarket.ztr.model.Type;
import supermarket.ztr.model.windows_screen;

import java.net.URL;
import java.util.ResourceBundle;

/** 登录窗口的控制器*/
public class Login implements Initializable {

    @FXML
    private Hyperlink RetrievePassword;                 /**登录界面的‘忘记密码’超链接*/
    @FXML
    public  TextField UserName;                        /**登录界面的‘账号’输入框*/
    @FXML
    private PasswordField Password;                  /**登录界面的‘密码’输入框*/
    @FXML
    private Hyperlink registered;                       /**登录界面的‘注册’按钮*/
    @FXML
    private Button Load;                              /**登录界面的‘登录’按钮*/
    @FXML
    private Button load_no_return_load;                  /**登录失败界面的‘返回’按钮*/

    /**登录界面单击‘注册’：
     * 打开注册界面，并且关闭登录界面*/
    @FXML
    public void registered() throws Exception{
        new windows_screen(). NewWindows(new Stage(),"../view/registered.fxml","注册",555,589);
        Stage stage;
        stage = (Stage) registered.getScene().getWindow();
        stage.close();
    }

    /**登录界面单击‘登录’：
     * 账号密码错误： 关闭登录界面，弹出登录失败对话框
     * 正确则关闭登录界面，进去开心超市主界面
     * */
    @FXML
    public void Load() throws Exception{
        /**特殊判断
         * 当账号或者密码框为 null 时
         * 调用登录失败窗口
         * 并退出此方法
         * */
        if(UserName.getText().equals("")||Password.getText().equals("")) {
            new windows_screen(). NewWindows(new Stage(),"../view/prompt.fxml","登录失败",445,235);
            return;                                                  //  提前结束此方法，因为已经判定输入错误
        }

        /**获取账号*/
        int id = 0;
        try {
            id = Integer.parseInt(UserName.getText());               //获得 int 型的账号
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        /**获取密码*/
        String pass = Password.getText();

        //用于获取用户信息
        User UserMessage= SelectAll.selectUser(id);
        /**判断输入的账号密码是否匹配
         * 匹配则进入’主界面‘，关闭’登录‘界面
         * 不匹配贼弹出’登录失败‘界面
         */
        if(!(UserMessage.getId()==0||!(UserMessage.getPassword().equals(pass)))){
            Type.Oline_user = id;              //标记该id用户登录成功
            if(UserMessage.getType()==0)        //代表普通用户
                 new windows_screen(). NewWindows(new Stage(),"../view/Main.fxml","开心超市",838,603);
            else
                 new windows_screen(). NewWindows(new Stage(),"../view/administrator.fxml","开心超市后台管理",960,688);

            Stage stage = (Stage) registered.getScene().getWindow(); //获取到当前窗口
            stage.close();  //关闭当前窗口
        }
        else {  //密码错误，打开登录失败窗口
            new windows_screen(). NewWindows(new Stage(),"../view/prompt.fxml","提示",445,235);
        }
    }

    /**
    功能描述 :鼠标进入时，改变按钮背景颜色
    */
    @FXML
    void Load_Entered(MouseEvent event) {
        Load.setStyle(" -fx-background-color: #00FFFF; -fx-background-radius: 80;");
    }

    /**
     功能描述 :鼠标移出时，改变按钮背景颜色
     */
    @FXML
    void Load_Exited(MouseEvent event) {
        Load.setStyle("-fx-background-color : #00F5FF ; -fx-background-radius : 80;");
    }

    /**
    功能描述 :初始化
    */
    public void initialize(URL url, ResourceBundle rb)
    {
        /**
        功能描述 :初始化时提前设置好登录失败的提示信息
        */
        Type.prompt="账号或密码错误！";
        Type.prompt_add="请重新输入！";
        Type.prompt_type=false;

        if(!(Registered_Yes.NameInt+"").equals("0")){
            UserName.setText(Registered_Yes.NameInt+"");          //用户注册成功，将刚注册的账号
        }
        if(Type.Oline_user!=0){                             //如果还记录了登录的用户，则消除，代表退出了
            UserName.setText(""+ Type.Oline_user);
            Type.Oline_user=0;                                 //标记无 用户 已经下线
        }
    }
}