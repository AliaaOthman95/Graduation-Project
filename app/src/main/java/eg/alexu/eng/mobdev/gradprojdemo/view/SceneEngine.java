package eg.alexu.eng.mobdev.gradprojdemo.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.List;

import eg.alexu.eng.mobdev.gradprojdemo.R;
import eg.alexu.eng.mobdev.gradprojdemo.controller.SceneCreator;
import eg.alexu.eng.mobdev.gradprojdemo.controller.adaptors.SceneAdapter;
import eg.alexu.eng.mobdev.gradprojdemo.controller.factories.SceneFactory;
import eg.alexu.eng.mobdev.gradprojdemo.model.Scene;

public class SceneEngine extends AppCompatActivity {

    private RecyclerView recyclerView;
    private SceneAdapter adapter;
    SceneFactory sceneFactory ;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scene_engine);

        setRecyclerView();

        // to make new scene
        ImageButton newScene= (ImageButton)findViewById(R.id.newScene);
        newScene.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent myintent = new Intent(getBaseContext(), SceneCreator.class);
                startActivity(myintent);

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_my_scenes,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){

            case R.id.save_story :
                // save story
                return true;
            case R.id.search_scene:
                // search
                return true;
            case R.id.delete_scene:
                // delete
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    private void setRecyclerView(){
        recyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        // make grid layout
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 3);
        mLayoutManager.scrollToPosition(2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(3, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        // create scences and set them to adapter
        adapter = new SceneAdapter(sceneFactory.createScenes());
        recyclerView.setAdapter(adapter);
    }
    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

    /**
     *to handle position of grid layout in recycleView
     */
    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }

    }


}