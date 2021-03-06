package com.example.aplicacion1

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Vibrator
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_main)

        //Si el usuario ya se metio una vez y se registro, debemos invocar el archivo preferenciascompartidas
        //y buscar el nombre de usuario y si existe, ocultamos el boton de empezar ahora(porque ya no hay necesidad de registrarse)
        //aqui vamos a la inversa, leeremos el archivo de sharedpreferences
        val preferencias=applicationContext?.getSharedPreferences("AMIGOS",Context.MODE_PRIVATE)?:return
        //para leer informacion se hace directamente con el objeto preferencias y no con el edit que es para guardar
        with(preferencias){
           val valorNombre=  getString("nombre",null)
            if(!valorNombre.isNullOrEmpty()){
                //Esta condicion si se cumple quiere decir que ya te registraste, que tiene el nombr
                    //y ademas que ya no tiene que estar visible el boton de registro
                Toast.makeText(applicationContext, "Bienvenido de nuevo ${valorNombre}", Toast.LENGTH_LONG).show()
                //Ocultamos el boton de registro
                empezar.visibility= View.INVISIBLE
            }
        }

        //Agregar vibracion
        var v= getSystemService(VIBRATOR_SERVICE) as Vibrator
        //Invocamos el vibrador
       v.vibrate(3000)
        //invocamos el boton localizar por su id con plugin de extensiones
        localizar.setOnClickListener {
            startActivity(Intent(this, MapitaActivity::class.java))
        }
        /*Empezamos a programar para ver la forma de implementar los eventos de boton
          en android studio:
          */
        var botonEmpezar=   findViewById<Button>(R.id.empezar)

        //Manejamos el evento
        botonEmpezar.setOnClickListener {
            //Antes de la navegacion hacia la activity registro vamos a invocar a una componente
            // qu se llama Toast: estos son mensajes de corta duracion que se mustran en la pantalla
            Toast.makeText(applicationContext," Vamos a registrarnos!!", Toast.LENGTH_LONG).show()
            //El siguiente renglon nos lleva de esta acivity a la de registro:

            //La redireccionamos
            startActivity(Intent(this, RegistroActivity::class.java))
        }

        //declaracion de una variable en kotlin
        var x=4
        var y="Hola mundo!!"
        //En java int x=4; o bien,String y="Hola mundo!!"
        //LO siguiete tambien es correcto SIEMP Y CUANDO NO LAS QUIERAS INICIALIZAR
        var z:Int
        var w:String
        //EN KOTLIN TODAS LAS VARRIABLES SON OBJETOS, YA NO EXISTEN LOS TIPOS DE DATOS
        //PRIMITIVOS: float, double, byte, short, int, long, boolean
        var a:Byte
        var b:Short
        var c:Int
        var d:Float
        var e:Double
        var f:Boolean

        //Vamos a imprimir el el logcat para ello usamos la lase Log
        Log.i("MALO", "Este es mi primer mensaje con etiquete en info")

        //Vamos a concatenar una variable EN KOTLIN
        var mensajito=" vamos a concataner"
        Log.i("MALO","En kotlin"+mensajito+ " mas facil "+" Que Java" )
// La version de concatenado de kotlin es muuuucho mejor!!
        Log.i("MALO","En kotlin $mensajito  mas facil Que Java" )
        //Ademas la interpolacion  reloaded
        Log.i("MALO", " Vamos a interpolar una expresion ${5+3} que puede ser una operacion ")
        //En kotlin las funciones son lo que en java los metodos y tienen tambien mucho mas poder
        //porque pueden anidarse y son tratadas como OBJETOS. a ESTO SE LE DENOMINA PROGRAMACION
        //FUNCIONAL: UNA FUNCION ES TRATADA COMO CUALQUIER TIPO DE VARIBLE (OBJETO)
        // ES DECIR, UNA FUNCION PUEDE DEVOLVERLES OTRA FUNCION!!!!!!!!!

//Invocamos o mandamos llamar nuestra funcioncita
        saludar()
        //Aqui estamos dentro del ambito de el metodo onCreate (es decir su cuerpo), aun asi
        // kotlin permite aqui dentro implementar una funcion, cosa que no es posible en Java
        fun mensajito(){
            Log.i("MALO", " iMPLEMENTANDO UNA FUNCION DENTRO DE OTRA!!!")
        }

//AQUI INVOCAMOS LA FUNCION ANTERIOR
        mensajito()

        //Funciones con tipo de retorno
        fun sumar():Int{
            var x=5+4
            return x
        }

//La invocamos
        Log.i("MALO", "iNVOCAMOS LA FUNCIO ${sumar()} con el interpolador de expresiones")

        //Otra modalidad de una funcion es la siguiente, que recibe argumentos:
        fun saludar2(mensaje:String){
            Log.i("MALO", mensaje)
        }
        //La invocamos
        saludar2("Este mensajito es el argumento de la funcion")

        //Kotlin es un lenguaje orientado a objetos, es funcional y tambien es reactivo
        fun saludar2(nombre:String, edad:Int){
            Log.i("MALO", " Tu nombre es $nombre y tu edad es $edad")
        }
        //Invocamos la funcion anterior  con sus argumentos
        saludar2("Andrea Lopez", 20)

    }

    //AQUI DECLARAMOS UNA FUNCINCITA
    fun  saludar(){
        Log.i("MALO", "Implementando mi primer funcion en kotlin")

    }
}