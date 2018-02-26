package eg.alexu.eng.mobdev.gradprojdemo.model;

import java.util.List;

/**
 * Created by Paula B. Bassily on 23/01/2018.
 */
public class Scene {

    private List<Entity> entities ;
    private String narration ;
    private String cover ;
    private String name;

    public Scene (String name ,String cover){
        this.name=name;
        this.cover=cover;
    }

    public List<Entity> getlist() {
        return entities;
    }

    public void setList(List<Entity> entities) {
        this.entities = entities;
    }

    public String getNarration() {
        return narration;
    }

    public void setNarration(String narration) {
        this.narration = narration;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getname() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
