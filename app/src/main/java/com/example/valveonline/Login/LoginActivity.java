package com.example.valveonline.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.valveonline.DataBaseConnector.DonneesFromMySQL;
import com.example.valveonline.DataBaseConnector.me_URL;
import com.example.valveonline.Home.HomeActivity;
import com.example.valveonline.R;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    ProgressDialog alertDialog;
    Button connecter ;
    TextView newAccount;
    EditText phone_edt, password_edt;
    String pref_nom_utilisateur, pref_niveau_utilisateur,
            pref_fonction_utilisateur, pref_designation_utilisateur,
            pref_code_faculte, pref_code_promotion,
            nom_user, password_user, fonction_user, niveau_user,
            code_faculte, code_promotion;

    SharedPreferences preferences;
    public static SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        this.getSupportActionBar().setTitle("Login");

        preferences = getSharedPreferences("maPreference", MODE_PRIVATE);
        editor = preferences.edit();

        phone_edt = findViewById(R.id.phone_number_edt);
        password_edt = findViewById(R.id.password_edt);
        connecter = findViewById(R.id.btn_connecter);

        connecter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (TextUtils.isEmpty(phone_edt.getText().toString().trim()))
                {
                    phone_edt.setError("Saisir le nom d'utilisateur");
                    phone_edt.requestFocus();
                    return;
                }else
                {
                    new connexionClass(phone_edt.getText().toString(),password_edt.getText().toString()).execute();

                }
            }
        });


    }


    private class  connexionClass extends AsyncTask<String, Void,String> {
        String username;
        String password;
        String reponseUserData;

        public connexionClass(String username, String password) {
            this.username = username;
            this.password = password;
        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            alertDialog = new ProgressDialog(LoginActivity.this);

            alertDialog.setCancelable(false);
            alertDialog.setMessage("Connection en cours...");
            alertDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            alertDialog.show();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            alertDialog.dismiss();
            if(s.contains("false")){
                Toast.makeText(LoginActivity.this, "Nom d'utilisateur ou mot de passe incorrect", Toast.LENGTH_SHORT).show();
            }else{

                //recuperation des information de l'utilisateur
                //connexion reussie avec succes
                try {

                    JSONArray jsonArray = new JSONArray(reponseUserData);



                    for(int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        Gson gson = new Gson(); // Or use new GsonBuilder().create();

                        nom_user = jsonObject.optString("nomUtilisateur");
                        password_user = jsonObject.optString("motPasseUtilisateur");
                        fonction_user = jsonObject.optString("fonctionUtilisateur");
                        niveau_user = jsonObject.optString("niveauUtilisateur");
                        code_faculte = jsonObject.optString("codeFaculte");
                        code_promotion = jsonObject.optString("codePromotion");

                        editor.putString("pref_nom_user",nom_user);
                        editor.putString("pref_foncion_user",fonction_user);
                        editor.putString("pref_niveau_user",niveau_user);
                        editor.putString("pref_code_faculte",code_faculte);
                        editor.putString("pref_code_promotion",code_promotion);
                        editor.commit();
                        editor.apply();
                    }

                    Toast.makeText(LoginActivity.this, "Connexion réussie avec succès", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                    //Toast.makeText(LoginActivity.this, ""+nom_user, Toast.LENGTH_LONG).show();
                    finish();

                } catch (JSONException e) {
                    e.printStackTrace();
                    s = e.toString();
                }

                //recuperation des information de l'utilsateur
            }
        }

        @Override
        protected String doInBackground(String... strings) {
            //verfication des l'existance d'un utilisateur
            String responseReturn = "";
            String reponse = DonneesFromMySQL.getDataFromServer(new me_URL(LoginActivity.this).IsUserExist(username,password));
            //String reponse = new DataFromAPI(LoginActivity.this).IsUserExist(username,password);
            if(reponse.contains("true")){
                //recuperation des information de l'uitlisateur
                reponseUserData = DonneesFromMySQL.getDataFromServer(new me_URL(LoginActivity.this).GetUserLogin(username));
                //String reponseUserData = new DataFromAPI(LoginActivity.this).GetLogin(username);
                responseReturn =  reponseUserData;
            }else{
                responseReturn = reponse;
            }

            return responseReturn;
        }
    }
}