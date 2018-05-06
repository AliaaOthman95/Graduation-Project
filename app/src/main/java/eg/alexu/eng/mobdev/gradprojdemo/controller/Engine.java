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

        if(db.getStory(story.getStoryId()) != null){
            Log.d("updateStory" , "hiiiiiiiiiiii");
            db.updateStory(story);
        }else{
            Log.d("addStory" , "byeeeeeeeeeee");
            db.addStory(story);
        }

    }
    public int getLastStoryId(){

       return db.getLastStoryId();

    }
    public int getLastSceneId(){

        return db.getLastSceneId();

    }
    public int getLastEntityId(){

        return db.getLastEntityId();

    }


}
