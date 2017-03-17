package Interface;

import Domain.Post;
import Domain.PostModel;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

import java.awt.*;


/**
 * Created by Utilizator on 27-Nov-16.
 */
public class PostView {

    private PostModel postModel;
    private PostController postController;
    private ObservableList<Post> postList;

    BorderPane background;

    TableView<Post> postTable;
    TableColumn<Post, Integer> postIdColumn;
    TableColumn<Post, String> postNameColumn;
    TableColumn<Post, String> postTipColumn;

    Label idLabel;
    Label nameLabel;
    Label tipLabel;
    Label filterLabel;

    TextField idText;
    TextField nameText;
    TextField tipText;
    TextField filterText;

    Button addBtn;
    Button removeBtn;
    Button updateBtn;

    private Pane createCenterPane(){
        GridPane gridPane = new GridPane();


        idLabel = new Label("ID: ");
        nameLabel=new Label("Denumire: ");
        tipLabel = new Label("Tip:");
        filterLabel = new Label("Filter:");

        idText = new TextField();
        //idText.setEditable(false);
        nameText = new TextField();
       // nameText.setEditable(false);
        tipText = new TextField();
        //tipText.setEditable(false);
        filterText = new TextField();


        gridPane.add(idLabel, 0,0);
        gridPane.add(nameLabel, 0,1);
        gridPane.add(tipLabel, 0,2);
        gridPane.add(filterLabel, 0,3);

        gridPane.add(idText, 1,0);
        gridPane.add(nameText, 1,1);
        gridPane.add(tipText, 1,2);
        gridPane.add(filterText, 1,3);


        filterText.setOnAction(postController.filterTextHandler());


        return gridPane;

    }

    private Pane createBottomPane(){
        HBox hBox = new HBox();
        addBtn = new Button("Add");

        addBtn.setOnAction(postController.addBtnPressedHandler());

        removeBtn = new Button("Remove");

        removeBtn.setOnAction(postController.removeBtnPressedHandler());
        updateBtn = new Button("Update");

        updateBtn.setOnAction(postController.updateBtnPressedHandler());
        hBox.getChildren().add(addBtn);
        hBox.getChildren().add(removeBtn);
        hBox.getChildren().add(updateBtn);

        return hBox;



    }

    private Pane createLeftPane(){
        postTable = new TableView<>();
        postIdColumn = new TableColumn<>("ID");
        postIdColumn.setCellValueFactory(new PropertyValueFactory<Post, Integer>("ID"));
        postNameColumn = new TableColumn<>("Name");
        postNameColumn.setCellValueFactory(new PropertyValueFactory<Post, String>("name"));
        postTipColumn = new TableColumn<>("Tip");
        postTipColumn.setCellValueFactory(new PropertyValueFactory<>("tip"));

        postTable.getColumns().add(postIdColumn);
        postTable.getColumns().add(postNameColumn);
        postTable.getColumns().add(postTipColumn);


        postTable.setItems(postList);

        postTable.getSelectionModel().selectedItemProperty().addListener(postController.postTableSelectHandler());

        return new HBox(postTable);
    }


    private void initComponents(){
        background=new BorderPane();
        background.setLeft(createLeftPane());
        background.setCenter(createCenterPane());
        background.setBottom(createBottomPane());

    }



    public PostView(ObservableList<Post> postModel, PostController postController) {
        this.postList = postModel;
        this.postController = postController;
        postController.setPostView(this);
        initComponents();
    }


    public BorderPane getView(){
        return background;
    }
}
