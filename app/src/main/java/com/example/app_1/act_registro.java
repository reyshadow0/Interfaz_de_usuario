package com.example.app_1;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.util.Patterns;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Calendar;
import java.util.Locale;

public class act_registro extends AppCompatActivity {

    private TextInputLayout tilCedula, tilNombre, tilFechaNacimiento, tilCiudad;
    private TextInputLayout tilCorreo, tilTelefono;
    private TextInputEditText tietCedula, tietNombres, tietFechaNacimiento, tietCiudad;
    private TextInputEditText tietCorreo, tietTelefono;
    private RadioGroup rgGenero;
    private TextView tvGeneroError;
    private MaterialButton btnLimpiar, btnEnviar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_act_registro);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initViews();
        setupUpperCaseFilters();
        setupDatePicker();
        setupButtons();
    }

    private void initViews() {
        tilCedula = findViewById(R.id.tilCedula);
        tilNombre = findViewById(R.id.tilNombre);
        tilFechaNacimiento = findViewById(R.id.tilFechaNacimiento);
        tilCiudad = findViewById(R.id.tilCiudad);
        tilCorreo = findViewById(R.id.tilCorreo);
        tilTelefono = findViewById(R.id.tilTelefono);

        tietCedula = findViewById(R.id.tietCedula);
        tietNombres = findViewById(R.id.tietNombres);
        tietFechaNacimiento = findViewById(R.id.tietFechaNacimiento);
        tietCiudad = findViewById(R.id.tietCiudad);
        tietCorreo = findViewById(R.id.tietCorreo);
        tietTelefono = findViewById(R.id.tietTelefono);

        rgGenero = findViewById(R.id.rgGenero);
        tvGeneroError = findViewById(R.id.tvGeneroError);
        btnLimpiar = findViewById(R.id.btnLimpiar);
        btnEnviar = findViewById(R.id.btnEnviar);
    }

    private void setupUpperCaseFilters() {
        InputFilter upperCaseFilter = (source, start, end, dest, dstart, dend) ->
                source.toString().toUpperCase(Locale.getDefault());

        tietNombres.setFilters(new InputFilter[]{
                new InputFilter.LengthFilter(500),
                upperCaseFilter
        });
        tietCiudad.setFilters(new InputFilter[]{
                new InputFilter.LengthFilter(200),
                upperCaseFilter
        });
    }

    private void setupDatePicker() {
        tietFechaNacimiento.setOnClickListener(v -> openDatePicker());
        tilFechaNacimiento.setEndIconOnClickListener(v -> openDatePicker());
    }

    private void openDatePicker() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(this,
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    String date = String.format(Locale.getDefault(),
                            "%02d/%02d/%04d", selectedDay, selectedMonth + 1, selectedYear);
                    tietFechaNacimiento.setText(date);
                    tilFechaNacimiento.setError(null);
                }, year, month, day);

        dialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        dialog.show();
    }

    private void setupButtons() {
        btnLimpiar.setOnClickListener(v -> limpiarFormulario());
        btnEnviar.setOnClickListener(v -> enviarFormulario());
    }

    private void limpiarFormulario() {
        tietCedula.setText("");
        tietNombres.setText("");
        tietFechaNacimiento.setText("");
        tietCiudad.setText("");
        tietCorreo.setText("");
        tietTelefono.setText("");
        rgGenero.clearCheck();

        tilCedula.setError(null);
        tilNombre.setError(null);
        tilFechaNacimiento.setError(null);
        tilCiudad.setError(null);
        tilCorreo.setError(null);
        tilTelefono.setError(null);
        tvGeneroError.setVisibility(View.GONE);
    }

    private void enviarFormulario() {
        String cedula = getText(tietCedula);
        String nombres = getText(tietNombres);
        String fechaNacimiento = getText(tietFechaNacimiento);
        String ciudad = getText(tietCiudad);
        String correo = getText(tietCorreo);
        String telefono = getText(tietTelefono);
        int generoId = rgGenero.getCheckedRadioButtonId();

        boolean valid = true;

        if (cedula.isEmpty()) {
            tilCedula.setError("La cédula es obligatoria");
            valid = false;
        } else if (cedula.length() != 10 || !cedula.matches("\\d+")) {
            tilCedula.setError("Debe tener exactamente 10 dígitos numéricos");
            valid = false;
        } else {
            tilCedula.setError(null);
        }

        if (nombres.isEmpty()) {
            tilNombre.setError("Los nombres son obligatorios");
            valid = false;
        } else {
            tilNombre.setError(null);
        }

        if (fechaNacimiento.isEmpty()) {
            tilFechaNacimiento.setError("Seleccione la fecha de nacimiento");
            valid = false;
        } else {
            tilFechaNacimiento.setError(null);
        }

        if (ciudad.isEmpty()) {
            tilCiudad.setError("La ciudad es obligatoria");
            valid = false;
        } else {
            tilCiudad.setError(null);
        }

        String genero = "";
        if (generoId == -1) {
            tvGeneroError.setText("Seleccione un género");
            tvGeneroError.setVisibility(View.VISIBLE);
            valid = false;
        } else {
            tvGeneroError.setVisibility(View.GONE);
            RadioButton rb = findViewById(generoId);
            genero = rb.getText().toString();
        }

        if (correo.isEmpty()) {
            tilCorreo.setError("El correo electrónico es obligatorio");
            valid = false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(correo).matches()) {
            tilCorreo.setError("Ingrese un correo electrónico válido");
            valid = false;
        } else {
            tilCorreo.setError(null);
        }

        if (telefono.isEmpty()) {
            tilTelefono.setError("El teléfono es obligatorio");
            valid = false;
        } else if (telefono.length() != 10 || !telefono.matches("\\d+")) {
            tilTelefono.setError("Debe tener exactamente 10 dígitos numéricos");
            valid = false;
        } else {
            tilTelefono.setError(null);
        }

        if (valid) {
            Intent intent = new Intent(this, ActDatosRegistro.class);
            intent.putExtra("cedula", cedula);
            intent.putExtra("nombres", nombres);
            intent.putExtra("fechaNacimiento", fechaNacimiento);
            intent.putExtra("ciudad", ciudad);
            intent.putExtra("genero", genero);
            intent.putExtra("correo", correo);
            intent.putExtra("telefono", telefono);
            startActivity(intent);
        }
    }

    private String getText(TextInputEditText field) {
        return field.getText() != null ? field.getText().toString().trim() : "";
    }
}
