package com.example.dtrack;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class paymentAdapter extends RecyclerView.Adapter<paymentAdapter.ExampleViewHolder> {
    private Context mContext;
    private ArrayList<Payment> mExampleList;

    private onItemClickListner mListner;

    public interface onItemClickListner {

        void onItemClick(int postion);
    }

    public void setOnItemClickListner(onItemClickListner listner) {

        mListner = listner;
    }

    public paymentAdapter(Context context, ArrayList<Payment> exampleList) {
        mContext = context;
        mExampleList = exampleList;


    }

    @NonNull
    @Override
    public ExampleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.payment_item, parent, false);
        return new ExampleViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ExampleViewHolder holder, int position) {
        Payment currentItem = mExampleList.get(position);

        String duedate = currentItem.getDueDate();
        String amount = currentItem.getPaymentAmount();
        String status = currentItem.getPaymentStatus();
        String method = currentItem.getMethod();


        if (status.equals("YES") ) {
            Drawable placeholder = holder.mImageviewPayment.getContext().getResources().getDrawable(R.drawable.paid);
            holder.mImageviewPayment.setImageDrawable(placeholder);
            holder.mconstraintLayout.setBackgroundColor(Color.parseColor("#98FB98"));
        }
        holder.mTextViewDuedate.setText("Monthly Payment :" + duedate);
        holder.mTextViewAmount.setText("LKR :" + amount);
        holder.mTextViewPaymentmethod.setText(method);


    }

    @Override
    public int getItemCount() {
        return mExampleList.size();
    }

    public class ExampleViewHolder extends RecyclerView.ViewHolder {

        public ConstraintLayout mconstraintLayout;
        public ImageView mImageviewPayment;
        public TextView mTextViewDuedate;
        public TextView mTextViewAmount;
        public TextView mTextViewPaymentmethod;

        public ExampleViewHolder(@NonNull View itemView) {
            super(itemView);
            mconstraintLayout = itemView.findViewById(R.id.paymentbackground);
            mImageviewPayment = itemView.findViewById(R.id.image_view_payment);
            mTextViewDuedate = itemView.findViewById(R.id.text_view_Due_date);
            mTextViewAmount = itemView.findViewById(R.id.text_view_Amount);
            mTextViewPaymentmethod =  itemView.findViewById(R.id.tvpaymentMethod);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mListner != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mListner.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
}
