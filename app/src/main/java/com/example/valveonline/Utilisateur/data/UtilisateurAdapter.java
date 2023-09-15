package com.example.valveonline.Utilisateur.data;

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

public class UtilisateurAdapter extends RecyclerView.Adapter<UtilisateurAdapter.UtilisateurListAdapter>{

    Context context;
    List<UtilisateurResponse> list;

    public UtilisateurAdapter(Context context) {
        this.context = context;
        list = new ArrayList<>();
    }

    @NonNull
    @Override
    public UtilisateurListAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.model_utilisateur, parent, false);
        return new UtilisateurListAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UtilisateurListAdapter holder, int position) {
        UtilisateurResponse utilisateurResponse = list.get(position);
        holder.textView_nom.setText("" + utilisateurResponse.getNomUtilisateur());
        holder.textView_niveau.setText("" + utilisateurResponse.getNiveauUtilisateur());
    }

    @Override
    public int getItemCount() {
        return this.list.size();
    }

    class UtilisateurListAdapter extends RecyclerView.ViewHolder
    {
        TextView textView_nom;
        TextView textView_niveau;

        LinearLayout linearLayoutArticle;

        public UtilisateurListAdapter(@NonNull View itemView) {
            super(itemView);

            textView_nom = itemView.findViewById(R.id.modelUtilisateurNom);
            textView_niveau = itemView.findViewById(R.id.modelUtilisateurNiveau);
            linearLayoutArticle = itemView.findViewById(R.id.model_article_linear_selectionner);

        }
    }

    public void setList(List<UtilisateurResponse> list)
    {
        this.list.clear();
        this.list.addAll(list);
    }
}
