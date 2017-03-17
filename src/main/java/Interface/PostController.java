package Interface;

import Domain.Post;
import Services.PostService;
import Validator.PostValidator;
import Validator.RepositoryException;
import Validator.ValidationException;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

/**
 * Created by Utilizator on 27-Nov-16.
 */
public class PostController {

    private PostService postService;
    private PostView postView;

    public void setPostView(PostView postView){
        this.postView = postView;
    }

    public PostController(PostService postService) {
        this.postService = postService;
    }

    public ChangeListener<Post> postTableSelectHandler(){
        return new ChangeListener<Post>() {
            @Override
            public void changed(ObservableValue<? extends Post> observable, Post oldValue, Post newValue) {

                if(newValue == null){
                    System.out.println("null");
                    return;
                }

                postView.idText.setText(newValue.getID().toString());
                postView.nameText.setText((newValue.getName()));
                postView.tipText.setText(newValue.getTip());
            }

        };
    }

    public EventHandler<ActionEvent> addBtnPressedHandler(){
        return new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Integer ID;
                String name, tip;
                try {
                    ID = Integer.parseInt(postView.idText.getText());
                    name = postView.nameText.getText();
                    tip = postView.tipText.getText();
                    postService.addPost(new Post(ID, name, tip));

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
        };
    }

    private void clearAllTextfields(){
        postView.idText.setText("");
        postView.nameText.setText("");
        postView.tipText.setText("");
    }

    public EventHandler<ActionEvent> removeBtnPressedHandler(){
        return new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Post selectedPost = postView.postTable.getSelectionModel().getSelectedItem();
                if(selectedPost == null){
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Eroare:\n" + "Niciun post selectat, nu se poate realiza stergerea!", ButtonType.OK);
                    alert.show();
                    return;
                }
                try{
                    postService.removePost(selectedPost.getID());
                    clearAllTextfields();
                } catch (RepositoryException e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Eroare la stergere:\n" + e.getMessage(), ButtonType.OK);
                    alert.show();
                }
            }
        };
    }

    public EventHandler<ActionEvent> updateBtnPressedHandler(){
        return new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Integer ID;
                String name, tip;
                try {
                    ID = Integer.parseInt(postView.idText.getText());
                    name = postView.nameText.getText();
                    tip = postView.tipText.getText();
                    postService.updatePost(new Post(ID, name, tip));

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
        };
    }

    public EventHandler<ActionEvent> filterTextHandler() {
        return new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                postService.setFilter( postView.filterText.getText());
            }
        };
    }
}
