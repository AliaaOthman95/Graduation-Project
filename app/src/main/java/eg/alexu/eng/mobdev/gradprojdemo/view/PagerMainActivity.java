package eg.alexu.eng.mobdev.gradprojdemo.view;

import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import eg.alexu.eng.mobdev.gradprojdemo.R;
import eg.alexu.eng.mobdev.gradprojdemo.controller.adaptors.SwipeAdapter;
import eg.alexu.eng.mobdev.gradprojdemo.model.Story;

public class PagerMainActivity extends FragmentActivity {

    private ViewPager storyViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pager_main);
        int storyIndex = (int) getIntent().getSerializableExtra("story_index");
        Story story = Book_Shelf_Activity.stories.get(storyIndex);
        storyViewPager = (ViewPager)findViewById(R.id.story_view_pager);
        SwipeAdapter swipeAdapter =  new SwipeAdapter(getSupportFragmentManager(),story);
        storyViewPager.setAdapter(swipeAdapter);
    }
}
