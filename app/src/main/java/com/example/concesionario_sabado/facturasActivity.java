package com.example.concesionario_sabado;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class facturasActivity extends AppCompatActivity {

    EditText jetcodigofactura, jetfecha, jetidcliente, jetplaca;
    TextView jtvnombrecliente, jtvtelefonocliente, jtvmarcavehiculo, jtvvalorvehiculo;
    CheckBox jcbactivo;
    ClsOpenHelper admin = new ClsOpenHelper(this, "Concesionario2.db", null, 1);
    String factura, fecha, idcliente, placa;
    Button jbtadicionar, jbtanular, jbtlimpiar;
    long respuesta;
    boolean sw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facturas);
        jetcodigofactura = findViewById(R.id.etcodigofactura);
        jetfecha = findViewById(R.id.etfechafactura);
        jetidcliente = findViewById(R.id.etidcliente);
        jetplaca = findViewById(R.id.etplacavehiculo);
        jtvnombrecliente = findViewById(R.id.tvnombre);
        jtvtelefonocliente = findViewById(R.id.tvtelefono);
        jtvmarcavehiculo = findViewById(R.id.tvmarca);
        jtvvalorvehiculo = findViewById(R.id.tvvalor);
        jcbactivo = findViewById(R.id.cbactivo);
        jbtadicionar = findViewById(R.id.btadicionar);
        jbtanular = findViewById(R.id.btanular);
        jbtlimpiar = findViewById(R.id.btlimpiarcampos);
        sw = false;
        jetcodigofactura.requestFocus();
        Toast.makeText(this, "Digite la placa para consultar", Toast.LENGTH_SHORT).show();
    }//Fin Oncreate

    public void ConsultarPlaca(View view) {
        placa = jetplaca.getText().toString();
        //Validar que la identificacion se digito
        if (!placa.isEmpty()) {
            SQLiteDatabase db = admin.getReadableDatabase();
            Cursor registro = db.rawQuery("select * from TblVehiculo where Placa='" + placa + "'", null);
            if (registro.moveToNext()) {
                sw = true;
                jbtanular.setEnabled(true);
                jtvmarcavehiculo.setText(registro.getString(1));
                jtvvalorvehiculo.setText(registro.getString(3));
                if (registro.getString(4).equals("Si")) {
                    jcbactivo.setChecked(true);
                } else {
                    jcbactivo.setChecked(false);
                }

            } else {
                jcbactivo.setChecked(true);
                Toast.makeText(this, "vehiculo no registrado", Toast.LENGTH_SHORT).show();
            }
            jetplaca.setEnabled(false);


        }
    }//Fin metodo consultar placa


    public void ConsultarCLiente(View view) {
        idcliente = jetidcliente.getText().toString();
        if (!idcliente.isEmpty()) {
            SQLiteDatabase db = admin.getReadableDatabase();
            Cursor registro = db.rawQuery("select * from TblCliente where Ident_cliente='" + idcliente + "'", null);
            if (registro.moveToNext()) {
                sw = true;
                jbtanular.setEnabled(true);
                jtvnombrecliente.setText(registro.getString(1));
                jtvtelefonocliente.setText(registro.getString(3));


            } else {
                jcbactivo.setChecked(true);
                Toast.makeText(this, "Cliente no registrado", Toast.LENGTH_SHORT).show();
            }
            jetidcliente.setEnabled(false);
            db.close();
        }else{
            Toast.makeText(this, "La identificacion es requerida", Toast.LENGTH_SHORT).show();
            jetidcliente.requestFocus();
        }

    }//Fin metodo consultar cliente

     public void LimpiarCampos(View view){
         jetidcliente.setText("");
         jtvnombrecliente.setText("");
         jtvtelefonocliente.setText("");
         jetplaca.setText("");
         jtvvalorvehiculo.setText("");
         jcbactivo.setChecked(false);
         sw=false;
     }

    public void Regresar(View view){
        Intent intmain = new Intent(this, MainActivity.class);
        startActivity(intmain);
    }





}