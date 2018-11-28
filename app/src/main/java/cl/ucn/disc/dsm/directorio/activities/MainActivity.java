/*
 * Copyright (c) 2018.  Fernando Caimanque <fernando.caimanque@alumnos.ucn.cl>
 * This work is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 * http://creativecommons.org/licenses/by-nc/4.0/
 *
 */

package cl.ucn.disc.dsm.directorio.activities;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;

import org.json.JSONArray;

import java.io.InputStream;
import java.util.ArrayList;

import cl.ucn.disc.dsm.directorio.Adapters.PersonaAdapter;
import cl.ucn.disc.dsm.directorio.Models.Persona;
import cl.ucn.disc.dsm.directorio.R;

/**
 * Activity principal.
 *
 * @author Fernando Caimanque
 */
public class MainActivity extends AppCompatActivity {

    static {
        AppCompatDelegate.setDefaultNightMode(
                AppCompatDelegate.MODE_NIGHT_AUTO);
    }

    /**
     * Adaptador vuelve a Persona en View
     */
    private PersonaAdapter adapter;

    /**
     * Barra de busqueda
     */
    SearchView barraDeBusqueda;


    ArrayList<Persona> listaContactos;
    /**
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Toolbar
        Toolbar toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

        // Se obtienen las personas desde el json
        listaContactos = Persona.readFile(loadJSONFromAsset());

        // Se crea el adapter
        if (this.adapter == null)
            this.adapter = new PersonaAdapter(this, listaContactos);

        /**
         * ListView que muestra las personas
         */
        ListView listView = findViewById(android.R.id.list);
        listView.setAdapter(adapter);

        // El buscador que esta sobre la ListView
        barraDeBusqueda = findViewById(R.id.busqueda);

        // Queda "escuchando"
        barraDeBusqueda.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                // Busca en el list
                adapter.getFilter().filter(newText);

                return false;
            }

        });

    }


    /**
     * Carga el json desde assets y retorna un arreglo de jsons.
     * @return Arreglo de archivos json..
     */
    private JSONArray loadJSONFromAsset(){
        String s;
        InputStream is;
        JSONArray jsonArray = null;
        try {
            is = getAssets().open("ucn.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            s = new String(buffer, "UTF-8");
            jsonArray = new JSONArray(s);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return jsonArray;
    }
}