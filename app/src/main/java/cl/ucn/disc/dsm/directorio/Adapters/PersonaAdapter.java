package cl.ucn.disc.dsm.directorio.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.internal.LinkedTreeMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


import cl.ucn.disc.dsm.directorio.Models.Persona;
import cl.ucn.disc.dsm.directorio.R;
import cl.ucn.disc.dsm.directorio.activities.MainActivity;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

/**
 * Adaptador de Personas
 */
@Slf4j
public final class PersonaAdapter extends BaseAdapter implements Filterable {

    /**
     * Filtro de busqueda
     */
    private CustomFilter filter;

    /**
     * Lista de personas que cumplen condicion de busqueda
     */
    private List <Persona> filterList;
    /**
     * Lista de personas que manejara este adaptador.
     */
    private List <Persona> personas;

    /**
     * Infla las vistas.
     */
    private LayoutInflater inflater;

    /**
     * Constructor del adaptador.
     *
     * @param context  La instancia del activity.
     * @param personas La lista de personas que se quiere manejar.
     */
    public PersonaAdapter(Context context, List<Persona> personas) {
        this.filterList=personas;
        inflater = LayoutInflater.from(context);
        this.personas = personas;
    }

    @Override
    public int getCount() {
        return personas.size();
    }

    @Override
    public Persona getItem(int position) {
        return personas.get(position);
    }


    @Override
    public long getItemId(int position) {
        return personas.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Obtener a la persona.
        final Persona persona = getItem(position);

        PersonaViewHolder holder;

        // Si la vista es nula, se infla
        // Si no, se vuelve a usar.

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.row_persona, parent, false);

            holder = new PersonaViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (PersonaViewHolder) convertView.getTag();
        }

        // textViews obtienen los datos disponibles
        // Si no hay dato, ocultar.

        holder.nombre.setText(persona.getNombre().equals("null") ? "Sin Nombre" : persona.getNombre());
        holder.cargo.setText(persona.getCargo().equals("null") ? "" : persona.getCargo());
        holder.unidad.setText(persona.getUnidad().equals("null") ? "" : persona.getUnidad());
        holder.oficina.setText(persona.getOficina().equals("null") ? "" : persona.getOficina());
        holder.email.setText(persona.getEmail().equals("null") ? "" : persona.getEmail());
        holder.telefono.setText(persona.getTelefono().equals("null") ? "" : persona.getTelefono());

        // Devolver la vista.
        return convertView;
    }

    private static class PersonaViewHolder {
        TextView nombre;
        TextView cargo;
        TextView unidad;
        TextView oficina;
        TextView email;
        TextView telefono;

        /**
         * @param view
         */
        PersonaViewHolder(View view) {
            this.nombre = view.findViewById(R.id.rp_tv_nombre);
            this.cargo = view.findViewById(R.id.rp_tv_cargo);
            this.unidad = view.findViewById(R.id.rp_tv_unidad);
            this.oficina = view.findViewById(R.id.rp_tv_oficina);
            this.email = view.findViewById(R.id.rp_tv_email);
            this.telefono = view.findViewById(R.id.rp_tv_telefono);
        }
    }


    public Filter getFilter() {

        if(filter == null)
        {
            filter=new CustomFilter();
        }

        return filter;
    }

    /**
     * Clase interna
     */
    class CustomFilter extends Filter
    {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            FilterResults results=new FilterResults();

            // buscar cuando se agregan letras
            if(constraint != null && constraint.length()>0)
            {

                //CONSTRAINT TO UPPER
                constraint=constraint.toString().toUpperCase();

                ArrayList<Persona> filters= new ArrayList<>();

                //obtencion de resultado especifico
                for(int i=0;i<filterList.size();i++)
                {
                    if(filterList.get(i).getNombre().toUpperCase().contains(constraint))
                    {
                        Persona p=new Persona(filterList.get(i).getId(), filterList.get(i).getNombre(), filterList.get(i).getCargo(),filterList.get(i).getUnidad(), filterList.get(i).getEmail(), filterList.get(i).getTelefono(), filterList.get(i).getOficina());

                        filters.add(p);
                    }
                }

                results.count=filters.size();
                results.values=filters;

            }else
            {
                //si la busqueda esta vacia, mostrar todas las personas
                results.count=filterList.size();
                results.values=filterList;

            }

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

            personas = (List<Persona>) results.values;

            // se notifica que los datos cambiaron
            notifyDataSetChanged();
        }

    }
}
