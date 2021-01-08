package supermarket.ztr.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import supermarket.ztr.model.Type;

import java.net.URL;
import java.util.ResourceBundle;

/**
提示窗口 的控制器
*/
public class Prompt implements Initializable {

    @FXML
    private Button Close;           //确认按钮，单击会关闭该窗口

    @FXML
    private Label prompt;          //提示信息

    @FXML
    private Label prompt_add;       //提示信息补充

    /**
    功能描述 :单击按钮会执行，关闭该窗口
    */
    @FXML
    void Close(ActionEvent event) throws Exception{
        Stage stage;
        stage = (Stage) Close.getScene().getWindow();
        stage.close();
    }

    @Override
    /**初始化,设置提示消息，和颜色*/
    public void initialize(URL url, ResourceBundle rb) {
        prompt.setText(Type.prompt);
        if(Type.prompt_type)
            prompt.setStyle("-fx-text-fill :  #00FF00 ;");
        else
            prompt.setStyle("-fx-text-fill :  Red ;");
        prompt_add.setText(Type.prompt_add);
    }
}
