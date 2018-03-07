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
import android.view.View;
import android.widget.Toast;

import java.util.List;

import eg.alexu.eng.mobdev.gradprojdemo.R;
import eg.alexu.eng.mobdev.gradprojdemo.controller.adaptors.BookShelfAdaptor;
import eg.alexu.eng.mobdev.gradprojdemo.controller.factories.StoryFactory;
import eg.alexu.eng.mobdev.gradprojdemo.model.Story;

public class Book_Shelf_Activity extends AppCompatActivity {

    private RecyclerView bookShelfRV ;
    private List<Story> stories ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book__shelf_);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        stories = StoryFactory.createRandomStories() ;

        setupBookShelf();

        setBookShelfContent(stories);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               Snackbar.make(view, "lesa shwaya", Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();
            }
        });
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

        Toast.makeText(this,"you clicked on "+stories.get(index).getStroyName(),Toast.LENGTH_LONG)
                .show();

        Intent myintent = new Intent(this,SceneEngine.class);
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
                        Toast.makeText(getBaseContext(),"you played "+stories.get(index).getStroyName()
                                ,Toast.LENGTH_LONG).show();
                        Intent myintent = new Intent(getBaseContext(),DisplayModeActivity.class);
                        startActivity(myintent);
                        break;
                    case R.id.story_menu_del:
                        Toast.makeText(getBaseContext(),"you deleted "+stories.get(index).getStroyName()
                                ,Toast.LENGTH_LONG).show();
                        break;
                    case R.id.story_menu_other:
                        Toast.makeText(getBaseContext(),"you opaaaaa "+stories.get(index).getStroyName()
                                ,Toast.LENGTH_LONG).show();
                        break;
                }
                return false;
            }
        });
        //displaying the popup
        popup.show();
    }
}
