package eg.alexu.eng.mobdev.gradprojdemo.view;

import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Date;

import eg.alexu.eng.mobdev.gradprojdemo.R;
import eg.alexu.eng.mobdev.gradprojdemo.controller.adaptors.SwipeAdapter;
import eg.alexu.eng.mobdev.gradprojdemo.controller.factories.EntityFactory;
import eg.alexu.eng.mobdev.gradprojdemo.controller.factories.SceneFactory;
import eg.alexu.eng.mobdev.gradprojdemo.model.Scene;
import eg.alexu.eng.mobdev.gradprojdemo.model.Story;

public class PagerMainActivity extends FragmentActivity {

    private ViewPager storyViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pager_main);
        Story staticStory =  new Story(null, null , null , null, null,SceneFactory.createScenes(this));
        storyViewPager = (ViewPager)findViewById(R.id.story_view_pager);
        SwipeAdapter swipeAdapter =  new SwipeAdapter(getSupportFragmentManager(),staticStory);
        storyViewPager.setAdapter(swipeAdapter);
    }
}
