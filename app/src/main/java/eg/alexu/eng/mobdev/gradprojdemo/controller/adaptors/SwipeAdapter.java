package eg.alexu.eng.mobdev.gradprojdemo.controller.adaptors;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import eg.alexu.eng.mobdev.gradprojdemo.model.Story;
import eg.alexu.eng.mobdev.gradprojdemo.view.PageFragment;

/**
 * Created by Paula B. Bassily on 23/04/2018.
 */
public class SwipeAdapter extends FragmentStatePagerAdapter {
    Story story ;
    public SwipeAdapter(FragmentManager fm,Story story) {
        super(fm);
        this.story = story ;
    }


    @Override
    public Fragment getItem(int position) {
        Fragment fragment =  new PageFragment(story);
        Bundle bundle = new Bundle();
        bundle.putInt("index",position);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getCount() {
        return story.getScenes().size();
    }
}
