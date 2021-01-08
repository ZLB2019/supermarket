package supermarket.ztr.controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import supermarket.ztr.bean.Classification;
import supermarket.ztr.model.JDBC.IDU;
import supermarket.ztr.model.Resfresh;
import supermarket.ztr.model.Type;
import supermarket.ztr.model.windows_screen;

import java.net.URL;
import java.util.ResourceBundle;


/**
添加商品页面  控制器
*/
public class AddGoods  implements Initializable {

    @FXML
    private TextField name;             //商品名文本输入框

    @FXML
    private Button addGoods;        //添加商品按钮

    @FXML
    private TextField number;       //商品数量

    @FXML
    private ChoiceBox<Classification> classList;  //分类下拉框

    @FXML
    private TextField price;        //商品价格

    private int  classification = -1;

    private ObservableList<Classification> clist = FXCollections.observableArrayList();       //存储商品列表的list

    /**
    功能描述 :单击添加商品按钮，执行下面操作
    */
    @FXML
    void addGoods(ActionEvent event) throws Exception {
        Boolean fag = price.getText().matches("^\\d+(\\.\\d+)?$");      //判断价格是否合法，利用正则表达式
        char[] numberChar = number.getText().toCharArray();
        int num = 0;
        for (int i = 0; i < numberChar.length; i++) {                       //计算num
            if ('0' > numberChar[i] || numberChar[i] > '9')                 //如果出现非数字，直接判断为不合法
                fag = false;
            num = num * 10 + numberChar[i] - '0';
        }
        if (fag == false || num < 0 || name.getText().equals("") || classification==-1) { //如果输入不合法

            /**
            设置提示的消息
            */
            Type.prompt = "你的输入有误！";
            Type.prompt_add = "请重新输入！";
            Type.prompt_type = false;

            new windows_screen().NewWindows(new Stage(), "../view/prompt.fxml", "提示", 445, 235);   //调出提示窗帘
        } else {//输入内容合法，添加

            String sql = "insert into goods(name,number,price,classification)"
                    + " values('" + name.getText() + "'," + number.getText() + "," + price.getText() + "," + classification + ")";
            IDU.idu(sql);                        //更改商品数量
            Resfresh.refreshGoodsList(Type.glist,"");              //刷新商品列表
            Type.prompt = "添加商品成功!";
            Type.prompt_add = "";
            Type.prompt_type = true;
            Stage stage = (Stage) addGoods.getScene().getWindow();
            stage.close();
            new windows_screen().NewWindows(new Stage(), "../view/prompt.fxml", "提示", 445, 235);
        }
    }

    /***
    功能描述 :鼠标进入‘添加商品’按钮改变颜色
    */
    @FXML
    void add_Entered(MouseEvent event) {
        addGoods.setStyle("-fx-background-color : #EEB4B4 ; -fx-background-radius : 6;");
    }

    /***
     功能描述 :鼠标移出‘添加商品’按钮改变颜色
     */
    @FXML
    void add_Exited(MouseEvent event) {
        addGoods.setStyle("-fx-background-color : #FFB6C1 ; -fx-background-radius : 6;");
    }

    public void initialize(URL location, ResourceBundle resources){
        try {
            classList.setItems(clist);
            Resfresh.refreshClassChoiceBox(clist);

            /**
             功能描述 ：设置显示内容
             */
            classList.setConverter(new StringConverter<Classification>() {
                @Override
                public String toString(Classification object) {
                    return object.getName();
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
                        classification=((Classification)newValue).getId();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
