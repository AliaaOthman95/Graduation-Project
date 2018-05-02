package eg.alexu.eng.mobdev.gradprojdemo.view;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import eg.alexu.eng.mobdev.gradprojdemo.R;
import eg.alexu.eng.mobdev.gradprojdemo.model.Entity;
import eg.alexu.eng.mobdev.gradprojdemo.model.Story;

/**
 * A simple {@link Fragment} subclass.
 */
public class PageFragment extends android.support.v4.app.Fragment {
    private TextView textView;
    private ViewGroup displayView ;
    private Story story ;
    private int sceneIndex ;

    public PageFragment(Story story) {
        this.story = story ;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_page_layout,container,false);

        textView = (TextView)view.findViewById(R.id.narration_text_view);
        displayView = view.findViewById(R.id.diplay_scene);

        Bundle bundle =  getArguments();
        sceneIndex = bundle.getInt("index") ;

        loadScene();

        return view ;
    }

    private void loadScene() {

        showNarrationText();
        showSceneEntities() ;
    }

    private void showSceneEntities() {

        List<Entity> entities = story.getScenes().get(sceneIndex).getEntities() ;
        if(entities != null ){
            for(Entity entity : entities){
                showEntity(entity);
            }
        }
    }

    private void showNarrationText() {

        String  message = story.getScenes().get(sceneIndex).getNarration();
        textView.setText(message);
    }

    private void showEntity(Entity entity){

        ImageView image = new ImageView(this.getContext());
        image.setX(entity.getPositionX());
        image.setY(entity.getPositionY());
        image.setRotation(entity.getRotationAngle());
        image.setScaleX(entity.getScaleX());
        image.setScaleY(entity.getScaleY());
        image.setImageBitmap(entity.getImage());
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(200,200);
        image.setLayoutParams(layoutParams);
        displayView.addView(image);


    }

}
