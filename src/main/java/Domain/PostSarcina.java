package Domain;

/**
 * Created by Utilizator on 06-Dec-16.
 */
public class PostSarcina implements HasID<PostSarcina>{
    private Integer postId;
    private Integer sarcinaId;

    public PostSarcina(Integer postId, Integer sarcinaId) {
        this.postId = postId;
        this.sarcinaId = sarcinaId;
    }


    @Override
    public void setID(PostSarcina postSarcina) {

    }

    @Override
    public PostSarcina getID() {
        return this;
    }

    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    public Integer getSarcinaId() {
        return sarcinaId;
    }

    public void setSarcinaId(Integer sarcinaId) {
        this.sarcinaId = sarcinaId;

    }


    @Override
    public boolean equals(Object object){
        if( !(object instanceof PostSarcina))
            return false;
        return (this.getPostId() == ((PostSarcina) object).getPostId() && this.getSarcinaId() == ((PostSarcina) object).getSarcinaId() );
    }

}
