package com.example.valveonline.Infos.data;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.valveonline.DataBaseConnector.Reponse;
import com.example.valveonline.Faculte.data.FaculteAdapter;
import com.example.valveonline.Faculte.data.FaculteReponse;
import com.example.valveonline.Horaire.PublierHoraireActivity;
import com.example.valveonline.Horaire.data.HoraireResponse;
import com.example.valveonline.Infos.PublierInfosActivity;
import com.example.valveonline.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InfosAdapter extends RecyclerView.Adapter<InfosAdapter.InfosListAdapter>{

    Context context;
    List<InfosResponse> list;
    InfosRepository infosRepository;
    String pref_nom_utilisateur, pref_niveau_utilisateur,
            pref_fonction_utilisateur, pref_designation_utilisateur,
            pref_code_faculte, pref_code_promotion;
          boolean  _mode_visiteur ;
    SharedPreferences preferences;
    public static SharedPreferences.Editor editor;

    public InfosAdapter(Context context, boolean mode_visiteur) {
        this.context = context;
        this.list = new ArrayList<>();
        this._mode_visiteur = mode_visiteur;

        infosRepository = InfosRepository.getInstance();

        preferences = context.getSharedPreferences("maPreference", MODE_PRIVATE);
        editor = preferences.edit();

        pref_nom_utilisateur = preferences.getString("pref_nom_user","");
        pref_niveau_utilisateur = preferences.getString("pref_niveau_user","");
        pref_code_faculte = preferences.getString("pref_code_faculte","");
        pref_code_promotion = preferences.getString("pref_code_promotion","");


    }

    @NonNull
    @Override
    public InfosListAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.model_infos, parent, false);
        return new InfosListAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InfosListAdapter holder, int position) {
        final InfosResponse infosResponse = list.get(position);
        holder.textView_titre_infos.setText("" + infosResponse.getTitreInfos());
        holder.textView_date_infos.setText("" + infosResponse.getDateInfos());
        holder.textView_description_infos.setText("" + infosResponse.getDesciptionInfos());

        if (pref_niveau_utilisateur.equals("Etudiant") || _mode_visiteur)
        {
            holder.textView_item_option_menu.setVisibility(View.GONE);
        }else
        {
            holder.textView_item_option_menu.setVisibility(View.VISIBLE);
        }

        holder.textView_item_option_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                PopupMenu popupMenu = new PopupMenu(context, holder.textView_item_option_menu);
                popupMenu.inflate(R.menu.menu_options);

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {

                        int id = menuItem.getItemId();

                        if (id == R.id.action_item_modifer)
                        {
                            context.startActivity(new Intent(context, PublierInfosActivity.class)
                                    .putExtra("type","modifier")
                                    .putExtra("myObject", infosResponse));
                        }
                        else if (id == R.id.action_item_supprimer)
                        {
                            AlertDialog alertDialogConfirm = new AlertDialog.Builder(context).create();
                            alertDialogConfirm.setTitle("Confirmation");
                            alertDialogConfirm.setMessage("Voulez-vous vraiment supprimer ?");
                            alertDialogConfirm.setCancelable(false);
                            alertDialogConfirm.setButton(DialogInterface.BUTTON_NEGATIVE, "Annuler", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    alertDialogConfirm.dismiss();
                                }
                            });

                            alertDialogConfirm.setButton(DialogInterface.BUTTON_POSITIVE, "Confirmer", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    alertDialogConfirm.dismiss();
                                    SupprimerInfos(infosResponse);

                                }
                            });

                            alertDialogConfirm.show();
                        } else if (id == R.id.action_item_cloturer) {

                            AlertDialog alertDialogConfirm = new AlertDialog.Builder(context).create();
                            alertDialogConfirm.setTitle("Confirmation");
                            alertDialogConfirm.setMessage("Voulez-vous vraiment archiver ?");
                            alertDialogConfirm.setCancelable(false);
                            alertDialogConfirm.setButton(DialogInterface.BUTTON_NEGATIVE, "Annuler", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    alertDialogConfirm.dismiss();
                                }
                            });

                            alertDialogConfirm.setButton(DialogInterface.BUTTON_POSITIVE, "Confirmer", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    alertDialogConfirm.dismiss();
                                    UpdateEtatInfos(infosResponse);

                                }
                            });

                            alertDialogConfirm.show();
                        }

                        return false;
                    }
                });
                popupMenu.show();
            }
        });
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
        TextView textView_item_option_menu;

        LinearLayout linearLayoutArticle;

        public InfosListAdapter(@NonNull View itemView) {
            super(itemView);

            textView_titre_infos = itemView.findViewById(R.id.modelInfosTitre);
            textView_date_infos = itemView.findViewById(R.id.modelInfosDate);
            textView_description_infos = itemView.findViewById(R.id.modelInfosDescription);
            textView_item_option_menu = itemView.findViewById(R.id.modelInfosItemOptionMenu);
            linearLayoutArticle = itemView.findViewById(R.id.model_article_linear_selectionner);

        }
    }

    public void setList(List<InfosResponse> list)
    {
        this.list.clear();
        this.list.addAll(list);
    }

    public void UpdateEtatInfos(InfosResponse infosResponse)
    {
        Call<Reponse> call_prix_depot = infosRepository.infosConnexion().ArchiverInfos(infosResponse);
        //progressBarLoadPrix.setVisibility(View.VISIBLE);
        call_prix_depot.enqueue(new Callback<Reponse>() {
            @Override
            public void onResponse(Call<Reponse> call, Response<Reponse> response) {
                if (response.isSuccessful())
                {
                    Reponse saveee = response.body();

                    boolean success = saveee.isSuccess();
                    String message = saveee.getMessage();

                    Log.e("OPERATION",response.body().toString());
                    if(success){
                        Toast.makeText(context, ""+message, Toast.LENGTH_SHORT).show();

                    }else{
                        Toast.makeText(context, "Echec"+message, Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<Reponse> call, Throwable t) {
                //progressBarLoadPrix.setVisibility(View.GONE);
            }
        });
    }

    public void SupprimerInfos(InfosResponse infosResponse)
    {
        Call<Reponse> call_prix_depot = infosRepository.infosConnexion().SupprimerInfos(infosResponse);
        //progressBarLoadPrix.setVisibility(View.VISIBLE);
        call_prix_depot.enqueue(new Callback<Reponse>() {
            @Override
            public void onResponse(Call<Reponse> call, Response<Reponse> response) {
                if (response.isSuccessful())
                {
                    Reponse saveee = response.body();

                    boolean success = saveee.isSuccess();
                    String message = saveee.getMessage();

                    Log.e("OPERATION",response.body().toString());
                    if(success){
                        Toast.makeText(context, ""+message, Toast.LENGTH_SHORT).show();

                    }else{
                        Toast.makeText(context, ""+message, Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<Reponse> call, Throwable t) {
                //progressBarLoadPrix.setVisibility(View.GONE);
            }
        });
    }
}
