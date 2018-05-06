package eg.alexu.eng.mobdev.gradprojdemo.controller.factories;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

import eg.alexu.eng.mobdev.gradprojdemo.model.Entity;
import eg.alexu.eng.mobdev.gradprojdemo.model.Scene;

/**
 * Created by shereen on 2/7/2018.
 */

public class SceneFactory {

    public static List<Scene> createSampleScenes() {
        List<Scene> sceneList = new ArrayList<>();
        Scene scene1 = new Scene(EntityFactory.createEntites(),"beautiful girafe & elephant",null,null, null);
        Scene scene2 = new Scene(EntityFactory.createEntites() ,"and they always still beautiful girafe & elephant",null,null, null);
        sceneList.add(scene1);
        sceneList.add(scene2);
        return sceneList;
    }

    public static Scene createBlackScene(){
        return new Scene(null , "" , null , null ,null) ;
    }




}
