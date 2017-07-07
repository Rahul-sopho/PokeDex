package com.example.rahul.pokedex;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText pokeSearch;
    Button go;

    PokemonAdapter mAdapter;

    private static String poke_url = "http://pokeapi.co/api/v2/pokemon/";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pokeSearch = (EditText)findViewById(R.id.editText);
        go = (Button)findViewById(R.id.button);


        ListView pokemonListView = (ListView) findViewById(R.id.list);

        // Create a new adapter that takes an empty list of pokemons as input
        mAdapter = new PokemonAdapter(this, new ArrayList<Pokemon>());

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        pokemonListView.setAdapter(mAdapter);

        // Set an item click listener on the ListView, which sends an intent to a web browser
        // to open a website with more information about the selected pokemon.
        pokemonListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // Find the current pokemon that was clicked on
                Pokemon currentPokemon = mAdapter.getItem(position);

                // Convert the String URL into a URI object (to pass into the Intent constructor)
                Uri pokemonUri = Uri.parse(currentPokemon.getmUrl());

                // Create a new intent to view the pokemon URI
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, pokemonUri);

                // Send the intent to launch a new activity
                startActivity(websiteIntent);
            }
        });

        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(pokeSearch.getText().toString().length()>0)
                {
                    poke_url="http://pokeapi.co/api/v2/pokemon/"+pokeSearch.getText().toString().toLowerCase().trim()+"/";
                    // Start the AsyncTask to fetch the pokemon data
                    PokemonAsyncTask task = new PokemonAsyncTask();
                    task.execute(poke_url);
                }
            }
        });

    }
    private class PokemonAsyncTask extends AsyncTask<String, Void, List<Pokemon>> {

        /**
         * This method runs on a background thread and performs the network request.
         * We should not update the UI from a background thread, so we return a list of
         * {@link Pokemon}s as the result.
         */
        @Override
        protected List<Pokemon> doInBackground(String... urls) {
            // Don't perform the request if there are no URLs, or the first URL is null
            if (urls.length < 1 || urls[0] == null) {
                return null;
            }

            List<Pokemon> result = QueryUtils.fetchPokemonData(urls[0]);
            return result;
        }

        /**
         * This method runs on the main UI thread after the background work has been
         * completed. This method receives as input, the return value from the doInBackground()
         * method. First we clear out the adapter, to get rid of pokemon data from a previous
         * query to USGS. Then we update the adapter with the new list of pokemons,
         * which will trigger the ListView to re-populate its list items.
         */
        @Override
        protected void onPostExecute(List<Pokemon> data) {
            // Clear the adapter of previous pokemon data
            mAdapter.clear();

            // If there is a valid list of {@link Pokemon}s, then add them to the adapter's
            // data set. This will trigger the ListView to update.
            if (data != null && !data.isEmpty()) {
                mAdapter.addAll(data);
            }
        }
    }
}
