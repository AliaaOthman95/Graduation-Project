package eg.alexu.eng.mobdev.gradprojdemo.view;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import eg.alexu.eng.mobdev.gradprojdemo.R;
import eg.alexu.eng.mobdev.gradprojdemo.controller.Engine;
import eg.alexu.eng.mobdev.gradprojdemo.controller.adaptors.BookShelfAdaptor;
import eg.alexu.eng.mobdev.gradprojdemo.controller.factories.StoryFactory;
import eg.alexu.eng.mobdev.gradprojdemo.model.Story;

public class Book_Shelf_Activity extends AppCompatActivity {

    private RecyclerView bookShelfRV ;
    public static List<Story> stories ;
    private AlertDialog dialog;
    private  String name_of_story ;
    private Engine engine;
    private Story story;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book__shelf_);
        /*Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);*/
      
        engine = Engine.getInstance();

        loadStories();

        setupBookShelf();
      
        setBookShelfContent(stories);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 dialogPopUp();
                 Log.d("***********************","****************");
                 story = StoryFactory.createRandomStories().get(0);
                 Log.d("storyIDbeforeSave", "eih el kalam");
                 engine.saveStroies(story);
                 Log.d("storyIDAfterSave", engine.getLastStoryId() + " is the last id");
                 story.setStoryId(engine.getLastStoryId());


            }
        });
    }

    private void loadStories() {
        stories = engine.loadStroies();
        if(stories==null)
            stories = new ArrayList<Story>();
        Log.d("loaded stories",stories.size()+"");
    }

    private void dialogPopUp() {

        AlertDialog.Builder mbuilder = new AlertDialog.Builder(Book_Shelf_Activity.this);
        View mview = getLayoutInflater().inflate(R.layout.dialog,null);

        // fileds of dialog
        final EditText story_name = (EditText) mview.findViewById(R.id.text);
        final TextView  text = (TextView) mview.findViewById(R.id.textView);
        text.setText("Enter name of your story");
        Button ok_button =(Button) mview.findViewById(R.id.ok);


        ok_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // get entity from server
                if(!story_name.getText().toString().isEmpty()) {
                    name_of_story = story_name.getText().toString();
                    if(engine.getStoryByName(name_of_story)){
                        Log.d("******************","//////////////////////");
                        story_name.setError("Please enter another name");
                    }else {
                        //if(found) {
                            story = StoryFactory.createRandomStories().get(0);
                            story.setStoryName(name_of_story);
                            stories.add(story);
                            setBookShelfContent(stories);
                            ((LinearLayoutManager) bookShelfRV.getLayoutManager()).scrollToPositionWithOffset(stories.size() - 1, 0);
                           // Log.d("//////////////////////", name_of_story);

                        dialog.dismiss();
                    }
                }else {
                    story_name.setError("Please enter name of your story");
                }
            }

        });

        ImageButton speech =(ImageButton) mview.findViewById(R.id.speech);
        speech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.ENGLISH);
                try{
                    startActivityForResult(intent,200);
                }catch (ActivityNotFoundException a){
                    Toast.makeText(getApplicationContext(),"Speech Intent problem",Toast.LENGTH_SHORT).show();
                }
            }
        });

        Button cancel = (Button) mview.findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        mbuilder.setView(mview);
        dialog = mbuilder.create();
        dialog.show();
        return;
    }

    private boolean findStorybyName(String name){
        for(Story s :stories){
            if(s !=null) {
                if (name.toLowerCase().equals(s.getStoryName().toLowerCase())) {
                    return true;
                }
            }

        }
        return false;
    }

    private void setupEngine() {
        engine = Engine.getInstance();
    }

    private void setBookShelfContent(List<Story> stories) {

        BookShelfAdaptor adapter = new BookShelfAdaptor(stories,this);

        bookShelfRV.setAdapter(adapter);
    }

    private void setupBookShelf() {

        bookShelfRV = (RecyclerView)findViewById(R.id.book_shelf_rv);
        bookShelfRV.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(),2);
        gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        gridLayoutManager.scrollToPosition(2);
        //LinearLayoutManager llm = new LinearLayoutManager(this);
        bookShelfRV.setLayoutManager(gridLayoutManager);

    }


    public  void onClickBook(int index) {

        Toast.makeText(this,"you clicked on "+stories.get(index).getStoryName(),Toast.LENGTH_LONG)
                .show();


        //stories.get(index);
        Intent myintent = new Intent(this,SceneActivity.class);
        myintent.putExtra("Integer",index);
        startActivity(myintent);
    }

    public void onOptionsClick(final int index , View view) {

        //creating a popup menu
        PopupMenu popup = new PopupMenu(this, view);
        //inflating menu from xml resource
        popup.inflate(R.menu.card_pop_menu);
        //adding click listener
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.story_menu_play:
                        Toast.makeText(getBaseContext(),"you played "+stories.get(index).getStoryName()
                                ,Toast.LENGTH_LONG).show();
                        Intent myintent = new Intent(getBaseContext(),PagerMainActivity.class);
                        myintent.putExtra("story_index",index);
                        startActivity(myintent);
                        break;
                    case R.id.story_menu_del:
                        Toast.makeText(getBaseContext(),"you deleted "+stories.get(index).getStoryName()
                                ,Toast.LENGTH_LONG).show();
                        engine.deleteStory(index);
                        // update view *************************************************
                        stories.remove(index);
                        setBookShelfContent(stories);
                        break;
                    case R.id.story_menu_rename:
                        Toast.makeText(getBaseContext(),"you opaaaaa "+stories.get(index).getStoryName()
                                ,Toast.LENGTH_LONG).show();


                        break;
                }
                return false;
            }
        });
        //displaying the popup
        popup.show();
    }

  /*  @Override
    public void onDestroy() {
        Toast.makeText(getBaseContext(),"da5aaaaaaaaal "
                ,Toast.LENGTH_LONG).show();
        for(Story story : stories)
            engine.saveStroies(story);
        super.onDestroy();

    }*/
    @Override
    public void onStop() {
        Toast.makeText(getBaseContext(),"da5aaaaaaaaal stop"
                ,Toast.LENGTH_LONG).show();
        for(Story story : stories)
            engine.saveStroies(story);
        super.onStop();

    }
}
