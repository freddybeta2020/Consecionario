package com.example.concesionario_sabado;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //getSupportActionBar().hide();
    }//fin metodo onCreate

    public void Clientes(View view){
        Intent intclientes=new Intent(this,ClientesActivity.class);
        startActivity(intclientes);
    } //fin metodo Clientes

    public void Vehiculos(View view){
        Intent intvehiculos=new Intent(this, VehiculosActivity.class);
        startActivity(intvehiculos);
    } //fin metodo Vehiculos

    public void Facturas(View view){
        Intent intfacturas=new Intent(this,facturasActivity.class);
        startActivity(intfacturas);
    } //fin metodo Facturas

    public void ListarClientes(View view){
        Intent intlistarclientes = new Intent(this, ListarClientesActivity.class);
        startActivity(intlistarclientes);
    }
}
