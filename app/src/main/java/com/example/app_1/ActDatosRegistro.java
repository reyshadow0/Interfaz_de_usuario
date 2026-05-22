package com.example.app_1;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;

public class ActDatosRegistro extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_act_datos_registro);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        String cedula = getIntent().getStringExtra("cedula");
        String nombres = getIntent().getStringExtra("nombres");
        String fechaNacimiento = getIntent().getStringExtra("fechaNacimiento");
        String ciudad = getIntent().getStringExtra("ciudad");
        String genero = getIntent().getStringExtra("genero");
        String correo = getIntent().getStringExtra("correo");
        String telefono = getIntent().getStringExtra("telefono");

        ((TextView) findViewById(R.id.tvCedula)).setText(cedula);
        ((TextView) findViewById(R.id.tvNombres)).setText(nombres);
        ((TextView) findViewById(R.id.tvFechaNacimiento)).setText(fechaNacimiento);
        ((TextView) findViewById(R.id.tvCiudad)).setText(ciudad);
        ((TextView) findViewById(R.id.tvGenero)).setText(genero);
        ((TextView) findViewById(R.id.tvCorreo)).setText(correo);
        ((TextView) findViewById(R.id.tvTelefono)).setText(telefono);

        MaterialButton btnVolver = findViewById(R.id.btnVolver);
        btnVolver.setOnClickListener(v -> finish());
    }
}
