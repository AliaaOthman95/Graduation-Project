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

    public static List<Scene> createScenes(Activity inst) {
        List<Scene> sceneList = new ArrayList<>();
        Scene scene1 = new Scene(EntityFactory.createEntites(inst),"beautiful girafe & elephant",null,null, "scene_pic_1");
        Scene scene2 = new Scene(EntityFactory.createEntites(inst) ,"and they always still beautiful girafe & elephant",null,null, "scene_pic_2");
        sceneList.add(scene1);
        sceneList.add(scene2);
        return sceneList;
    }




}
