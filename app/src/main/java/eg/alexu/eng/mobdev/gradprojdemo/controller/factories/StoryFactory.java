package eg.alexu.eng.mobdev.gradprojdemo.controller.factories;

import android.util.Log;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import eg.alexu.eng.mobdev.gradprojdemo.model.Story;

/**
 * Created by Paula B. Bassily on 05/02/2018.
 */
public class StoryFactory {

    public static List<Story> createRandomStories(int number) {

        List<Story> stories = new ArrayList<Story>();
        Story story ;
        Random random = new Random();
        for(int i = 0 ; i < number ; i++){
            String coverImagePath = "sample_cover_"+random.nextInt(6) ;
            story = new Story("story #"+i,new Date(),coverImagePath);
            Log.d("storyName",story.getStoryName());
            stories.add(story);
        }

        return stories;

    }
}
