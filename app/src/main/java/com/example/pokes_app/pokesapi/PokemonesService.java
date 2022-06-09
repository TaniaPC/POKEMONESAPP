/*
 *   Creado por TaniaPC
 *   03/06/22
 * */

package com.example.pokes_app.pokesapi;

import com.example.pokes_app.models.PokemonesRespuesta;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PokemonesService {
    @GET("pokemones")
    Call<PokemonesRespuesta> obtenerListaPokemones(@Query("limit") int limit, @Query("offset") int offset);
}
