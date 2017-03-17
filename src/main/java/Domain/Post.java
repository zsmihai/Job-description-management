
package Domain;

public class Post implements HasID<Integer> {

    private int id;
    private String name;
    private String tip;

    public Post(int id) {
        this.id = id;
        name = "";
    }

    public Post(int id, String name, String tip) {
        this.id = id;
        this.name = name;
        this.tip = tip;
    }


    public String getName() {
        return name;
    }


    public void setName(String nName) {
        name = nName;
    }

    public boolean equals(Post other) {
        return id == other.id;
    }

    public String toString() {
        return id + " " + name;
    }




    @Override
    public boolean equals(Object other) {

        if(!(other instanceof Post)) {
            return false;
        }
        Post oth = (Post) other;
        return oth.getID() == this.getID();
    }

    @Override
    public int hashCode(){
        return this.id;
    }


    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    @Override
    public void setID(Integer id) {
        this.id=id;
    }

    @Override
    public Integer getID() {
        return id;
    }
}
