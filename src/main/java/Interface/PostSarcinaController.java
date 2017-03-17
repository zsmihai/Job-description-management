package Interface;

import Domain.*;
import Services.PostSarcinaService;
import Validator.RepositoryException;
import Validator.ValidationException;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Created by Utilizator on 06-Dec-16.
 */
public class PostSarcinaController {

    @FXML
    private TableView<Post> postTable;

    @FXML
    private TableView<Sarcina> sarcinaTable;


    @FXML
    private TableColumn<Post, Integer> postIDColumn;

    @FXML
    private TableColumn<Post, String> postNameColumn;

    @FXML
    private TableColumn<Post, String> postTipColumn;

    @FXML
    private TableColumn<Sarcina, Integer> sarcinaIdColumn;

    @FXML
    private TableColumn<Sarcina, String> sarcinaDescriptionColumn;


    @FXML
    private Button postSarcinaRemoveBtn;

    @FXML
    private Button postSarcinaAddBtn;



    @FXML
    private Button postSarcinaAddAddBtn;

    @FXML
    private TextField postIdText;

    @FXML
    private TextField sarcinaIdText;


    private PostSarcinaService postSarcinaService;
    private PostSarcinaModel postSarcinaModel;
    private PostModel postModel;

    public PostSarcinaController(){}

    public PostSarcinaController(PostSarcinaService postSarcinaService, PostSarcinaModel postSarcinaModel, PostModel postModel){
        this.postSarcinaModel = postSarcinaModel;
        this.postSarcinaService = postSarcinaService;
        this.postModel = postModel;
    }


    @FXML
    public void postSarcinaAddAddBtnHandler(){
        Integer postID, sarcinaID;

        try {
            postID = Integer.parseInt(postIdText.getText());
            sarcinaID = Integer.parseInt( sarcinaIdText.getText());
            postSarcinaService.addPostSarcina(new PostSarcina(postID, sarcinaID));
            System.out.println("ceva");
        }
        catch (NumberFormatException e){
            Alert alert = new Alert(Alert.AlertType.ERROR, "ID invalid, dati un numar", ButtonType.OK);
            alert.show();
        } catch (RepositoryException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Eroare:\n" + e.getMessage(), ButtonType.OK);
            alert.show();
        } catch (ValidationException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Eroare:\n" + e.getMessage(), ButtonType.OK);
            alert.show();
        }
    }

    @FXML
    public void postSarcinaAddBtnHandler(){
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(this.getClass().getResource("PostSarcinaAddView.fxml"));
        loader.setController(this);

        try {
            BorderPane pane = loader.load();
            Scene scene = new Scene(pane);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public void init(){

        sarcinaTable.setItems(this.postSarcinaModel.getPostSarcinaModel());
        postTable.setItems(this.postModel.getModelList());
        sarcinaIdColumn.setCellValueFactory(new PropertyValueFactory<Sarcina, Integer>("ID"));
        sarcinaDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));

        postIDColumn.setCellValueFactory(new PropertyValueFactory<Post, Integer>("ID"));
        postNameColumn.setCellValueFactory(new PropertyValueFactory<Post, String>("nume"));
        postTipColumn.setCellValueFactory(new PropertyValueFactory<Post, String>("tip"));


        postTable.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<Post>() {
                    @Override
                    public void changed(ObservableValue<? extends Post> observable, Post oldValue, Post newValue) {
                        postSarcinaModel.setCurrentPost(newValue);
                        System.out.println("ceva");
                    }
                }
        );

    }

    @FXML
    private void postSarcinaRemoveBtnHandler(){
        Post post = postTable.getSelectionModel().getSelectedItem();
        Sarcina sarcina = sarcinaTable.getSelectionModel().getSelectedItem();

        if(post == null){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Niciun post selectat", ButtonType.OK);
            alert.show();
        }
        else if(sarcina == null){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Nicio sarcina selectata", ButtonType.OK);
            alert.show();
        }
        else{

            try {
                postSarcinaService.removePostSarcina(new PostSarcina(post.getID(), sarcina.getID()) );
            } catch (RepositoryException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Eroare:\n" + e.getMessage(), ButtonType.OK);
                alert.show();
            }
        }
    }

}
