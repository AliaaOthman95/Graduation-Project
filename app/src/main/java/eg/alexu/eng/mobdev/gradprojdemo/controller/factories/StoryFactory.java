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

    public static List<Story> createRandomStories() {

        List<Story> stories = new ArrayList<Story>();
        Story story ;
        Random random = new Random();
        for(int i = 0 ; i < 10 ; i++){
            String coverImagePath = "sample_cover_"+random.nextInt(6) ;
            story = new Story("story #"+i,new Date(),coverImagePath);
            Log.d("storyName",story.getStoryName());
            stories.add(story);
        }

        return stories;

    }

    public static Story createStory(String name_of_story) {
        Story story;
        Random random = new Random();
        String coverImagePath = "sample_cover_" + random.nextInt(6);
        story = new Story(name_of_story, new Date(), coverImagePath);
        return story;
    }
}
