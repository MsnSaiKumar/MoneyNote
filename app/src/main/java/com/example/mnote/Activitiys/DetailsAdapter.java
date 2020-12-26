package com.example.mnote.Activitiys;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mnote.R;

import java.util.List;

public class DetailsAdapter extends RecyclerView.Adapter <DetailsAdapter.DetailsViewHolder>{

    private List<Users> userDetailsList;

    public DetailsAdapter(List<Users> userDetailsList) {
        this.userDetailsList = userDetailsList;
    }


    @NonNull
    @Override
    public DetailsAdapter.DetailsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.print_details , parent ,false);

        return new DetailsViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull DetailsAdapter.DetailsViewHolder holder, int position) {

        Users user_details = userDetailsList.get(position);

        holder.dateTV.setText(user_details.getDate()+"");
        holder.noteTV.setText(user_details.getNote()+"");
        holder.amountTV.setText(user_details.getAmount()+"");
        holder.totalTV.setText(user_details.getTotal()+"");

        if(user_details.getValue().equals(Constant.ADD)){
            holder.addedTV.setText("true");
            holder.deductedTV.setText("....");
        }
        else if(user_details.getValue().equals(Constant.DEDUCTED)){
            holder.addedTV.setText("....");
            holder.deductedTV.setText("true");
        }
    }

    @Override
    public int getItemCount() {
        return userDetailsList.size();
    }

    public class DetailsViewHolder  extends RecyclerView.ViewHolder {
        View mView;
        TextView dateTV , noteTV , amountTV , addedTV , deductedTV , totalTV;

        public DetailsViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;

            dateTV = (TextView)mView.findViewById(R.id.id_date);
            noteTV = (TextView)mView.findViewById(R.id.id_note);
            amountTV = (TextView)mView.findViewById(R.id.id_amount);
            addedTV = (TextView)mView.findViewById(R.id.id_added);
            deductedTV = (TextView)mView.findViewById(R.id.id_deducted);
            totalTV = (TextView)mView.findViewById(R.id.id_total);
        }
    }
}
