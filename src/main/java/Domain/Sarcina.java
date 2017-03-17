package Domain;

import java.io.Serializable;

public class Sarcina implements HasID<Integer>, Serializable{

    private int id;
    private String description;

    public Sarcina(int id) {
        this.id = id;
        description = "";
    }

    public Sarcina(int id, String description) {
        this.id = id;
        this.description = description;
    }



    public String getDescription() {
        return description;
    }



    public void setDescription(String nDescription) {
        description = nDescription;
    }

    public String toString() {
        return id + " " + description;
    }

    @Override
    public boolean equals(Object other) {

        if(!(other instanceof Sarcina)) {
            return false;
        }

        Sarcina oth = (Sarcina) other;
        return this.getID() == oth.getID();
    }

    @Override
    public void setID(Integer nID) {
        id = nID;
    }

    @Override
    public Integer getID() {
        return id;
    }
}


