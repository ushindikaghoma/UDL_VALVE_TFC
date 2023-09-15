package com.example.valveonline.Horaire.data;

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

public class HoraireAdapter  extends RecyclerView.Adapter<HoraireAdapter.HoraireListAdapter>{

    Context context;
    List<HoraireResponse> list;

    public HoraireAdapter(Context context) {
        this.context = context;
        this.list = new ArrayList<>();
    }

    @NonNull
    @Override
    public HoraireListAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.model_horaire, parent, false);
        return new HoraireListAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HoraireListAdapter holder, int position) {
        HoraireResponse horaireResponse = list.get(position);
        holder.textView_nom_cours.setText("" + horaireResponse.getCodeCours());
        //holder.textView_heure_debut.setText("" + horaireResponse.get);
        //holder.textView_heure_fin.setText("" + horaireResponse.getDesignationFaculte());
    }

    @Override
    public int getItemCount() {
        return this.list.size();
    }

    class HoraireListAdapter extends RecyclerView.ViewHolder
    {
        TextView textView_nom_cours;
        TextView textView_heure_debut;
        TextView textView_heure_fin;

        LinearLayout linearLayoutArticle;

        public HoraireListAdapter(@NonNull View itemView) {
            super(itemView);

            textView_nom_cours = itemView.findViewById(R.id.modelHoraireNomCours);
            textView_heure_debut = itemView.findViewById(R.id.modelHoraireDateDebut);
            textView_heure_fin = itemView.findViewById(R.id.modelHoraireDateFin);
            linearLayoutArticle = itemView.findViewById(R.id.model_article_linear_selectionner);

        }
    }

    public void setList(List<HoraireResponse> list)
    {
        this.list.clear();
        this.list.addAll(list);
    }
}
