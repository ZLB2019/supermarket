package supermarket.ztr.model;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/***
打开窗口类，将需要打开的窗口路径、窗口名、窗口宽度、窗口高度传入里面的方法，便可以打开一个窗口
*/
public class windows_screen extends Application {

    public void NewWindows(Stage primaryStage, String fxml, String Title, int w, int h) throws Exception {
        AnchorPane root = FXMLLoader.load(getClass().getResource(fxml));
        primaryStage.setResizable(false);
        primaryStage.getIcons().add(new Image("file:///E:/supermarket/src/supermarket/ztr/image/logo.png"));        //更改窗口图标
        primaryStage.setTitle(Title);
        primaryStage.setScene(new Scene(root, w, h));
        primaryStage.show();
    }
    @Override
    public void start(Stage primaryStage){
    }
}