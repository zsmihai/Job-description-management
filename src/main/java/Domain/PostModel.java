package Domain;

import Services.PostService;
import Utils.Observer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Created by Utilizator on 27-Nov-16.
 */
public class PostModel implements Observer{
    private ObservableList<Post> modelList;
    private PostService postService;

    public PostModel(PostService postService){
        this.postService = postService;
        this.postService.addObserver(this);
        modelList = FXCollections.observableList( postService.getAll());
    }

    @Override
    public void update() {
        modelList.setAll( FXCollections.observableList(postService.getAll()));
    }

    public ObservableList<Post> getModelList(){
        return modelList;
    }
}
