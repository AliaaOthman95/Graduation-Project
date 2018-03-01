package eg.alexu.eng.mobdev.gradprojdemo.controller;

import java.util.ArrayList;
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

        return db.getAllStories();
    }

    public void saveStroies(Story story){

        if(db.getStory(story.getStoryId()) != null){
            db.updateStory(story);
        }else{
            db.addStory(story);
        }

    }


}
