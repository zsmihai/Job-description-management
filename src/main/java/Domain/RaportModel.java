package Domain;

import Services.PostSarcinaService;
import Services.PostService;
import Services.SarcinaService;
import Utils.Observable;
import Utils.Observer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Created by Utilizator on 09-Jan-17.
 */
public class RaportModel implements Observer{

    private PostService postService;
    private SarcinaService sarcinaService;
    private PostSarcinaService postSarcinaService;

    private ObservableList<Post> postList;
    private ObservableList<Sarcina> sarcinaList;

    private Comparator<Post> postComparator;
    private Comparator<Sarcina> sarcinaComparator;
    private Predicate<Post> postFilter;
    private Predicate<Sarcina> sarcinaFilter;

    private State state;
    private Direction postDirection, sarcinaDirection;
    private HowMany postHowMany, sarcinaHowMany;
    private Double postNumber, sarcinaNumber;


    public ObservableList<Post> getPostList(){
        return postList;
    }
    public ObservableList<Sarcina> getSarcinaList(){
        return sarcinaList;
    }



    public RaportModel(PostService postService, SarcinaService sarcinaService, PostSarcinaService postSarcinaService) {
        this.postService = postService;
        this.sarcinaService = sarcinaService;
        this.postSarcinaService = postSarcinaService;

        postList = FXCollections.observableList(new ArrayList<Post>());
        sarcinaList = FXCollections.observableList(new ArrayList<>());

    }


    public void setSarcinaListFromPost( Post post  ){

        if(post == null)return;
        List<Sarcina> allSarcina = sarcinaService.getAll();
        List<Sarcina> list = new ArrayList<Sarcina>();
        List<PostSarcina> postSarcinaList = postSarcinaService.getAll();
        for (Sarcina sarcina:allSarcina) {
            if (postSarcinaList.stream().filter(o -> o.getPostId().equals(post.getID())).anyMatch(o -> o.getSarcinaId().equals(sarcina.getID())))             ;
            list.add(sarcina);
        }
            sarcinaList.setAll(list);
    }




    public void setPostListFromSarcina( Sarcina sarcina  ){

        if(sarcina == null)return;
        List<Post> allPost = postService.getAll();
        List<Post> list = new ArrayList<Post>();
        List<PostSarcina> postSarcinaList = postSarcinaService.getAll();
        for (Post post:allPost) {
            if (postSarcinaList.stream().filter(o -> o.getSarcinaId() .equals( sarcina.getID())).anyMatch(o -> o.getPostId() .equals( post.getID())))
                list.add(post);
        }
        postList.setAll(list);
    }

    public void selectFirstPostsNumber(Integer number){
        List<Post> list = postList.subList(0, number-1);
        postList.setAll(list);
    }

    public void selectFirstSarciniNumber(Integer number){

        List<Sarcina> list = sarcinaList.subList(0, number-1);
        sarcinaList.setAll(list);
    }

    public void selectLastPostsNumber(Integer number){
        List<Post> list = (postList.subList( Math.max(0, postList.size()-number),postList.size()-1 ));
        postList.setAll(list);
    }

    public void selectLastSarciniNumber(Integer number){
        sarcinaList.setAll(sarcinaList.subList( Math.max(0, sarcinaList.size()-number),sarcinaList.size()-1 ));
    }

    public void selectFirstPostsPercentage(Double percentage){
        postList.setAll(postList.subList(0, ((int) (percentage * postList.size()))-1));
    }

    public void selectFirstSarciniPercentage(Double percentage){
        sarcinaList.setAll(sarcinaList.subList(0, ((int) (percentage * postList.size()))-1  ));
    }

    public void selectLastPostsPercentage(Double percentage){
        postList.setAll(postList.subList(  Math.max( 0,  postList.size() -  (int) (percentage * postList.size())-1)   ,      postList.size()-1 ));
    }

    public void selectLastSarciniPercentage(Double percentage){
        sarcinaList.setAll(sarcinaList.subList(  Math.max( 0,  sarcinaList.size() -  (int) (percentage * sarcinaList.size())-1)   ,      sarcinaList.size()-1 ));
    }


    public void filterSarcina(Predicate<Sarcina> filter){
        sarcinaList.setAll( sarcinaList.stream().filter(filter).collect(Collectors.toList()));
    }

    public void filterPost(Predicate<Post> filter){
        postList.setAll( postList.stream().filter(filter).collect(Collectors.toList()));
    }

    public void sortSarcina(Comparator<Sarcina> comparator){
        sarcinaList.setAll(sarcinaList.stream().sorted(comparator).collect(Collectors.toList()));
    }

    public void sortPost(Comparator<Post> comparator){
        postList.setAll(postList.stream().sorted(comparator).collect(Collectors.toList()));
    }

    public void resetSarcinaList(){
        sarcinaList.setAll(sarcinaService.getAll());
    }
    public void resetPostList(){
        postList.setAll(postService.getAll());
    }


    public Integer countPostsPerSarcina(Sarcina sarcina){
        List<Post> allPost = postService.getAll();
        List<Post> list = new ArrayList<Post>();
        List<PostSarcina> postSarcinaList = postSarcinaService.getAll();
        for (Post post:allPost) {
            if (postSarcinaList.stream().filter(o -> o.getSarcinaId() .equals( sarcina.getID())).anyMatch(o -> o.getPostId() .equals( post.getID())))
                list.add(post);
        }
        return list.size();
    }

    public Integer countSarcinaPerPost(Post post){
        List<Sarcina> allSarcina = sarcinaService.getAll();
        List<Sarcina> list = new ArrayList<Sarcina>();
        List<PostSarcina> postSarcinaList = postSarcinaService.getAll();
        return (int)allSarcina.stream().filter(sarcina -> postSarcinaList.stream().filter(o -> o.getPostId().equals( post.getID())).anyMatch(o -> o.getSarcinaId().equals( sarcina.getID()))).count();
    }

    @Override
    public void update() {

        if(state == State.Post){
            resetPostList();
            sarcinaList.setAll(new ArrayList<Sarcina>());
            if(postFilter !=null)
                filterPost(postFilter);
            if(postComparator!=null)
                sortPost(postComparator);

            if( postDirection == Direction.TOP ){

                if(postHowMany == HowMany.COUNT){
                    selectFirstPostsNumber( postNumber.intValue());
                }
                else{
                    selectFirstPostsPercentage(postNumber);
                }

            }
            if(postDirection == Direction.BOTTOM){
                if(postHowMany == HowMany.COUNT){
                    selectLastPostsNumber( postNumber.intValue());
                }
                else{
                    selectLastPostsPercentage(postNumber);
                }
            }
        }
        if(state == State.Sarcina){
            resetSarcinaList();
            postList.setAll(new ArrayList<Post>());
            if(sarcinaFilter !=null)
                filterSarcina(sarcinaFilter);
            if(sarcinaComparator!=null)
                sortSarcina(sarcinaComparator);

            if( sarcinaDirection == Direction.TOP ){

                if(sarcinaHowMany == HowMany.COUNT){
                    selectFirstSarciniNumber( sarcinaNumber.intValue());
                }
                else{
                    selectFirstSarciniPercentage(sarcinaNumber);
                }

            }
            if(sarcinaDirection == Direction.BOTTOM){
                if(sarcinaHowMany == HowMany.COUNT){
                    selectLastSarciniNumber( sarcinaNumber.intValue());
                }
                else{
                    selectLastSarciniPercentage(sarcinaNumber);
                }
            }
        }
    }

    public void setPostComparator(Comparator<Post> postComparator) {
        this.postComparator = postComparator;
    }

    public void setSarcinaComparator(Comparator<Sarcina> sarcinaComparator) {
        this.sarcinaComparator = sarcinaComparator;
    }

    public void setPostFilter(Predicate<Post> postFilter) {
        this.postFilter = postFilter;
    }

    public void setSarcinaFilter(Predicate<Sarcina> sarcinaFilter) {
        this.sarcinaFilter = sarcinaFilter;
    }

    public void setState(State state) {
        this.state = state;
    }

    public void setPostDirection(Direction postDirection) {
        this.postDirection = postDirection;
    }

    public void setSarcinaDirection(Direction sarcinaDirection) {
        this.sarcinaDirection = sarcinaDirection;
    }

    public void setPostHowMany(HowMany postHowMany) {
        this.postHowMany = postHowMany;
    }

    public void setSarcinaHowMany(HowMany sarcinaHowMany) {
        this.sarcinaHowMany = sarcinaHowMany;
    }

    public void setPostNumber(Double postNumber) {
        this.postNumber = postNumber;
    }

    public void setSarcinaNumber(Double sarcinaNumber) {
        this.sarcinaNumber = sarcinaNumber;
    }
}
