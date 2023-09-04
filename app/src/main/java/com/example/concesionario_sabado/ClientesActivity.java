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

import com.example.concesionario_sabado.ClsOpenHelper;
import com.example.concesionario_sabado.MainActivity;
import com.example.concesionario_sabado.R;

public class ClientesActivity extends AppCompatActivity {

    EditText jetidentificacion,jetnombre,jetdireccion,jettelefono;
    Button jbtguardar,jbtanular;
    CheckBox jcbactivo;
    ClsOpenHelper admin=new ClsOpenHelper(this,"Concesionario2.db",null,1);
    String identificacion,nombre,direccion,telefono;
    long respuesta;
    boolean sw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clientes);
        //Asociar los objetos Java con los objetos Xml
        jetidentificacion=findViewById(R.id.etidentificacion);
        jetnombre=findViewById(R.id.etnombre);
        jetdireccion=findViewById(R.id.etdireccion);
        jettelefono=findViewById(R.id.ettelefono);
        jbtguardar=findViewById(R.id.btguardar);
        jbtanular=findViewById(R.id.btanular);
        jcbactivo=findViewById(R.id.cbactivo);
        sw=false;
        jetidentificacion.requestFocus();
        Toast.makeText(this, "Digite la identificacion y luego Consulte", Toast.LENGTH_SHORT).show();
    } // fin onCreate

    public void Consultar(View view){
        identificacion=jetidentificacion.getText().toString();
        //Validar que la identificacion se digito
        if (!identificacion.isEmpty()){
            SQLiteDatabase db=admin.getReadableDatabase();
            Cursor registro=db.rawQuery("select * from TblCliente where Ident_cliente='"+ identificacion +"'",null);
            if (registro.moveToNext()){
                sw=true;
                jbtanular.setEnabled(true);
                jetnombre.setText(registro.getString(1));
                jetdireccion.setText(registro.getString(2));
                jettelefono.setText(registro.getString(3));
                if (registro.getString(4).equals("Si"))
                    jcbactivo.setChecked(true);
                else
                    jcbactivo.setChecked(false);

            }else{
                jcbactivo.setChecked(true);
                Toast.makeText(this, "Cliente no registrado", Toast.LENGTH_SHORT).show();
            }
            jetnombre.setEnabled(true);
            jetdireccion.setEnabled(true);
            jettelefono.setEnabled(true);
            jbtguardar.setEnabled(true);
            jetidentificacion.setEnabled(false);
            jetnombre.requestFocus();

            db.close();
        }else{
            Toast.makeText(this, "La identificacion es requerida", Toast.LENGTH_SHORT).show();
            jetidentificacion.requestFocus();
        }
    }//fin metodo Consultar

    public void Guardar(View view){
        //Validar que los campos no esten vacios
        identificacion=jetidentificacion.getText().toString();
        nombre=jetnombre.getText().toString();
        direccion=jetdireccion.getText().toString();
        telefono=jettelefono.getText().toString();
        if (identificacion.isEmpty() || nombre.isEmpty() || direccion.isEmpty() || telefono.isEmpty()){
            Toast.makeText(this, "Todos los datos son requeridos", Toast.LENGTH_SHORT).show();
            jetidentificacion.requestFocus();
        }else{
            SQLiteDatabase db=admin.getWritableDatabase();
            ContentValues fila=new ContentValues();
            //Llenar el contenedor
            fila.put("Ident_cliente",identificacion);
            fila.put("Nom_cliente",nombre);
            fila.put("Dir_cliente",direccion);
            fila.put("Tel_cliente",telefono);
            if (sw == false)
                respuesta=db.insert("TblCliente",null,fila);
            else{
                sw=false;
                respuesta=db.update("TblCliente",fila,"Ident_cliente='"+identificacion+"'",null);
            }
            if (respuesta > 0){
                Toast.makeText(this, "Registro guardado", Toast.LENGTH_SHORT).show();
                Limpiar_campos();
            }else
                Toast.makeText(this, "Error guardando registro", Toast.LENGTH_SHORT).show();
            db.close();
        }
    } //fin del metodo Guardar

    public void Anular(View view){
        SQLiteDatabase db=admin.getWritableDatabase();
        ContentValues fila=new ContentValues();
        fila.put("Activo","No");
        respuesta=db.update("TblCliente",fila,"Ident_cliente='"+identificacion+"'",null);
        if (respuesta > 0){
            Toast.makeText(this, "Registro anulado", Toast.LENGTH_SHORT).show();
            Limpiar_campos();
        }else
            Toast.makeText(this, "Error anulando registro", Toast.LENGTH_SHORT).show();
        db.close();
    }//fin metodo Anular

    public void Limpiar(View view){
        Limpiar_campos();
    }

    public void Regresar(View view){
        Intent intmain=new Intent(this, MainActivity.class);
        startActivity(intmain);
    }//fin metodo regresar

    private void Limpiar_campos(){
        jetidentificacion.setText("");
        jetnombre.setText("");
        jetdireccion.setText("");
        jettelefono.setText("");
        jcbactivo.setChecked(false);
        jetidentificacion.setEnabled(true);
        jetidentificacion.requestFocus();
        jetnombre.setEnabled(false);
        jetdireccion.setEnabled(false);
        jettelefono.setEnabled(false);
        jbtguardar.setEnabled(false);
        jbtanular.setEnabled(false);
        sw=false;
    }

    public void Listar(View view){
        Intent intlistar=new Intent(this, ListarClientesActivity.class);
        startActivity(intlistar);
    }//fin metodo Listar

}
