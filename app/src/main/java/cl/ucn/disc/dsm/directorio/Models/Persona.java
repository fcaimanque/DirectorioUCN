package cl.ucn.disc.dsm.directorio.Models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Collections;

import lombok.Builder;
import lombok.Getter;

@Builder
public final class Persona {
    /**
     *
     * @param id
     * @param nombre
     * @param cargo
     * @param unidad
     * @param email
     * @param telefono
     * @param oficina
     */
    public Persona(int id, String nombre, String cargo, String unidad, String email, String telefono, String oficina) {
        this.id = id;
        this.nombre = nombre;
        this.cargo = cargo;
        this.unidad = unidad;
        this.email = email;
        this.telefono = telefono;
        this.oficina = oficina;
    }



    /**
     * Crea una lista de personas dado un array de jsons.
     * @param array
     * @return
     */
    public static ArrayList<Persona> readFile(JSONArray array) {
        ArrayList<Persona> personas = new ArrayList<>();
        for (int i = 0; i < array.length(); i++) {
            try {
                personas.add(new Persona(array.getJSONObject(i)));
                Collections.sort(personas, (p1, p2) -> p1.getNombre().compareToIgnoreCase(p2.getNombre()));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return personas;
    }

    /**
     * Recibe JSON para crear Persona
     * @param obj
     */
    public Persona(JSONObject obj) {
        try {
            this.id = obj.getInt("id");
            this.nombre = obj.getString("nombre");
            this.cargo = obj.getString("cargo");
            this.unidad = obj.getString("unidad");
            this.email = obj.getString("email");
            this.telefono = obj.getString("telefono");
            this.oficina = obj.getString("oficina");

        } catch (JSONException exception) {
            exception.printStackTrace();
        }
    }

    /**
     * El ID de la persona en el archivo.
     */
    @Getter
    private int id;

    /**
     * El nombre de la persona.
     */
    @Getter
    private String nombre;

    /**
     * El cargo de la persona.
     */
    @Getter
    private String cargo;

    /**
     * La unidad en la que trabaja la persona.
     */
    @Getter
    private String unidad;

    /**
     * El email de la persona.
     */
    @Getter
    private String email;

    /**
     * El telefono de la persona.
     */
    @Getter
    private String telefono;

    /**
     * La oficina en donde esta la persona.
     */
    @Getter
    private String oficina;
}

