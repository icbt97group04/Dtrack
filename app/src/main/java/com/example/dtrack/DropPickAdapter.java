package com.example.dtrack;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dtrack.DropPick;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DropPickAdapter extends RecyclerView.Adapter<DropPickAdapter.ExampleViewHolder> {
    private Context mContext;
    private ArrayList<DropPick> mExampleList;

    private onItemClickListner mListner;

    public interface onItemClickListner {

        void onItemClick(int postion);
    }
    public void setOnItemClickListner(onItemClickListner listner){

        mListner=listner;
    }

    public DropPickAdapter(Context context, ArrayList<DropPick> exampleList) {
        mContext = context;
        mExampleList = exampleList;


    }

    @NonNull
    @Override
    public ExampleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.children_pick_drop_item, parent, false);
        return new ExampleViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ExampleViewHolder holder, int position) {
        DropPick currentItem = mExampleList.get(position);

        String firstname = currentItem.getFname();
        String lastname = currentItem.getLname();

        holder.mTextViewChildrenName.setText(firstname + "" +lastname);
    }

    @Override
    public int getItemCount() {
        return mExampleList.size();
    }

    public class ExampleViewHolder extends RecyclerView.ViewHolder {


        public TextView mTextViewChildrenName;
        public ImageView mimageviewmark;

        public ExampleViewHolder(@NonNull View itemView) {
            super(itemView);

            mTextViewChildrenName = itemView.findViewById(R.id.text_view_children_name);
            mimageviewmark = itemView.findViewById(R.id.image_view_pickanddropcheck);

            mimageviewmark.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(mListner != null){
                        int position = getAdapterPosition();
                        if(position!= RecyclerView.NO_POSITION){
                            mListner.onItemClick(position);
                        }
                    }
                }
            });


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(mListner != null){
                        int position = getAdapterPosition();
                        if(position!= RecyclerView.NO_POSITION){
                            mListner.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
}
