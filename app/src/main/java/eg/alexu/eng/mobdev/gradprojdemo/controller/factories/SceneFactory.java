package eg.alexu.eng.mobdev.gradprojdemo.controller.factories;

import java.util.ArrayList;
import java.util.List;

import eg.alexu.eng.mobdev.gradprojdemo.model.Scene;

/**
 * Created by shereen on 2/7/2018.
 */

public class SceneFactory {

    public static List<Scene> createScenes() {
        List<Scene> sceneList = new ArrayList<>();
        Scene scene1 = new Scene("1", "scene_pic_1");
        Scene scene2 = new Scene("2", "scene_pic_2");
        Scene scene3 = new Scene("3", "scene_pic_1");
        Scene scene4 = new Scene("4", "scene_pic_1");
        Scene scene5 = new Scene("5", "scene_pic_2");
        sceneList.add(scene1);
        sceneList.add(scene2);
        sceneList.add(scene3);
        sceneList.add(scene4);
        sceneList.add(scene5);
        sceneList.add(scene1);
        sceneList.add(scene2);
        sceneList.add(scene3);
        sceneList.add(scene4);

        return sceneList;
    }




}
