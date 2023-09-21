package com.example.valveonline.Horaire.data;

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
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.valveonline.DataBaseConnector.Reponse;
import com.example.valveonline.Faculte.data.FaculteAdapter;
import com.example.valveonline.Faculte.data.FaculteReponse;
import com.example.valveonline.Horaire.ListeHoraireTousActivity;
import com.example.valveonline.Horaire.PublierHoraireActivity;
import com.example.valveonline.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HoraireAdapter  extends RecyclerView.Adapter<HoraireAdapter.HoraireListAdapter> {

    Context context;
    List<HoraireResponse> list;
    List<HoraireResponse> listResult;
    HoraireRepository horaireRepository;
    String pref_nom_utilisateur, pref_niveau_utilisateur,
            pref_fonction_utilisateur, pref_designation_utilisateur,
            pref_code_faculte, pref_code_promotion;
    SharedPreferences preferences;
    public static SharedPreferences.Editor editor;

    public HoraireAdapter(Context context, List<HoraireResponse> list) {
        this.context = context;
        this.list = list;

        this.listResult = new ArrayList<>();
        this.listResult.addAll(ListeHoraireTousActivity.list_local);

        horaireRepository = HoraireRepository.getInstance();

    }
    public HoraireAdapter(Context context) {
        this.context = context;
        this.list = new ArrayList<>();

        horaireRepository = HoraireRepository.getInstance();

        preferences = context.getSharedPreferences("maPreference", MODE_PRIVATE);
        editor = preferences.edit();

        pref_nom_utilisateur = preferences.getString("pref_nom_user","");
        pref_niveau_utilisateur = preferences.getString("pref_niveau_user","");
        pref_code_faculte = preferences.getString("pref_code_faculte","");
        pref_code_promotion = preferences.getString("pref_code_promotion","");
    }

    @NonNull
    @Override
    public HoraireListAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.model_horaire, parent, false);
        return new HoraireListAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HoraireListAdapter holder, int position) {
        final HoraireResponse horaireResponse = list.get(position);
        holder.textView_nom_cours.setText("" + horaireResponse.getCodeCours());
        //holder.textView_heure_debut.setText("" + horaireResponse.getHeureDebut());
        holder.textView_heure_fin.setText("De  " + horaireResponse.getHeureDebut()+"  à  "+ horaireResponse.getHeureFin());

        if (pref_niveau_utilisateur.equals("Etudiant"))
        {
            holder.textView_item_menu.setVisibility(View.GONE);
        }else
        {
            holder.textView_item_menu.setVisibility(View.VISIBLE);
        }

        holder.textView_item_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                PopupMenu popupMenu = new PopupMenu(context, holder.textView_item_menu);
                popupMenu.inflate(R.menu.menu_options);

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {

                        int id = menuItem.getItemId();

                        if (id == R.id.action_item_modifer)
                        {
                            context.startActivity(new Intent(context, PublierHoraireActivity.class)
                                    .putExtra("type","modifier")
                                    .putExtra("myObject", horaireResponse));
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
                                    SupprimerHoraire(horaireResponse);

                                }
                            });

                            alertDialogConfirm.show();

                        } else if (id == R.id.action_item_cloturer) {

                            AlertDialog alertDialogConfirm = new AlertDialog.Builder(context).create();
                            alertDialogConfirm.setTitle("Confirmation");
                            alertDialogConfirm.setMessage("Voulez-vous vraiment cloturer ?");
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
                                    UpdateEtatHoraire(horaireResponse);
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

    public void UpdateEtatHoraire(HoraireResponse horaireResponse)
    {
        Call<Reponse> call_prix_depot = horaireRepository.horaireConnexion().UpdadeEtatHoraire(horaireResponse);
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

    public void SupprimerHoraire(HoraireResponse horaireResponse)
    {
        Call<Reponse> call_prix_depot = horaireRepository.horaireConnexion().SupprimerHoraire(horaireResponse);
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

    @Override
    public int getItemCount() {
        return this.list.size();
    }

    class HoraireListAdapter extends RecyclerView.ViewHolder
    {
        TextView textView_nom_cours;
        TextView textView_heure_debut;
        TextView textView_heure_fin;
        TextView textView_item_menu;

        LinearLayout linearLayoutArticle;

        public HoraireListAdapter(@NonNull View itemView) {
            super(itemView);

            textView_nom_cours = itemView.findViewById(R.id.modelHoraireNomCours);
            textView_heure_debut = itemView.findViewById(R.id.modelHoraireDateDebut);
            textView_heure_fin = itemView.findViewById(R.id.modelHoraireDateFin);
            textView_item_menu = itemView.findViewById(R.id.modelHoraireItemOptionMenu);
            linearLayoutArticle = itemView.findViewById(R.id.model_article_linear_selectionner);

        }
    }

    public void setList(List<HoraireResponse> list)
    {
        this.list.clear();
        this.list.addAll(list);
    }

//    public Filter getFilter() {
//        return new Filter() {
//            @Override
//            protected FilterResults performFiltering(CharSequence charSequence) {
//                String charString = charSequence.toString();
//                if (charString.isEmpty()) {
//                    listResult = list;
//                } else {
//                    List<HoraireResponse> filteredList = new ArrayList<>();
//                    for (HoraireResponse row : listResult) {
//
//                        // name match condition. this might differ depending on your requirement
////                        // here we are looking for name or phone number match
//                        if (row.getCodeCours().toLowerCase().contains(charString.toLowerCase())) {
//                            filteredList.add(row);
//                        }
//                    }
//
//
//                    listResult = filteredList;
//
//                }
//                FilterResults filterResults = new FilterResults();
//                filterResults.values = listResult;
//                return filterResults;
//            }
//
//            @Override
//            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
//                listResult = (ArrayList<HoraireResponse>) filterResults.values;
//                notifyDataSetChanged();
//            }
//        };
//    }

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        this.list.clear();
        if (charText.length() == 0) {
            this.list.addAll(listResult);
        } else {
            for (HoraireResponse wp : listResult) {
                if ((wp.getCodeCours()).toLowerCase(Locale.getDefault()).contains(charText)){
                    this.list.add(wp);
                }

//                if (wp.getCodeFaculte().toLowerCase(Locale.getDefault()).contains(charText)){
//                    this.list.add(wp);
//                }

            }
        }
        notifyDataSetChanged();
    }
}
