package eg.alexu.eng.mobdev.gradprojdemo.model;

import android.content.Intent;
import android.graphics.Point;
import android.graphics.Bitmap;
/**
 * Created by Paula B. Bassily on 23/01/2018.
 */
public class Entity {

    private Integer id;
    // bird, cow, or any general description
    private String classification ;
    // if this entity is somehow important in the story , it should have name e.g. Nemo
    private String name ;
    // what is this entity looks like, generated by GAN in real app
    private Bitmap image ;
    private float positionX ;
    private float positionY ;
    private float rotationAngle;
    private float scale ;

    public Entity(Integer id, String classification, String name, Bitmap image, float positionX, float positionY, float rotationAngle, float scale) {
        this.id = id;
        this.classification = classification;
        this.name = name;
        this.image = image;
        this.positionX = positionX;
        this.positionY = positionY;
        this.rotationAngle = rotationAngle;
        this.scale = scale;
    }



    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getId() {
        return id;
    }
    public String getClassification() {
        return classification;
    }

    public String getName() {
        return name;
    }

    public Bitmap getImage() {
        return image;
    }

    public float getPositionX() {
        return positionX;
    }

    public float getPositionY() {
        return positionY;
    }

    public float getRotationAngle() {
        return rotationAngle;
    }

    public float getScale() {
        return scale;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public void setPositionX(float positionX) {
        this.positionX = positionX;
    }

    public void setPositionY(float positionY) {
        this.positionY = positionY;
    }

    public void setRotationAngle(float rotationAngle) {
        this.rotationAngle = rotationAngle;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }
}
