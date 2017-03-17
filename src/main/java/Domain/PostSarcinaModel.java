package Domain;

import Services.PostSarcinaService;
import Services.SarcinaService;
import Utils.Observer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Utilizator on 06-Dec-16.
 */
public class PostSarcinaModel implements Observer {
    private SarcinaService sarcinaService;
    private PostSarcinaService postSarcinaService;
    private ObservableList<Sarcina> currentSarcinaList;
    private Post currentPost;

    public PostSarcinaModel(SarcinaService sarcinaService, PostSarcinaService postSarcinaService) {
        this.sarcinaService = sarcinaService;
        this.postSarcinaService = postSarcinaService;


        this.postSarcinaService.addObserver(this);
        currentPost = null;
        currentSarcinaList = FXCollections.observableList(new ArrayList<Sarcina>());
    }


    public void setCurrentPost(Post post){
        currentPost = post;
        update();
    }

    public ObservableList<Sarcina> getPostSarcinaModel(){
        return currentSarcinaList;
    }

    @Override
    public void update() {
        if(currentPost != null){
            List<Sarcina> sarcinaList = sarcinaService.getAll();
            List<Sarcina> list = new ArrayList<>();
            //postSarcinaService.getAll().stream().filter(o-> o.getPostId() == currentPost.getID()).forEach(o->  o.getSarcinaId());

            for (Sarcina sarcina:sarcinaList) {
                if(  postSarcinaService.getAll().stream().filter(o-> o.getPostId() == currentPost.getID()).anyMatch(o-> o.getSarcinaId() == sarcina.getID())  )
                    list.add(sarcina);
            }
            currentSarcinaList.setAll(list);
        }
    }
}
