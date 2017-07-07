package com.example.rahul.pokedex;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;


import java.util.List;

/**
 * Created by Rahul on 07-07-2017.
 */

public class PokemonAdapter extends ArrayAdapter<Pokemon> {
    private static final String LOCATION_SEPARATOR = " of ";

    /**
     * Constructs a new {@link PokemonAdapter}.
     *
     * @param context of the app
     * @param pokemons is the list of pokemons, which is the data source of the adapter
     */
    public PokemonAdapter(Context context, List<Pokemon> pokemons) {
        super(context, 0, pokemons);
    }

    /**
     * Returns a list item view that displays information about the pokemon at the given position
     * in the list of pokemons.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if there is an existing list item view (called convertView) that we can reuse,
        // otherwise, if convertView is null, then inflate a new list item layout.
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.pokemon_item_list, parent, false);
        }

        // Find the pokemon at the given position in the list of pokemons
        Pokemon currentPokemon = getItem(position);

        // Find the TextView with view ID magnitude
        TextView name = (TextView) listItemView.findViewById(R.id.textView1);
        TextView height = (TextView) listItemView.findViewById(R.id.textView2);
        TextView type = (TextView) listItemView.findViewById(R.id.textView4);
        TextView weight = (TextView) listItemView.findViewById(R.id.textView5);
        TextView ability = (TextView) listItemView.findViewById(R.id.textView6);
        ImageView imageView = (ImageView) listItemView.findViewById(R.id.imageView);



        String namePoke = currentPokemon.getName();

        name.setText("Name: "+namePoke);

        String heightPoke = currentPokemon.getHeight();

        height.setText("Height: "+heightPoke+" ft");


        String weightPoke = currentPokemon.getWeight();

        weight.setText("Weight: "+weightPoke+" kg");

        String abilityPoke = currentPokemon.getAbility();

        ability.setText("Abilities: "+abilityPoke);

        String typePoke = currentPokemon.getType();

        type.setText("Type: "+typePoke);

        String url = currentPokemon.getmUrl();

        Picasso.with(getContext()).load(url).resize(600,600).into(imageView);







        return listItemView;
    }



}
