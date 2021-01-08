package supermarket.ztr.model;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


/***
启动类，启动会打开登录窗口
*/
public class Login_Start extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        AnchorPane root = FXMLLoader.load(getClass().getResource("../view/login.fxml"));
        primaryStage.setResizable(false);                                                       //固定窗口
        primaryStage.getIcons().add(new Image("file:///E:/supermarket/src/supermarket/ztr/image/logo.png"));        //更改窗口图标
        primaryStage.setTitle("登录");
        primaryStage.setScene(new Scene(root, 600, 384));
        primaryStage.show();                                                                      //显示窗口
    }

    public static void main(String[] args) {
        launch(args);
    }
}
