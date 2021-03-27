package com.example.aplicacion1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.example.aplicacion1.modelo.Usuario
import kotlinx.android.synthetic.main.activity_registro.*
import org.json.JSONObject
import android.content.Context
import android.graphics.Bitmap
import android.util.LruCache
import com.android.volley.RequestQueue
import com.android.volley.toolbox.ImageLoader
import com.android.volley.toolbox.Volley



class RegistroActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)

        //MAnejamos el evento del boton para pedir los valores de las componentes de nuestro formulario
        registrar.setOnClickListener {
            var usuario = Usuario()
            //Este usuario que se llama usuario , lo llenamos con valores de los ampos de texto del formulario
            //Los siguientes 3 renglones representan la capa presenter, une la vista con el modelo
            usuario.email = txtEmail.text.toString()
            usuario.nickname = txtNickname.text.toString()
            usuario.nombre = txtNombre.text.toString()

            //El siguiente paso es que este objeto (usuario) lo tenemos que enviar a un servidor externo para que
            //pueda ser guardado y registrado en cualquier red social
            //Para este paso necesitamos enviar la informacion  a un servidor y el mecanismo de envio
            //es una arquitectura muy particular que se denomina arquitectura estilo REST
            //en android existe una tecnologia que nos va a ayudar para poder enviar nuestro objeto de
            //registro al back-end. Esta tecnologia  se conoce como RETROFIT
//Generar objeto JSON
            var usuariojson = JSONObject()
            usuariojson.put("email", usuario.email)
            usuariojson.put("nickname", usuario.nickname)
            usuariojson.put("nombre", usuario.nombre)
            //Generar objeto tipo request donde se envia al back-end nuestro usuario
            var url = "https://benesuela.herokuapp.com/api/usuario"
            val jsonObjectRequest = JsonObjectRequest(Request.Method.POST, url, usuariojson,
                Response.Listener { response ->
//aqui vamos a guardar el objeto de sharedpreferences
                    val preferencias=applicationContext?.getSharedPreferences("AMIGOS", Context.MODE_PRIVATE)?:return@Listener
                    //El equivalente de abajo pero orientado a objeto
                   // preferencias?.edit()?.putString("nombre",usuario.nombre)?.commit()
                    //con notacion funcional lambda
                    with(preferencias.edit()){
                        putString("nombre",usuario.nombre).commit()
                       // putFloat("edad", 19.8f).commit()
                    }

                    //El mensaje
                    Toast.makeText(this, response.get("mensaje").toString(), Toast.LENGTH_LONG)
                        .show()
                },
                Response.ErrorListener { error ->
                    // TODO: Handle error
                    Toast.makeText(this, "Hubo un error, ${error}", Toast.LENGTH_LONG).show()
                }
            )

            // Acceso al request por medio de una clase Singleton
            MiSingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)


        }


    }

    class MiSingleton constructor(context: Context) {
        companion object {
            @Volatile
            private var INSTANCE: MiSingleton? = null
            fun getInstance(context: Context) =
                INSTANCE ?: synchronized(this) {
                    INSTANCE ?: MiSingleton(context).also {
                        INSTANCE = it
                    }
                }
        }

        //Para el caso d cargar un objeto como una imagen.
        val imageLoader: ImageLoader by lazy {
            ImageLoader(requestQueue,
                object : ImageLoader.ImageCache {
                    private val cache = LruCache<String, Bitmap>(20)
                    override fun getBitmap(url: String): Bitmap {
                        return cache.get(url)
                    }

                    override fun putBitmap(url: String, bitmap: Bitmap) {
                        cache.put(url, bitmap)
                    }
                })
        }
        val requestQueue: RequestQueue by lazy {
            // applicationContext es para evitar fuga de mmoria
            Volley.newRequestQueue(context.applicationContext)
        }

        fun <T> addToRequestQueue(req: Request<T>) {
            requestQueue.add(req)
        }
    }
}

