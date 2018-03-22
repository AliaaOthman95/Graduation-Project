package eg.alexu.eng.mobdev.gradprojdemo.model;

import java.util.List;

/**
 * Created by Paula B. Bassily on 23/01/2018.
 */
public class Scene {

    private List<Entity> entities ;
    private String narration ;
    private Integer id ;
    private Integer storyId;
    private String cover ;

    public Scene(List<Entity> entities, String narration, Integer id, Integer storyId, String cover) {
        this.entities = entities;
        this.narration = narration;
        this.id = id;
        this.storyId = storyId;
        this.cover = cover;
    }
    // private String name;

    public Scene (){
    }

    public Scene (String cover){
        //this.name=name;
        this.cover=cover;
    }

    public List<Entity> getEntities() {
        return entities;
    }

    public void setEntities(List<Entity> entities) {
        this.entities = entities;
    }

    public String getNarration() {
        return narration;
    }

    public void setNarration(String narration) {
        this.narration = narration;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStoryId() {
        return storyId;
    }

    public void setStoryId(Integer storyId) {
        this.storyId = storyId;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }



}
