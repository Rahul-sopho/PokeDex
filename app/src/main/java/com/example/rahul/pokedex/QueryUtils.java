package com.example.rahul.pokedex;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rahul on 07-07-2017.
 */

public class QueryUtils {

    private static final String LOG_TAG = QueryUtils.class.getSimpleName();

    private QueryUtils() {
    }

    /**
     * Query the USGS dataset and return a list of {@link Pokemon} objects.
     */
    public static List<Pokemon> fetchPokemonData(String requestUrl) {
        // Create URL object
        URL url = createUrl(requestUrl);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }

        // Extract relevant fields from the JSON response and create a list of {@link Pokemon}s
        List<Pokemon> pokemons = extractFeatureFromJson(jsonResponse);

        // Return the list of {@link Pokemon}s
        return pokemons;
    }

    /**
     * Returns new URL object from the given string URL.
     */
    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Problem building the URL ", e);
        }
        return url;
    }

    /**
     * Make an HTTP request to the given URL and return a String as the response.
     */
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the pokemon JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                // Closing the input stream could throw an IOException, which is why
                // the makeHttpRequest(URL url) method signature specifies than an IOException
                // could be thrown.
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    /**
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server.
     */
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    /**
     * Return a list of {@link Pokemon} objects that has been built up from
     * parsing the given JSON response.
     */
    private static List<Pokemon> extractFeatureFromJson(String pokemonJSON) {
        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(pokemonJSON)) {
            return null;
        }

        // Create an empty ArrayList that we can start adding pokemons to
        List<Pokemon> pokemons = new ArrayList<>();

        // Try to parse the JSON response string. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {

            // Create a JSONObject from the JSON response string
            JSONObject baseJsonResponse = new JSONObject(pokemonJSON);

            String name = baseJsonResponse.getString("name");

            String weight = baseJsonResponse.getString("weight");

            String height = baseJsonResponse.getString("height");

            JSONArray abilityPoke = baseJsonResponse.getJSONArray("abilities");

            JSONObject abilityPoke1 = abilityPoke.getJSONObject(0);

            JSONObject abilityPoke2 = abilityPoke1.getJSONObject("ability");

            JSONObject urlPokemon = baseJsonResponse.getJSONObject("sprites");




                String urlImg = urlPokemon.getString("front_default");

                String ability = abilityPoke2.getString("name");


            JSONArray typePoke = baseJsonResponse.getJSONArray("types");

            String type = "";

            for(int i=0;i< typePoke.length();i++)
            {
                JSONObject typePoke1 = typePoke.getJSONObject(i);
                JSONObject typePoke2 = typePoke1.getJSONObject("type");

                if(i!=typePoke.length()-1)
                    type=type+typePoke2.getString("name")+",";
                else
                    type=type+typePoke2.getString("name");


            }




            // Extract the JSONArray associated with the key called "features",
            // which represents a list of features (or pokemons).



                Pokemon pokemon = new Pokemon(name, height, urlImg,type,weight,ability);

                // Add the new {@link Pokemon} to the list of pokemons.
                pokemons.add(pokemon);


        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the pokemon JSON results", e);
        }

        // Return the list of pokemons
        return pokemons;
    }




}
