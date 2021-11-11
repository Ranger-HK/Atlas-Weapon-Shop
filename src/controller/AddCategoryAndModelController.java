package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import model.Category;
import model.Model;
import util.Validation;
import view.tm.CategoryTM;
import view.tm.ModelTM;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class AddCategoryAndModelController {
    public AnchorPane contextCategoryAndModel;
    public Label lblDate;
    public Label lblTime;
    public JFXTextField txtCategoryId;
    public JFXTextField txtCategoryName;
    public JFXTextField txtModelId;
    public JFXTextField txtModelName;
    public TableView<CategoryTM> tblCategory;
    public TableColumn colCategoryId;
    public TableColumn colCategoryName;
    public TableView<ModelTM> tblModel;
    public TableColumn colModelId;
    public TableColumn colModelName;
    public JFXButton btnAddCategory;
    public JFXButton btnAddModel;
    public JFXButton btnRemoveModels;
    public JFXButton btnRemoveCategory;



    LinkedHashMap<TextField , Pattern> map = new LinkedHashMap<>();
    LinkedHashMap<TextField , Pattern> map1 = new LinkedHashMap<>();

    Pattern modelId = Pattern.compile("^(M-|m-)[0-9]{3,4}$");
    Pattern categoryId = Pattern.compile("^(C-|c-)[0-9]{3,4}$");
    Pattern modelName = Pattern.compile("^[A-z0-9].{2,15}$");
    Pattern categoryName = Pattern.compile("^[A-z0-9].{2,15}$");


    private void storeValidation(){
        map.put(txtModelId,modelId);
        map1.put(txtCategoryId,categoryId);
        map.put(txtModelName,modelName);
        map1.put(txtCategoryName,categoryName);
    }


    public void initialize() throws SQLException, ClassNotFoundException {
        storeValidation();
        btnAddModel.setDisable(true);
        btnAddCategory.setDisable(true);
        loadDateAndTime();
        setModelToTable();
        setCategoryToTable();

    }


    private void loadDateAndTime() {
        Date date = new Date();
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MMMM-dd");
        lblDate.setText(f.format(date));

        Timeline time = new Timeline(new KeyFrame(Duration.ZERO, e->{
            LocalTime currentTime = LocalTime.now();
            lblTime.setText(
                    currentTime.getHour()+ " : "+currentTime.getMinute()+ " : "+currentTime.getSecond()
            );
        }),
                new KeyFrame(Duration.seconds(1))
        );
        time.setCycleCount(Animation.INDEFINITE);
        time.play();
    }


    public void setModelToTable() throws SQLException, ClassNotFoundException {
        ObservableList<ModelTM> model = new ModelServiceController().getModelList();
        colModelId.setCellValueFactory(new PropertyValueFactory<>("modelId"));
        colModelName.setCellValueFactory(new PropertyValueFactory<>("modelName"));

        tblModel.setItems(model);
    }


    public void setCategoryToTable() throws SQLException, ClassNotFoundException {
        ObservableList<CategoryTM> category = new CategoryServiceController().getCategoryList();
        colCategoryId.setCellValueFactory(new PropertyValueFactory<>("categoryId"));
        colCategoryName.setCellValueFactory(new PropertyValueFactory<>("categoryName"));

        tblCategory.setItems(category);
    }



    public void btnAddCategoryOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        Category category = new Category(
                txtCategoryId.getText(),
                txtCategoryName.getText()
        );
        if (new CategoryServiceController().saveCategory(category)){
            new AddNotifications().sceneNotifications("CONFIRMATION","Category Save Completed..",0,1);
            setCategoryToTable();
            btnAddCategory.setDisable(true);
        }else {
            new AddNotifications().sceneNotifications("WARNING","Try Again..",0,5);
        }
        clearFieldCategory();


    }



    public void btnRemoveCategoryOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        CategoryTM category = tblCategory.getSelectionModel().getSelectedItem();
        String categoryName = category.getCategoryId();

        if (new CategoryServiceController().deleteCategory(categoryName)){
            new AddNotifications().sceneNotifications("CONFIRMATION","Category Remove Completed..",0,3);
            setCategoryToTable();

        }else {
            new AddNotifications().sceneNotifications("WARNING","Try Again..",0,5);
        }
        clearFieldCategory();

    }



    public void btnAddModelOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        Model model = new Model(
                txtModelId.getText(),
                txtModelName.getText()

        );
        if (new ModelServiceController().saveModel(model)){
            new AddNotifications().sceneNotifications("CONFIRMATION","Model Save Completed..",0,1);
            setModelToTable();
            btnAddModel.setDisable(true);
        }else {
            new AddNotifications().sceneNotifications("WARNING","Try Again..",0,5);
        }
        clearFieldModel();

    }



    public void btnRemoveModelOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        ModelTM model = tblModel.getSelectionModel().getSelectedItem();
        String modelName = model.getModelId();

        if(new ModelServiceController().deleteModel(modelName)){
            new AddNotifications().sceneNotifications("CONFIRMATION","Model Remove Completed..",0,3);
            setModelToTable();

        }else {
            new AddNotifications().sceneNotifications("WARNING","Try Again..",0,5);
        }
        clearFieldModel();


    }

    private void clearFieldModel(){
        txtModelId.clear();
        txtModelName.clear();
    }


    private void clearFieldCategory(){
        txtCategoryId.clear();
        txtCategoryName.clear();
    }



    public void moveToCategoryName(ActionEvent actionEvent) {
        txtCategoryName.requestFocus();
    }

    public void moveToModelName(ActionEvent actionEvent) {
        txtModelName.requestFocus();

    }


    public void txtModelKeyRelease(KeyEvent keyEvent) {
        btnAddModel.setDisable(true);
        Object response = Validation.validate(map,btnAddModel);
        if (keyEvent.getCode()== KeyCode.ENTER){
            if (response instanceof TextField){
                TextField error = (TextField) response;
                error.requestFocus();
            }else if (response instanceof Boolean){
                new AddNotifications().sceneNotifications("CONFIRMATION"," Success ",0,1);

            }
        }

    }


    public void txtCategoryKeyRelease(KeyEvent keyEvent) {
        btnAddCategory.setDisable(true);
        Object response = Validation.validate(map1,btnAddCategory);
        if (keyEvent.getCode()== KeyCode.ENTER){
            if (response instanceof TextField){
                TextField error = (TextField) response;
                error.requestFocus();
            }else if (response instanceof Boolean){
                new AddNotifications().sceneNotifications("CONFIRMATION"," Success ",0,1);

            }
        }

    }
}
