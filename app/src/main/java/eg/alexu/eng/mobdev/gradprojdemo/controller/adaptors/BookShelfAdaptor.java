package eg.alexu.eng.mobdev.gradprojdemo.controller.adaptors;

import android.content.Context;
import android.content.res.Resources;
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
import eg.alexu.eng.mobdev.gradprojdemo.model.Story;

/**
 * Created by Paula B. Bassily on 05/02/2018.
 */

    public class BookShelfAdaptor extends RecyclerView.Adapter<BookShelfAdaptor.StroyCardViewHolder> {

        private List<Story> storyList ;



        public BookShelfAdaptor(List<Story> stories){
            this.storyList=stories;
        }




        @Override
        public StroyCardViewHolder onCreateViewHolder(ViewGroup parent, int i) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.story_crad_view, parent, false);
            StroyCardViewHolder stroyCardViewHolder = new StroyCardViewHolder(view);
            return stroyCardViewHolder;
        }

        @Override
        public void onBindViewHolder(StroyCardViewHolder holder, final int index) {

            holder.storyName.setText(storyList.get(index).getStroyName());

            holder.storyDate.setText(storyList.get(index).getCreationDate()+"");
            final Context context = holder.storyCover.getContext();
            String coverName = storyList.get(index).getCover();
            int coverImageId = context.getResources().getIdentifier(coverName,
                    "drawable", context.getPackageName());
            holder.storyCover.setImageResource(coverImageId);
            holder.setItemClickListener(new ItemClickListener() {
                @Override
                public void onClick(View view, int pos) {
                    Toast.makeText(context,"click"+ storyList.get(index),Toast.LENGTH_LONG).show();
                }
            });


        }

        @Override
        public int getItemCount() {
            return storyList.size();
        }

        @Override
        public void onAttachedToRecyclerView(RecyclerView recyclerView) {
            super.onAttachedToRecyclerView(recyclerView);
        }



    public static class StroyCardViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            CardView cv;
            TextView storyName;
            TextView storyDate;
            ImageView storyCover;
            ItemClickListener itemClickListener;


            StroyCardViewHolder(View itemView) {
                super(itemView);
                cv = (CardView)itemView.findViewById(R.id.cv);
                storyName = (TextView)itemView.findViewById(R.id.story_name);
                storyDate = (TextView)itemView.findViewById(R.id.story_date);
                storyCover = (ImageView)itemView.findViewById(R.id.story_cover);
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
