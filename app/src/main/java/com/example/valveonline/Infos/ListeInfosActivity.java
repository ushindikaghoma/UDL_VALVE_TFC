package com.example.valveonline.Infos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.valveonline.Faculte.data.FaculteReponse;
import com.example.valveonline.Infos.data.InfosAdapter;
import com.example.valveonline.Infos.data.InfosRepository;
import com.example.valveonline.Infos.data.InfosResponse;
import com.example.valveonline.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListeInfosActivity extends AppCompatActivity {

    InfosRepository infosRepository;
    InfosAdapter infosAdapter;
    RecyclerView recyclerViewListeInfos;
    ProgressBar progressBar;
    boolean visitor_mode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_infos);

        this.getSupportActionBar().setTitle("Infos à la une");

        visitor_mode = getIntent().getBooleanExtra("visitor_mode",false);


        recyclerViewListeInfos = findViewById(R.id.infos_recycle);
        progressBar = findViewById(R.id.infos_progress);

        recyclerViewListeInfos.setHasFixedSize(true);
        recyclerViewListeInfos.setLayoutManager(new LinearLayoutManager(this));


        infosRepository = InfosRepository.getInstance();
        infosAdapter = new InfosAdapter(this, visitor_mode);

        //Toast.makeText(this, ""+visitor_mode, Toast.LENGTH_SHORT).show();


        LoadListeInfos(progressBar);
    }


    public void LoadListeInfos(ProgressBar progressBarLoadArticle)
    {
        Call<List<InfosResponse>> call_liste_article = infosRepository.infosConnexion().getListeInfos();
        progressBarLoadArticle.setVisibility(View.VISIBLE);
        call_liste_article.enqueue(new Callback<List<InfosResponse>>() {
            @Override
            public void onResponse(Call<List<InfosResponse>> call, Response<List<InfosResponse>> response) {
                if (response.isSuccessful())
                {
                    progressBarLoadArticle.setVisibility(View.GONE);

                    List<InfosResponse> list_local = new ArrayList<>();
                    for (int a = 0; a < response.body().size(); a++)
                    {
                        InfosResponse liste_article =
                                new InfosResponse (
                                        response.body().get(a).getTitreInfos(),
                                        response.body().get(a).getDateInfos(),
                                        response.body().get(a).getDesciptionInfos(),
                                        response.body().get(a).getIdInfos()
                                );


                        list_local.add(liste_article);
                    }
                    infosAdapter.setList(list_local);
                    recyclerViewListeInfos.setAdapter(infosAdapter);

                }
            }

            @Override
            public void onFailure(Call<List<InfosResponse>> call, Throwable t) {
                progressBarLoadArticle.setVisibility(View.GONE);
            }
        });
    }
}