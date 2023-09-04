package com.example.concesionario_sabado;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class VehiculosActivity extends AppCompatActivity {
        EditText jetplaca, jetmodelo, jetvalor, jetmarca;
        Button jbtregistrar,jbtanular;
    CheckBox jcbactivo;
    ClsOpenHelper admin=new ClsOpenHelper(this,"Concesionario2.db",null,1);
    String placa, modelo , valor, marca;
    long respuesta;
    boolean sw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehiculos);
        jetplaca = findViewById(R.id.etplaca);
        jetmarca = findViewById(R.id.etmarca);
        jetmodelo = findViewById(R.id.etmodelo);
        jetvalor = findViewById(R.id.etvalor);
        jbtregistrar = findViewById(R.id.btregistrar);
        jbtanular = findViewById(R.id.btanular);
        jcbactivo = findViewById(R.id.cbactivo);
        sw= false;
        jetplaca.requestFocus();
        Toast.makeText(this, "Digite la placa para consultar", Toast.LENGTH_SHORT).show();


    }//Fin Oncreate

    public void Consultar(View view){
        placa = jetplaca.getText().toString();
        if (!placa.isEmpty()){
            SQLiteDatabase db = admin.getReadableDatabase();
            Cursor registro= db.rawQuery("select * from TblVehiculo where Placa='"+ placa +"'",null);
            if (registro.moveToNext()){
                sw= true;
                jbtanular.setEnabled(true);
                jetmarca.setText(registro.getString(1));
                jetmodelo.setText(registro.getString(2));
                jetvalor.setText(registro.getString(3));
                if (registro.getString(4).equals("Si")){
                    jcbactivo.setChecked(true);
                }else{
                    jcbactivo.setChecked(false);
                }

            }else{
                jcbactivo.setChecked(true);
                Toast.makeText(this, "vehiculo no registrado", Toast.LENGTH_SHORT).show();
            }
            jetplaca.setEnabled(false);
            jetmarca.setEnabled(true);
            jetmodelo.setEnabled(true);
            jetvalor.setEnabled(true);
            jbtregistrar.setEnabled(true);
            jetplaca.requestFocus();

        }else{
            Toast.makeText(this, "La placa es requerida", Toast.LENGTH_SHORT).show();
            jetplaca.requestFocus();
        }
    }//Fin metodo consultar

    public void Registrar(View view){
        //Validar que los campos no esten vacios
        placa = jetplaca.getText().toString();
        marca = jetmarca.getText().toString();
        modelo= jetmodelo.getText().toString();
        valor = jetvalor.getText().toString();
        if (placa.isEmpty()||marca.isEmpty()|| modelo.isEmpty()||valor.isEmpty()) {
            Toast.makeText(this, "Todos los datos son requeridos", Toast.LENGTH_SHORT).show();
            jetplaca.requestFocus();
        }else{
            SQLiteDatabase db = admin.getReadableDatabase();
            ContentValues fila = new ContentValues();
            //Llenar el contenedor
            fila.put("Placa",placa);
            fila.put("Marca",marca);
            fila.put("Modelo",modelo);
            fila.put("Valor",valor);
            if (sw == false) {
                respuesta=db.insert("TblVehiculo",null,fila);
            }else{
                sw=false;
                respuesta=db.update("TblVehiculo",fila,"Placa='"+placa+"'",null);
            }
            if (respuesta>0){
                Toast.makeText(this, "Registro Existoso", Toast.LENGTH_SHORT).show();
                LimpiarCampos();
            }else{
                Toast.makeText(this, "Error guardando registro", Toast.LENGTH_SHORT).show();
                db.close();
            }


        }
    }//Fin metodo guardar

    public void Anular(View view){
        SQLiteDatabase db=admin.getWritableDatabase();
        ContentValues fila=new ContentValues();
        fila.put("Activo","No");
        respuesta=db.update("TblVehiculo",fila,"Placa='"+placa+"'",null);
        if (respuesta > 0){
            Toast.makeText(this, "Registro anulado", Toast.LENGTH_SHORT).show();
            LimpiarCampos();
        }else
            Toast.makeText(this, "Error anulando registro", Toast.LENGTH_SHORT).show();
        db.close();
    }//fin metodo Anular

    public void Limpiar(View view){
        LimpiarCampos();
    }

    public void Regresar(View view){
        Intent intmain = new Intent(this, MainActivity.class);
        startActivity(intmain);
    }

    private  void LimpiarCampos(){
        jetplaca.setText("");
        jetmarca.setText("");
        jetmodelo.setText("");
        jetvalor.setText("");
        jcbactivo.setChecked(false);
        jetplaca.setEnabled(true);
        jetmarca.setEnabled(false);
        jetmodelo.setEnabled(false);
        jetvalor.setEnabled(false);
        jbtanular.setEnabled(false);
        jbtregistrar.setEnabled(false);
        sw=false;
    }


}