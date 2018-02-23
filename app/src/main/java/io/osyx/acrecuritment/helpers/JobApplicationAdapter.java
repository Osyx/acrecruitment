package io.osyx.acrecuritment.helpers;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.Collections;
import java.util.List;

import io.osyx.acrecuritment.R;

public class JobApplicationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private LayoutInflater inflater;
    private List<JobApplication> data = Collections.emptyList();

    public JobApplicationAdapter(Context context, List<JobApplication> data){
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.data = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.container_job_application, parent,false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        MyHolder myHolder = (MyHolder) holder;
        JobApplication current = data.get(position);
        myHolder.textPersonName.setText(current.person.name + " " + current.person.surname);
        myHolder.textEmail.setText("Email: " + current.person.email);
        myHolder.textAppDate.setText("Application Date: " + current.application.date);
        myHolder.textAccepted.setText(current.application.accepted);
        myHolder.textAccepted.setTextColor(ContextCompat.getColor(context, R.color.colorAccent));

    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    class MyHolder extends RecyclerView.ViewHolder{

        TextView textPersonName;
        TextView textEmail;
        TextView textAppDate;
        TextView textAccepted;

        MyHolder(View itemView) {
            super(itemView);
            textPersonName = itemView.findViewById(R.id.textPersonName);
            textEmail = itemView.findViewById(R.id.textEmail);
            textAppDate = itemView.findViewById(R.id.textAppDate);
            textAccepted = itemView.findViewById(R.id.textAccepted);
        }

    }

}
