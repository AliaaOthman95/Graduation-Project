package eg.alexu.eng.mobdev.gradprojdemo.model;

import android.graphics.Color;
import android.media.Image;

import java.util.Date;
import java.util.List;

/**
 * Created by Paula B. Bassily on 23/01/2018.
 */
public class Story {

    private String stroyName;
    private String cover ;
    private Color coverColor;
    private Date creationDate ;
    private List<Scene> scenes;

    public Story(String stroyName, Date creationDate) {
        this.stroyName = stroyName;
        this.creationDate = creationDate;
    }

    public Story(String stroyName, Date creationDate, String cover) {
        this.stroyName = stroyName;
        this.creationDate = creationDate;
        this.cover = cover;
    }

    public String getStroyName() {
        return stroyName;
    }

    public void setStroyName(String stroyName) {
        this.stroyName = stroyName;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public Color getCoverColor() {
        return coverColor;
    }

    public void setCoverColor(Color coverColor) {
        this.coverColor = coverColor;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public List<Scene> getScenes() {
        return scenes;
    }

    public void setScenes(List<Scene> scenes) {
        this.scenes = scenes;
    }

}
