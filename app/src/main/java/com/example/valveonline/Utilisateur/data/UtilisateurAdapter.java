package com.example.valveonline.Utilisateur.data;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.valveonline.DataBaseConnector.Reponse;
import com.example.valveonline.Faculte.data.FaculteAdapter;
import com.example.valveonline.Faculte.data.FaculteReponse;
import com.example.valveonline.Faculte.data.FaculteRepository;
import com.example.valveonline.Infos.PublierInfosActivity;
import com.example.valveonline.Infos.data.InfosResponse;
import com.example.valveonline.Promotion.data.PromotionRepository;
import com.example.valveonline.Promotion.data.PromotionResponse;
import com.example.valveonline.R;
import com.example.valveonline.Utilisateur.NouvelUtilisateurActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UtilisateurAdapter extends RecyclerView.Adapter<UtilisateurAdapter.UtilisateurListAdapter>{

    Context context;
    List<UtilisateurResponse> list;

    String pref_nom_utilisateur, pref_niveau_utilisateur,
            pref_fonction_utilisateur, pref_designation_utilisateur,
            pref_code_faculte, pref_code_promotion;
    boolean  _mode_visiteur ;
    SharedPreferences preferences;
    public static SharedPreferences.Editor editor;
    FaculteRepository faculteRepository;
    PromotionRepository promotionRepository;
    UtilisateurRepository utilisateurRepository;
    ArrayAdapter arrayAdapterFaculte, arrayAdapterPromotion;


    public UtilisateurAdapter(Context context) {
        this.context = context;
        list = new ArrayList<>();

        faculteRepository = FaculteRepository.getInstance();
        promotionRepository = PromotionRepository.getInstance();
        utilisateurRepository = UtilisateurRepository.getInstance();

        preferences = context.getSharedPreferences("maPreference", MODE_PRIVATE);
        editor = preferences.edit();

        pref_nom_utilisateur = preferences.getString("pref_nom_user","");
        pref_niveau_utilisateur = preferences.getString("pref_niveau_user","");
        pref_code_faculte = preferences.getString("pref_code_faculte","");
        pref_code_promotion = preferences.getString("pref_code_promotion","");
    }

    @NonNull
    @Override
    public UtilisateurListAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.model_utilisateur, parent, false);
        return new UtilisateurListAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UtilisateurListAdapter holder, int position) {
        final UtilisateurResponse _utilisateurResponse = list.get(position);
        holder.textView_nom.setText("" + _utilisateurResponse.getNomUtilisateur());
        holder.textView_niveau.setText("" + _utilisateurResponse.getNiveauUtilisateur());

        if (pref_niveau_utilisateur.equals("Admin"))
        {
            holder.textView_option_menu.setVisibility(View.VISIBLE);
            holder.linearLayoutArticle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    View myView = LayoutInflater.from(context).inflate(R.layout.dialog_nouvel_utilisateur, null);
                    builder.setView(myView);

                    EditText nom_user = myView.findViewById(R.id.dialog_utilisateur_designation);
                    EditText password_user = myView.findViewById(R.id.dialog_utilisateur_password);
                    Spinner niveau_user = myView.findViewById(R.id.utilisateur_spinner_niveau);
                    Spinner faculte = myView.findViewById(R.id.utilisateur_spinner_faculte);
                    Spinner promotion = myView.findViewById(R.id.utilisateur_spinner_promotion);
                    Button save_user = myView.findViewById(R.id.dialog_utilisateur_save);
                    ProgressBar progress = myView.findViewById(R.id.dialog_utilisateur_saveprogress);

                    nom_user.setText(_utilisateurResponse.getNomUtilisateur());
                    password_user.setText(_utilisateurResponse.getMotPasseUtilisateur());

                    new AsyncLoadSpinner(progress, faculte, promotion, niveau_user).execute();


                    AlertDialog dialog = builder.create();
                    dialog.show();

                    save_user.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            new AsyncUpdateUtilisateur(faculte.getSelectedItem().toString(),
                                    promotion.getSelectedItem().toString(),
                                    niveau_user.getSelectedItem().toString(),
                                    nom_user, password_user,progress, dialog,
                                    _utilisateurResponse.getIdUtilisateur()).execute();
                        }
                    });

                }
            });

            holder.textView_option_menu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    PopupMenu popupMenu = new PopupMenu(context, holder.textView_option_menu);
                    popupMenu.inflate(R.menu.menu_option_user);

                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem menuItem) {

                            int id = menuItem.getItemId();

                             if (id == R.id.action_item_supprimer)
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
                                        SupprimerUtilisateur(_utilisateurResponse);

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
        }else
        {
            holder.textView_option_menu.setVisibility(View.GONE);
            Toast.makeText(context, "Pas authorisé!!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public int getItemCount() {
        return this.list.size();
    }

    class UtilisateurListAdapter extends RecyclerView.ViewHolder
    {
        TextView textView_nom;
        TextView textView_niveau;
        TextView textView_option_menu;

        LinearLayout linearLayoutArticle;

        public UtilisateurListAdapter(@NonNull View itemView) {
            super(itemView);

            textView_nom = itemView.findViewById(R.id.modelUtilisateurNom);
            textView_niveau = itemView.findViewById(R.id.modelUtilisateurNiveau);
            textView_option_menu = itemView.findViewById(R.id.modelIUtilisateurItemOptionMenu);
            linearLayoutArticle = itemView.findViewById(R.id.model_user_linear_selectionner);

        }
    }

    public void setList(List<UtilisateurResponse> list)
    {
        this.list.clear();
        this.list.addAll(list);
    }

    public void LoadListeNiveau(Spinner spinner_liste_niveau)
    {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("Admin");
        arrayList.add("Etudiant");
        arrayList.add("Appariteur");
        arrayList.add("Visiteur");

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(context,
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, arrayList);
        spinner_liste_niveau.setAdapter(arrayAdapter);
    }
    public void LoadListeFaculte(Spinner spinner_liste_faculte)
    {
        Call<List<FaculteReponse>> call_liste_depot = faculteRepository.faculteConnexion().getListeFaculte();
        //loadDepot.setVisibility(View.VISIBLE);
        call_liste_depot.enqueue(new Callback<List<FaculteReponse>>() {
            @Override
            public void onResponse(Call<List<FaculteReponse>> call, Response<List<FaculteReponse>> response) {
                if (response.isSuccessful())
                {
                    //loadDepot.setVisibility(View.GONE);

                    List<String>list_local_depot = new ArrayList<>();
                    for (int a = 0; a < response.body().size(); a++)
                    {
                        FaculteReponse liste_faculte=
                                new FaculteReponse (
                                        response.body().get(a).getCodeFaculte(),
                                        response.body().get(a).getDesignationFaculte()
                                );

                        list_local_depot.add(response.body().get(a).getCodeFaculte());
                    }
                    arrayAdapterFaculte = new ArrayAdapter<>(context, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, list_local_depot);

                    spinner_liste_faculte.setAdapter(arrayAdapterFaculte);

                }
            }

            @Override
            public void onFailure(Call<List<FaculteReponse>> call, Throwable t) {
                //loadDepot.setVisibility(View.GONE);
            }
        });
    }
    public void LoadListePromotion(Spinner spinner_liste_promotione)
    {
        Call<List<PromotionResponse>> call_liste_depot = promotionRepository.promotionConnexion().getListePromotion();
        //loadDepot.setVisibility(View.VISIBLE);
        call_liste_depot.enqueue(new Callback<List<PromotionResponse>>() {
            @Override
            public void onResponse(Call<List<PromotionResponse>> call, Response<List<PromotionResponse>> response) {
                if (response.isSuccessful())
                {
                    //loadDepot.setVisibility(View.GONE);

                    List<String>list_local_depot = new ArrayList<>();
                    for (int a = 0; a < response.body().size(); a++)
                    {
                        PromotionResponse liste_faculte=
                                new PromotionResponse (
                                        response.body().get(a).getCodePromotion(),
                                        response.body().get(a).getCodeFaculte(),
                                        response.body().get(a).getDesignationPromotion()
                                );

                        list_local_depot.add(response.body().get(a).getCodePromotion());
                    }
                    arrayAdapterPromotion = new ArrayAdapter<>(context, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, list_local_depot);

                    spinner_liste_promotione.setAdapter(arrayAdapterPromotion);

                }
            }

            @Override
            public void onFailure(Call<List<PromotionResponse>> call, Throwable t) {
                //loadDepot.setVisibility(View.GONE);
            }
        });
    }

    public class AsyncLoadSpinner extends AsyncTask<Void, Void, Void>
    {
        ProgressBar load;
        Spinner faculte, promotion, niveau;

        public AsyncLoadSpinner(ProgressBar load, Spinner faculte,
                                Spinner promotion, Spinner niveau) {
            this.load = load;
            this.faculte = faculte;
            this.promotion = promotion;
            this.niveau = niveau;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            load.setVisibility(View.VISIBLE);


        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            load.setVisibility(View.GONE);
        }

        @Override
        protected Void doInBackground(Void... voids) {

            LoadListeFaculte(faculte);
            LoadListePromotion(promotion);
            LoadListeNiveau(niveau);

            return null;
        }
    }

    public class AsyncUpdateUtilisateur extends AsyncTask<Void, Void, Void>
    {
        String codeFaculte, codePromotion, niveau;
        EditText editTextNom, editTextPassword;
        ProgressBar saveProgress;
        AlertDialog dialog;
        int IdUser;

        public AsyncUpdateUtilisateur(String codeFaculte, String codePromotion,
                                    String niveau,
                                    EditText editTextNom,
                                    EditText editTextPassword,
                                    ProgressBar saveProgress,
                                    AlertDialog dialog, int idUser) {
            this.codeFaculte = codeFaculte;
            this.codePromotion = codePromotion;
            this.niveau = niveau;
            this.editTextNom = editTextNom;
            this.editTextPassword = editTextPassword;
            this.saveProgress = saveProgress;
            this.dialog = dialog;
            this.IdUser = idUser;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            saveProgress.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);

            saveProgress.setVisibility(View.GONE);
            editTextNom.setText("");
            editTextPassword.setText("");

            dialog.dismiss();


        }

        @Override
        protected Void doInBackground(Void... voids) {

            UtilisateurRepository utilisateurRepository = UtilisateurRepository.getInstance();
            UtilisateurResponse utilisateurResponse = new UtilisateurResponse();

            utilisateurResponse.setCodeFaculte(codeFaculte);
            utilisateurResponse.setCodePromotion(codePromotion);
            utilisateurResponse.setNomUtilisateur(editTextNom.getText().toString());
            utilisateurResponse.setMotPasseUtilisateur(editTextPassword.getText().toString());
            utilisateurResponse.setNiveauUtilisateur(niveau);
            utilisateurResponse.setDesignationUtilisateur(editTextNom.getText().toString());
            utilisateurResponse.setFonctionUtilisateur(niveau);
            utilisateurResponse.setIdUtilisateur(IdUser);
            utilisateurRepository.utilisateurConnexion().UpdateUtilisateur(utilisateurResponse).enqueue(new Callback<Reponse>()
            {
                @Override
                public void onResponse(Call<Reponse> call, Response<Reponse> response) {
                    if (response.isSuccessful())
                    {
                        Log.e("Faculte",""+response);


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
                    else
                    {
                        switch (response.code())
                        {
                            case 404:
                                Toast.makeText(context, "Serveur introuvable", Toast.LENGTH_LONG).show();
                                Log.e("Facule",""+response);
                                break;
                            case 500:
                                Toast.makeText(context, "Serveur en pane",Toast.LENGTH_LONG).show();
                                Log.e("Faculte",""+response);
                                break;
                            default:
                                Toast.makeText(context, "Erreur inconnu", Toast.LENGTH_LONG).show();
                                Log.e("Faculte",""+response);
                                break;
                        }
                    }
                }

                @Override
                public void onFailure(Call<Reponse> call, Throwable t) {
                    Toast.makeText(context, "Probleme de connection", Toast.LENGTH_LONG).show();
                }
            });

            return null;
        }
    }

    public void SupprimerUtilisateur(UtilisateurResponse utilisateurResponse)
    {
        Call<Reponse> call_prix_depot = utilisateurRepository.utilisateurConnexion().SupprimerUtilisateur(utilisateurResponse);
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
