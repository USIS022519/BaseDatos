package com.master.basedatossqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.DashPathEffect;
import android.graphics.RegionIterator;
import android.os.Bundle;
import android.renderscript.Sampler;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText TXTcodigo, TXTdescripcion, TXTprecio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

     TXTcodigo = (EditText)findViewById(R.id.txt_codigo);
     TXTdescripcion = (EditText)findViewById(R.id.txt_descripcion);
     TXTprecio = (EditText)findViewById(R.id.txt_precio);

    }


    //Creamos un metodo para el boton Registrar Producto

    public void Registrar(View view){

        //Creamos un objeto de clase que creamos

        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);

        //Creamos un objeto de la clase SQLiteDataBase, para abrir nuestra BD en modo escritura y lectura
        //Pasos:
        //Primero: Llamamos la clase SQLiteDataBase
        //Segundo: definimos el nombre del objeto
        //Tercero: llamamos el objeto anterior (admin) y colocamos .getWritableDataBase

        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        //Crearemos variables donde se guardara lo que ingrese el usuario

        String codigo = TXTcodigo.getText().toString();
        String Descripcion = TXTdescripcion.getText().toString();
        String Precio = TXTprecio.getText().toString();

        //Validamos si el usuario ingreso los datos

        if(!codigo.isEmpty() && !Descripcion.isEmpty() && !Precio.isEmpty()){

            //Si ingreso los datos, los ingresamos a la BD
            //Creamos un objeto de ContentValues

            ContentValues registro = new ContentValues();

            //hacemos referencia del nombre del valor que queremos pasar a la BD

            registro.put("codigo",codigo);
            registro.put("Descripcion", Descripcion);
            registro.put("Precio", Precio);

            //Guardamos los valores en la Tabla que creamos
            //Primero colocamos el nombre de la BD
            //Segundo ponemos Insert + el nombre de la tabla + null + el nombre del metodo donde estan los valores (registro)

            BaseDeDatos.insert("Articulos", null, registro);

            //Cerramos la BD

            BaseDeDatos.close();

            //Limpiamos los campos donde escribio el usuario

            TXTprecio.setText("");
            TXTcodigo.setText("");
            TXTdescripcion.setText("");

            //Le indicamos que el registro de datos fue exitoso

            Toast.makeText(getApplicationContext(), "Registro Exitoso", Toast.LENGTH_SHORT).show();

        } else {

            Toast.makeText(getApplicationContext(),"Ingrese todos los datos",Toast.LENGTH_LONG).show();

        }

    }


    //Metodo para buscar producto

    public void Buscar(View view){

        //Creamos objeto de la clase que creamos

        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);

        //Creamos el objeto para abrir la Base de Datos

        SQLiteDatabase BaseDatos = admin.getWritableDatabase();

        

    }


}