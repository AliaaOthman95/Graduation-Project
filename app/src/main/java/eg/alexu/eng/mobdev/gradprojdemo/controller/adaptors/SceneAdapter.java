package eg.alexu.eng.mobdev.gradprojdemo.controller.adaptors;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import eg.alexu.eng.mobdev.gradprojdemo.R;
import eg.alexu.eng.mobdev.gradprojdemo.controller.listener.ItemClickListener;
import eg.alexu.eng.mobdev.gradprojdemo.model.Scene;
import eg.alexu.eng.mobdev.gradprojdemo.view.SceneActivity;

/**
 * Created by shereen on 2/6/2018.
 */

public class SceneAdapter extends RecyclerView.Adapter<SceneAdapter.MySceneHolder>{

    List<Scene> scenesList ;
    private SceneActivity sceneEngineInstance;

    public SceneAdapter (List<Scene> scenesList , SceneActivity sceneEngineInstance){
        this.scenesList=scenesList;
        this.sceneEngineInstance = sceneEngineInstance ;
    }


    @Override
    public MySceneHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.scene_card,parent,false);
        return new MySceneHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MySceneHolder holder, final int position) {
        Scene scene = scenesList.get(position);
       // holder.sceneName.setText(scenesList.get(position).getname());
        final Context context = holder.sceneCover.getContext();

        Bitmap cover = scenesList.get(position).getCover();
        holder.sceneCover.setImageBitmap(cover);


        holder.sceneCover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sceneEngineInstance.onOptionsClick(position, holder.sceneCover);
            }
        });
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int pos) {
              
                Toast.makeText(context,"click",Toast.LENGTH_LONG).show();

            }
        });
    }

    @Override
    public int getItemCount() {
        if(scenesList == null) return 0 ;
        return scenesList.size();
    }

    public class MySceneHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
            CardView cardView;
            TextView sceneName;
            ImageView sceneCover;
            ItemClickListener itemClickListener;

        public MySceneHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.card_view);
            //sceneName = (TextView) itemView.findViewById(R.id.sceneName);
            sceneCover =(ImageView) itemView.findViewById(R.id.sceneCover);
            itemView.setOnClickListener(this);
        }

        public void setItemClickListener (ItemClickListener itemClickListener){
            this.itemClickListener=itemClickListener;
        }

        @Override
        public void onClick(View v) {
            itemClickListener.onClick(v,getAdapterPosition());
        }
    }

}
