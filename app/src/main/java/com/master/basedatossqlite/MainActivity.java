package com.master.basedatossqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
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

        //Recuperamos el valor con el que se buscara nuestros valores
        String codigo = TXTcodigo.getText().toString();

        //Creamos una validacion para que el usuario no deje este campo vacio
        //Al momento de buscar el producto
        if (!codigo.isEmpty()){
            Cursor fila = BaseDatos.rawQuery("Select descripcion, precio from Articulos where codigo =" + codigo, null);

            // Creamos una estructura condicional que nos retorne si es verdadera, si esta encuentra
            //Datos dentro de nuestra tabla
            if (fila.moveToFirst()){
                TXTdescripcion.setText(fila.getString(0));
                TXTprecio.setText(fila.getString(1));

                //Cerramos nuestra base de datos
                BaseDatos.close();
            }else{
                Toast.makeText(this, "No existe el articulo", Toast.LENGTH_SHORT).show();

                //Volve,os a cerrar nuestra base de datos ya que en caso de que
                //No se cumpla la condicio se ejecuta esta otra por lo tanto
                // si no la cerramos quedaria abierta
                BaseDatos.close();
            }
        }else {
            Toast.makeText(this, "Debes ingresar el codigo del articulo", Toast.LENGTH_SHORT).show();
        }

    }

    //Este es el metodo para eliminar productos o articulos

    public void Eliminar(View view){
        AdminSQLiteOpenHelper admi = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase BaseDatos = admi.getWritableDatabase();

        // Recuperamos el valor o el dato con el que identificaremos el producto
        // O articulo que se desea eliminar de la base de datos

        String codigo = TXTcodigo.getText().toString();

        //Validamos el campo
        if(!codigo.isEmpty()){
            int cantidad = BaseDatos.delete("Articulos", "codigo= " + codigo, null);

            //Cerramos nuestra base de datos
            BaseDatos.close();

            //Limpiamos los campos

            TXTcodigo.setText("");
            TXTdescripcion.setText("");
            TXTprecio.setText("");

            // Creamos otra estructura condicional para poder agregar los
            //  Dos mensajes
            if(cantidad == 1){
                Toast.makeText(this, "El articulo eliminado exitosamente", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "El articulo no existe", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(this, "Debes ingresar el codigo del articulo", Toast.LENGTH_SHORT).show();
        }
    }

    // Creamos el metodo modificar

    public void Modificar(View view){
        AdminSQLiteOpenHelper admi = new  AdminSQLiteOpenHelper(this, "administracion", null,  1);
        SQLiteDatabase BaseDatos = admi.getWritableDatabase();

        //Creamos tres variables donde se guardara los que el usuario haya modificado

        String codigo = TXTcodigo.getText().toString();
        String descripcion = TXTdescripcion.getText().toString();
        String precio = TXTprecio.getText().toString();

        //creamos una estructura condicional para validar los compos

        // le decimos que si la condicion es completamente diferente a vacio si la condicion se cumple
        if(!codigo.isEmpty() && !descripcion.isEmpty() && !precio.isEmpty()){

            //creamos un objeto de la clase contentvalues
            ContentValues registro = new ContentValues();

            //ahora guardaremos dentro de nuestro registro las modificaciones que el usuario a realizado
            registro.put("codigo", codigo);
            registro.put("descripcion", descripcion);
            registro.put("precio", precio);

            // ahora los guardaremos dentro de nuestra base de datos

            int cantidad = BaseDatos.update("Articulos", registro, "codigo=" + codigo, null);

            //cerramos nuestra base de datos
            BaseDatos.close();

            //Creamos una estructura condicional para indicarle al usuario que el articulo se modificio
            // o el articulo que quiere o trata de moficiar no ixiste

            if(cantidad == 1){
                Toast.makeText(this, "Articulo modificado exitosamente", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "El articulo no existe", Toast.LENGTH_SHORT).show();
            }

        }else{
            Toast.makeText(this, "Debes llenar todos los campos", Toast.LENGTH_SHORT).show();
        }
    }


}