package eg.alexu.eng.mobdev.gradprojdemo.controller.factories;

/**
 * Created by shereen on 4/24/2018.
 */
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.provider.ContactsContract;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import eg.alexu.eng.mobdev.gradprojdemo.model.Entity;
import eg.alexu.eng.mobdev.gradprojdemo.view.DisplayModeActivity;
import eg.alexu.eng.mobdev.gradprojdemo.view.MainActivity;

/**
 * Created by Paula B. Bassily on 23/04/2018.
 */
public class EntityFactory {
    private static Activity activityInst = MainActivity.appCompatActivity;

    public static List<Entity> createEntites (){
        List entities = new ArrayList<Entity>();
        Entity entity  ;
        entity= new Entity(null, "elephant","elephant",getBitMap("elephant"),(float)0,(float)0, (float)0 , (float)0,(float)0);

        entities.add(entity);

        entity= new Entity(null , "giraffe","giraffe", getBitMap("giraffe"), (float) 300,(float) 300 , (float)0 , (float)0,(float)0);
        entities.add(entity);

        return entities;
    }

    private static Bitmap getBitMap(String desc){
        int imageID = activityInst.getResources().getIdentifier(desc,
                "drawable",activityInst.getPackageName());
        ImageView image = new ImageView(activityInst.getApplicationContext());
        Bitmap bitmap = BitmapFactory.decodeResource(activityInst.getResources(), imageID);
        return bitmap ;
    }

    public static Entity createNewEntity (String desc){
        return new Entity(null , null,desc, getBitMap(desc), (float)0,(float) 0 , (float)0 , (float)1,(float)1);
    }

}
