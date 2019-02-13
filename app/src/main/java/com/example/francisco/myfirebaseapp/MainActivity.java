package com.example.francisco.myfirebaseapp;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.francisco.myfirebaseapp.Models.Persona;
import com.google.android.gms.common.internal.Objects;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    EditText nombre, apellido, correo, password;
    ListView listView;

    private List<Persona> listaPersona = new ArrayList<Persona>();
    ArrayAdapter<Persona> arrayAdapter;


    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    Persona personaSeleccionada;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nombre = (EditText)findViewById(R.id.txtNombrePersona);
        apellido = (EditText)findViewById(R.id.txtApellido);
        correo = (EditText)findViewById(R.id.txtCorreo);
        password = (EditText)findViewById(R.id.txtPassword);

        listView = (ListView)findViewById(R.id.lv_personas);

        inicializarFirebase();
        listarDatos();


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                personaSeleccionada = (Persona) adapterView.getItemAtPosition(i);
                nombre.setText(personaSeleccionada.getNombre());
                apellido.setText(personaSeleccionada.getApellido());
                correo.setText(personaSeleccionada.getCorreo());
                password.setText(personaSeleccionada.getPassword());
            }
        });


    }

    private void listarDatos() {
        databaseReference.child("Persona").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listaPersona.clear();
                for(DataSnapshot objSnapshot : dataSnapshot.getChildren()){
                    Persona p = objSnapshot.getValue(Persona.class);
                    listaPersona.add(p);

                    arrayAdapter = new ArrayAdapter<Persona>(MainActivity.this,android.R.layout.simple_list_item_1, listaPersona);
                    listView.setAdapter(arrayAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void inicializarFirebase() {
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        //firebaseDatabase.setPersistenceEnabled(true); // persistencia de datos
        databaseReference = firebaseDatabase.getReference();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        String nom = nombre.getText().toString();
        String mail = correo.getText().toString();
        String pass = password.getText().toString();
        String app = apellido.getText().toString();

        switch (item.getItemId()){
            case R.id.icon_add:
                if(nom.equals("")){
                    validacion();

                }
                else {
                    Persona p = new Persona();
                    p.setUid(UUID.randomUUID().toString());
                    p.setNombre(nom);
                    p.setApellido(app);
                    p.setCorreo(mail);
                    p.setPassword(pass);

                    databaseReference.child("Persona").child(p.getUid()).setValue(p);
                    Toast.makeText(this,"Agregar",Toast.LENGTH_SHORT).show();
                    limpiarDatos();


                }
                break;
            case R.id.icon_save:
                Persona pe = new Persona();
                pe.setUid(personaSeleccionada.getUid());
                pe.setNombre(nombre.getText().toString().trim());
                pe.setApellido(apellido.getText().toString().trim());
                pe.setCorreo(correo.getText().toString().trim());
                pe.setPassword(password.getText().toString().trim());
                databaseReference.child("Persona").child(pe.getUid()).setValue(pe);
                Toast.makeText(this,"guardar",Toast.LENGTH_SHORT).show();
                break;
            case R.id.icon_delete:
                Persona p = new Persona();
                p.setUid(personaSeleccionada.getUid());
                databaseReference.child("Persona").child(p.getUid()).removeValue();
                Toast.makeText(this,"eliminar",Toast.LENGTH_SHORT).show();
                limpiarDatos();
                break;
        }


        return true;


    }

    private void limpiarDatos() {
        nombre.setText("");
        apellido.setText("");
        password.setText("");
        correo.setText("");

    }

    private void validacion() {
        String nom = nombre.getText().toString();
        if(nom.equals("")){
            nombre.setError("Debe ingresar nombre");
        }
    }
}
