package Interface;

import Domain.Sarcina;
import Domain.SarcinaModel;
import Services.SarcinaService;
import Validator.RepositoryException;
import Validator.ValidationException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Created by Utilizator on 04-Dec-16.
 */
public class SarcinaController {

    @FXML
    private TableView<Sarcina> sarcinaTable;

    @FXML
    private TableColumn<Sarcina, Integer> sarcinaIdColumn;

    @FXML
    private TableColumn<Sarcina, String> sarcinaDescriptionColumn;

    @FXML
    private TextField idText;

    @FXML
    private TextField descriptionText;

    @FXML
    private Button sarcinaAddBtn;

    @FXML
    private Button sarcinaRemoveBtn;

    @FXML
    private Button sarcinaUpdateBtn;

    @FXML
    private Button sarcinaAddAddBtn;

    @FXML
    private Button sarcinaAddQuitBtn;

    @FXML
    private TextField sarcinaAddIdText;

    @FXML
    private TextField sarcinaAddDescriptionText;

    @FXML
    private TextField sarcinaUpdateIdText;

    @FXML
    private TextField sarcinaUpdateDescriptionText;

    @FXML
    private TextField filterText;

    @FXML
    private Button sarcinaUpdateUpdateBtn;


    private SarcinaService sarcinaService;
    private SarcinaModel sarcinaModel;

    public SarcinaController(){}

    public SarcinaController(SarcinaService sarcinaService, SarcinaModel sarcinaModel){
        this.sarcinaService = sarcinaService;
        this.sarcinaModel = sarcinaModel;

    }

    public void init(){
        sarcinaTable.setItems(this.sarcinaModel.getModelList());
        sarcinaIdColumn.setCellValueFactory(new PropertyValueFactory<Sarcina, Integer>("ID"));
        sarcinaDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
    }

    public void setSarcinaService(SarcinaService sarcinaService){
        this.sarcinaService = sarcinaService;
    }

    public void setSarcinaModel(SarcinaModel sarcinaModel){
        this.sarcinaModel = sarcinaModel;
    }

    @FXML
    public void sarcinaAddBtn(){
        //
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(this.getClass().getResource("SarcinaAddView.fxml"));
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

    @FXML
    public void sarcinaAddAddBtnHandler(){
        System.out.println("ceva");
        Integer ID;
        String description;
        try {
            ID = Integer.parseInt(sarcinaAddIdText.getText());
            description = sarcinaAddDescriptionText.getText();
            sarcinaService.addSarcina(new Sarcina(ID, description));
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
    public void filterTextHandler(){
        sarcinaService.setFilter( filterText.getText());
    }

    @FXML
    public void sarcinaUpdateBtnHandler(){
        Sarcina selectedSarcina = sarcinaTable.getSelectionModel().getSelectedItem();
        if(selectedSarcina == null){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Eroare:\n" + "Nicio sarcina selectata, nu se poate realiza update!", ButtonType.OK);
            alert.show();
            return;
        }
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(this.getClass().getResource("SarcinaUpdateView.fxml"));
        loader.setController(this);

        try {
            BorderPane pane = loader.load();
            Scene scene = new Scene(pane);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();

            sarcinaUpdateIdText.setText(selectedSarcina.getID().toString());
            sarcinaUpdateDescriptionText.setText(selectedSarcina.getDescription());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void sarcinaUpdateUpdateBtnHandler(){
        Integer ID;
        String description;
        try {
            ID = Integer.parseInt(sarcinaUpdateIdText.getText());
            description = sarcinaUpdateDescriptionText.getText();
           sarcinaService.updateSarcina(new Sarcina(ID, description));

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
    public void sarcinaRemoveBtnHandler(){
        Sarcina selectedSarcina = sarcinaTable.getSelectionModel().getSelectedItem();
        if(selectedSarcina == null){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Eroare:\n" + "Nicio sarcina selectata, nu se poate realiza stergerea!", ButtonType.OK);
            alert.show();
            return;
        }

        try {
            sarcinaService.removeSarcina(selectedSarcina.getID());
        } catch (RepositoryException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Eroare:\n" + e.getMessage(), ButtonType.OK);
            alert.show();
        }
    }

}
