package com.example.tkid.testvknews;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collection;

public class Adapter extends RecyclerView.Adapter<Adapter.AdapterViewHolider> {

    private ArrayList<Newsitem> nitems = new ArrayList<>();

    public class AdapterViewHolider extends RecyclerView.ViewHolder {
        private ImageView imagenews;
        private TextView content;

        public AdapterViewHolider(View itemView) {
            super(itemView);
            imagenews = itemView.findViewById(R.id.imageView4);
            content = itemView.findViewById(R.id.textView3);
        }

        public void bind (Newsitem newsitem){
            content.setText(newsitem.getText());

            String newsPhotoUrl = newsitem.getImageURL();
            Picasso.get().load(newsPhotoUrl).into(imagenews);
            imagenews.setVisibility(newsPhotoUrl != null ? View.VISIBLE : View.GONE);

        }
    }

    @Override
    public AdapterViewHolider onCreateViewHolder(ViewGroup parent, int viewType) {
        return new AdapterViewHolider(LayoutInflater.from(parent.getContext()).inflate(R.layout.news_view, parent, false)) ;
    }

    @Override
    public void onBindViewHolder(AdapterViewHolider holder, int position) {
               holder.bind(nitems.get(position));
    }

    @Override
    public int getItemCount() {
        return nitems.size();
    }

    public void clearItems(){
        nitems.clear();
        notifyDataSetChanged();
    }
    public void setItem(Collection<Newsitem> newses){
        nitems.addAll(newses);
        notifyDataSetChanged();
    }


}
