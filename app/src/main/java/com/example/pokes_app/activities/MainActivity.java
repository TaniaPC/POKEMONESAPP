/*
*   Creado por TaniaPC
*   03/06/22
* */

package com.example.pokes_app.activities;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.R;
import android.R.layout;

import com.example.pokes_app.models.Pokemon;
import com.example.pokes_app.models.PokemonesRespuesta;
import com.example.pokes_app.pokesapi.PokemonesService;
import com.example.pokes_app.pokesapi.ListaPokemonesAdapter;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private Retrofit retrofit;
    private static final String TAG = "POKEMONESAPP";

    private RecyclerView recyclerView;
    private ListaPokemonesAdapter listaPokemonesAdapter;

    private int offset;
    private boolean aptoParaCargar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.pokes_app.R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        listaPokemonesAdapter = new ListaPokemonesAdapter(this);
        recyclerView.setAdapter(listaPokemonesAdapter);
        recyclerView.setHasFixedSize(true);

        final GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy >0){
                    int visibleItemCount = layoutManager.getChildCount();
                    int totalItemCount = layoutManager.getItemCount();
                    int pastVisibleItems = layoutManager.findFirstVisibleItemPosition();

                    if(aptoParaCargar) {
                        if ((visibleItemCount +pastVisibleItems ) >= totalItemCount) {
                            Log.i(TAG, " Llegamos al final");
                            aptoParaCargar = false;
                            offset += 20;
                            obtenerDatos(offset);
                        }
                    }
                }
            }
        });

        retrofit = new Retrofit.Builder()
                .baseUrl("http://pokeapi.co/api/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        aptoParaCargar = true;
        offset = 0;
        obtenerDatos(offset);
    }

    private void obtenerDatos(int offset) {
        PokemonesService service = retrofit.create(PokemonesService.class);
        Call<PokemonesRespuesta> pokemonRespuestaCall = service.obtenerListaPokemones(20,offset);

        pokemonRespuestaCall.enqueue(new Callback<PokemonesRespuesta>() {
            @Override
            public void onResponse(Call<PokemonesRespuesta> call, Response<PokemonesRespuesta> response) {
                aptoParaCargar = true;
                if(response.isSuccessful()){
                    PokemonesRespuesta pokemonesRespuesta = response.body();

                    ArrayList<Pokemon> listaPokemon = pokemonesRespuesta.getResults();

                    listaPokemonesAdapter.adicionarListaPokemon(listaPokemon);

                } else
                    Log.e(TAG, " on response "+ response.errorBody());
            }

            @Override
            public void onFailure(Call<PokemonesRespuesta> call, Throwable t) {
                aptoParaCargar = true;
                Log.e(TAG," on Failure "+ t.getMessage());
            }
        });
    }

}
