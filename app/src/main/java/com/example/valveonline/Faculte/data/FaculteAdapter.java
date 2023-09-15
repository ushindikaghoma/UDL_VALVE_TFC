package com.example.valveonline.Faculte.data;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.valveonline.R;

import java.util.ArrayList;
import java.util.List;

public class FaculteAdapter extends RecyclerView.Adapter<FaculteAdapter.FaculteListAdapter>{

    Context context;
    private ArrayList<FaculteReponse> list;

    public FaculteAdapter(Context context) {
        this.context = context;
        this.list = new ArrayList<>();
    }

    @NonNull
    @Override
    public FaculteListAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.model_faculte, parent, false);
        return new FaculteListAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FaculteListAdapter holder, int position) {
        FaculteReponse faculteReponse = list.get(position);
        holder.textView_code_faculte.setText("" + faculteReponse.getCodeFaculte());
        holder.textView_designation_faculte.setText("" + faculteReponse.getDesignationFaculte());
    }

    @Override
    public int getItemCount() {
        return this.list.size();
    }

    class FaculteListAdapter extends RecyclerView.ViewHolder
    {
        TextView textView_code_faculte;
        TextView textView_designation_faculte;

        LinearLayout linearLayoutArticle;

        public FaculteListAdapter(@NonNull View itemView) {
            super(itemView);

            textView_code_faculte = itemView.findViewById(R.id.modelFaculteCode);
            textView_designation_faculte = itemView.findViewById(R.id.modelFaculteDesignation);
            linearLayoutArticle = itemView.findViewById(R.id.model_article_linear_selectionner);

        }
    }

    public void setList(List<FaculteReponse> list)
    {
        this.list.clear();
        this.list.addAll(list);
    }
}
