package eg.alexu.eng.mobdev.gradprojdemo.controller;

import android.util.Log;

import java.util.List;

import eg.alexu.eng.mobdev.gradprojdemo.model.*;
import eg.alexu.eng.mobdev.gradprojdemo.view.MainActivity;

/**
 * Created by shereen on 2/3/2018.
 */

public class Engine {

    private static Engine instance = null ;
    private static SQLiteDBHelper db = null;
    public static Engine getInstance (){

        if(instance == null){
            db = new SQLiteDBHelper(MainActivity.appContext);
            return new Engine();
        }
        return instance;
    }


    public List<Story> loadStroies(){
        List<Story> res= db.getAllStories();;
        for(int i = 0; i < res.size(); i++){
            Log.d("story", "h" + res.get(i).getScenes().size());
        }
        return res;
    }

    public void saveStroies(Story story){

        if(story.getStoryId() != null){
            Log.d("story info" , "story "+story.getStoryName()+" will be updated");
            db.updateStory(story);
        }else{
            Log.d("story info" , "story "+story.getStoryName()+" will be added to DB");
            db.addStory(story);
        }

    }
    public Integer getLastStoryId(){

        return db.getLastStoryId();

    }
    public Integer getLastSceneId(){

        return db.getLastSceneId();

    }
    public Integer getLastEntityId(){

        return db.getLastEntityId();

    }

    public  boolean getStoryByName(String name) {
        return db.getStoryByName(name);
    }
    public void  deleteStory(String storyName){
        Log.d("story info" , "story "+storyName+" will be deleted from db");
        db.deleteStory(storyName);
    }
    public void  deleteScene(Integer id ,Integer sceneId){
        Log.d("scene info" , "scene "+sceneId+" will be updated");
        db.deleteScene(id,sceneId);
    }
    public void  deleteEntity(Integer entityId){
        db.deleteEntity(entityId);
    }


}
