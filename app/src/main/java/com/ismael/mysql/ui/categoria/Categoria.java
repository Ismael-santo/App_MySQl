package com.ismael.mysql.ui.categoria;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.textfield.TextInputLayout;
import com.ismael.mysql.MySingleton;
import com.ismael.mysql.R;
import com.ismael.mysql.Setting_VAR;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class Categoria extends Fragment implements View.OnClickListener {
    private TextInputLayout ti_idcategoria, ti_namecategoria;
    private EditText et_idcategoria, et_namecategoria;
    private Spinner sp_estado;
    private Button btnSave, btnNew;
    String datoSelect = "";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_categoria, container, false);
        ti_idcategoria = root.findViewById(R.id.ti_idcategoria);
        ti_namecategoria = root.findViewById(R.id.ti_namecategoria);
        et_idcategoria = root.findViewById(R.id.et_idcategoria);
        et_namecategoria = root.findViewById(R.id.et_namecategoria);
        sp_estado = root.findViewById(R.id.sp_estado);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.estadoCategoria, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_estado.setAdapter(adapter);
        sp_estado.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                if (sp_estado.getSelectedItemPosition() > 0) {
                    datoSelect = sp_estado.getSelectedItem().toString();
                } else {
                    datoSelect = "";
                }
                //Toast.makeText(getContext(), ""+datoSelect, Toast.LENGTH_SHORT).show();
 /*
 if(conta>=1 && sp_especialista.getSelectedItemPosition()>0){
 //Toast toast = Toast.makeText(getApplicationContext(), sp_especialista.getSelectedItem().toString(),
Toast.LENGTH_SHORT);
 //toast.setGravity(Gravity.CENTER, 0, 0);
 //toast.show();
 String dato_SP = sp_especialista.getSelectedItem().toString();
 String s[] = dato_SP.split("~");
 documento = s[0].trim();
 String nombres = s[1];
 }else{
 }
 conta++;
 */

            }


            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btnSave = root.findViewById(R.id.btnSave);
        btnNew = root.findViewById(R.id.btnNew);
        btnSave.setOnClickListener(this);
        btnNew.setOnClickListener(this);

        return root;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btnSave:
                //save-server(getContect(),);
                String id = et_idcategoria.getText().toString();
                String nombre = et_namecategoria.getText().toString();
                if (id.length() == 0) {
                    et_idcategoria.setError("Campo obligatorio");
                } else if (nombre.length() == 0) {
                    et_namecategoria.setError("Campo obligatorio");
                    //}else if(!datoSelect.isEmpty()){
                } else if (sp_estado.getSelectedItemPosition() > 0) {
                    //Acciones para guardar registro en la base de datos.
                    Toast.makeText(getContext(), "Bien...", Toast.LENGTH_SHORT).show();
                    save_server(getContext(), Integer.parseInt(id), nombre, Integer.parseInt(datoSelect));
                } else {
                    Toast.makeText(getContext(), "Debe de optar por un estado para la categoria", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.btnNew:
                new_categories();
                break;

            default:
        }
    }

    private void save_server(final Context context, final int id_categoria, final String nom_categoria, final int estado_categoria) {
        StringRequest request = new StringRequest(Request.Method.POST, Setting_VAR.URL_guardar_categoria, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject requestJSON = null;
                try {
                    requestJSON = new JSONObject(response.toString());
                    String estado = requestJSON.getString("estado");
                    String mensaje = requestJSON.getString("mensaje");
                    if (estado.equals("1")) {
                        Toast.makeText(context, mensaje, Toast.LENGTH_SHORT).show();
                        //Toast.makeText(context, "Registro almacenado en MySQL.", Toast.LENGTH_SHORT).show();
                    } else if (estado.equals("2")) {
                        Toast.makeText(context, "" + mensaje, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "No se puede guardar, \n" + "Intentelo mas tarde,", Toast.LENGTH_SHORT).show();
            }
        }) {
            protected Map<String, String> getParams() throws AuthFailureError {
                //En este m??todo se colocan o se setean los valores a recibir por el fichero *.php
                Map<String, String> map = new HashMap<>();
                map.put("Content-Type", "application/json; charset=utf-8");
                map.put("Accept", "application/json");
                map.put("id", String.valueOf(id_categoria));
                map.put("nombre", nom_categoria);
                map.put("estado", String.valueOf(estado_categoria));
                return map;
            }
        };
        MySingleton.getInstance(context).addToRequestQueue(request);
    }

    private void new_categories() {
        et_idcategoria.setText(null);
        et_namecategoria.setText(null);
        sp_estado.setSelection(0);
    }
}