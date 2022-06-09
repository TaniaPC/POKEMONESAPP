/*
 *   Creado por TaniaPC
 *   03/06/22
 * */

package com.example.pokes_app.pokesapi;

import android.content.Context;
import android.content.Intent;

import com.google.android.material.snackbar.Snackbar;
import androidx.recyclerview.widget.RecyclerView;
import androidx.cardview.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.R;
import android.R.id;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.pokes_app.models.Pokemon;


import java.util.ArrayList;

public class ListaPokemonesAdapter extends RecyclerView.Adapter<ListaPokemonesAdapter.ViewHolder> {
    private ArrayList<Pokemon> dataset;
    private Context context;
    private Pokemon p;

    public ListaPokemonesAdapter(Context context) {
        this.context = context;
        dataset = new ArrayList<>();
    }


    @Override
    public ListaPokemonesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pokemones, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ListaPokemonesAdapter.ViewHolder holder, int position) {
        p = dataset.get(position);
        holder.nomTextView.setText(p.getName());
        Glide.with(context)
                .load("http://pokeapi.co/media/sprites/pokemon/" + p.getNumber() + ".png")
                .centerCrop()
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.photoImageView);

    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public void adicionarListaPokemon(ArrayList<Pokemon> listaPokemon) {
        dataset.addAll(listaPokemon);
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView photoImageView;
        private TextView nomTextView;
        private CardView cartas;

        public ViewHolder(View itemView) {
            super(itemView);

            photoImageView = (ImageView) itemView.findViewById(R.id.photoImageView);
            nomTextView = (TextView) itemView.findViewById(R.id.nomTextView);
            cartas = (CardView) itemView.findViewById(R.id.cartas);

            cartas.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.cartas:
                    String pokemon =  p.getName();

                    Intent i = new Intent(v.getContext(),ButterFreeActivity.class);
                    v.getContext().startActivity(i);
                    Snackbar.make(v, pokemon, Snackbar.LENGTH_SHORT).show();
                    break;
            }

        }
    }
}
