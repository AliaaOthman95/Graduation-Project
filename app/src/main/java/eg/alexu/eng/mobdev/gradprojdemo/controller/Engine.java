package eg.alexu.eng.mobdev.gradprojdemo.controller;

import java.util.ArrayList;
import java.util.List;

import eg.alexu.eng.mobdev.gradprojdemo.model.Story;

/**
 * Created by shereen on 2/3/2018.
 */

public class Engine {

    private static Engine instance = null ;

    public Engine getInstance (){
        if(instance==null)
            return new Engine();
        return instance;
    }


    public List<Story> loadStroies(){

        return new ArrayList<Story>();
    }

    public void saveStroies(List<Story> stories){


    }


}
