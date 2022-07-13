package com.example.dtrack;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;


public class notificationAdapter extends RecyclerView.Adapter<notificationAdapter.ExampleViewHolder> {
    private Context mContext;
    private ArrayList<Notification> mExampleList;

    private onItemClickListner mListner;

    public interface onItemClickListner {

        void onItemClick(int postion);
    }
    public void setOnItemClickListner(onItemClickListner listner){

        mListner=listner;
    }

    public notificationAdapter(Context context, ArrayList<Notification> exampleList) {
        mContext = context;
        mExampleList = exampleList;


    }

    @NonNull
    @Override
    public ExampleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.notification_item, parent, false);
        return new ExampleViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ExampleViewHolder holder, int position) {
        Notification currentItem = mExampleList.get(position);


        String titile = currentItem.getTopic();
        String messagecontent= currentItem.getContent();
        String date =  currentItem.getDate();


        holder.mtitle.setText(titile);
        holder.mcontent.setText((CharSequence) messagecontent);
        holder.mdatetime.setText(date);


    }

    @Override
    public int getItemCount() {
        return mExampleList.size();
    }

    public class ExampleViewHolder extends RecyclerView.ViewHolder {

        public TextView mtitle;
        public TextView mcontent;
        public TextView mdatetime;

        public ExampleViewHolder(@NonNull View itemView) {
            super(itemView);

            mtitle = itemView.findViewById(R.id.text_view_title);
            mcontent = itemView.findViewById(R.id.text_view_content);
            mdatetime = itemView.findViewById(R.id.text_view_date);

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
