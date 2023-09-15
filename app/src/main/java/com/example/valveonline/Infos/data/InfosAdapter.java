package com.example.valveonline.Infos.data;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.valveonline.Faculte.data.FaculteAdapter;
import com.example.valveonline.Faculte.data.FaculteReponse;
import com.example.valveonline.R;

import java.util.ArrayList;
import java.util.List;

public class InfosAdapter extends RecyclerView.Adapter<InfosAdapter.InfosListAdapter>{

    Context context;
    List<InfosResponse> list;

    public InfosAdapter(Context context) {
        this.context = context;
        this.list = new ArrayList<>();
    }

    @NonNull
    @Override
    public InfosListAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.model_infos, parent, false);
        return new InfosListAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InfosListAdapter holder, int position) {
        InfosResponse infosResponse = list.get(position);
        holder.textView_titre_infos.setText("" + infosResponse.getTitreInfos());
        holder.textView_date_infos.setText("" + infosResponse.getDateInfos());
        holder.textView_description_infos.setText("" + infosResponse.getDesciptionInfos());
    }

    @Override
    public int getItemCount() {
        return this.list.size();
    }

    class InfosListAdapter extends RecyclerView.ViewHolder
    {
        TextView textView_titre_infos;
        TextView textView_date_infos;
        TextView textView_description_infos;

        LinearLayout linearLayoutArticle;

        public InfosListAdapter(@NonNull View itemView) {
            super(itemView);

            textView_titre_infos = itemView.findViewById(R.id.modelInfosTitre);
            textView_date_infos = itemView.findViewById(R.id.modelInfosDate);
            textView_description_infos = itemView.findViewById(R.id.modelInfosDescription);
            linearLayoutArticle = itemView.findViewById(R.id.model_article_linear_selectionner);

        }
    }

    public void setList(List<InfosResponse> list)
    {
        this.list.clear();
        this.list.addAll(list);
    }
}
