package eg.alexu.eng.mobdev.gradprojdemo.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.Serializable;
import java.util.List;

import eg.alexu.eng.mobdev.gradprojdemo.view.MainActivity;

/**
 * Created by Paula B. Bassily on 23/01/2018.
 */
public class Scene implements Serializable {

    private List<Entity> entities ;
    private String narration ;
    private Integer id ;
    private Integer storyId;
    private Bitmap cover ;

    public Scene(List<Entity> entities, String narration, Integer id, Integer storyId, Bitmap cover) {
        this.entities = entities;
        this.narration = narration;
        this.id = id;
        this.storyId = storyId;
        this.cover = cover;

        if(cover == null)
            loadInitialCover();
    }

    private void loadInitialCover() {
        int coverId = MainActivity.appContext.getResources().getIdentifier("bacground2",
                "drawable",MainActivity.appContext.getPackageName());
        cover = BitmapFactory.decodeResource(MainActivity.appContext.getResources(), coverId);
    }

    // private String name;

    public Scene (){
    }

    public Scene (Bitmap cover){
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

    public Bitmap getCover() {
        return cover;
    }

    public void setCover(Bitmap cover) {
        this.cover = cover;
    }



}
