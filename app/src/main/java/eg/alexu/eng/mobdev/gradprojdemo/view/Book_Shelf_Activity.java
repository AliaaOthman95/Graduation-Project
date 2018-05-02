package eg.alexu.eng.mobdev.gradprojdemo.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Display;
import android.view.MenuItem;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import eg.alexu.eng.mobdev.gradprojdemo.R;
import eg.alexu.eng.mobdev.gradprojdemo.controller.Engine;
import eg.alexu.eng.mobdev.gradprojdemo.controller.adaptors.BookShelfAdaptor;
import eg.alexu.eng.mobdev.gradprojdemo.controller.factories.StoryFactory;
import eg.alexu.eng.mobdev.gradprojdemo.model.Story;

public class Book_Shelf_Activity extends AppCompatActivity {

    private RecyclerView bookShelfRV ;
    public static List<Story> stories ;
    private Engine engine;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book__shelf_);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
      
        engine = Engine.getInstance();

        loadStories();

        setupBookShelf();
      
        setBookShelfContent(stories);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Story  story = StoryFactory.createRandomStories().get(0);
                stories.add(story);
                setBookShelfContent(stories);
                Log.d("storyIDbeforeSave","eih el kalam");
                engine.saveStroies(story);
                Log.d("storyIDAfterSave",engine.getLastStoryId()+" is the last id");
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
        LinearLayoutManager llm = new LinearLayoutManager(this);
        bookShelfRV.setLayoutManager(llm);

    }


    public  void onClickBook(int index) {

        Toast.makeText(this,"you clicked on "+stories.get(index).getStoryName(),Toast.LENGTH_LONG)
                .show();


        //stories.get(index);
        Intent myintent = new Intent(this,SceneEngine.class);
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
                        break;
                    case R.id.story_menu_other:
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
