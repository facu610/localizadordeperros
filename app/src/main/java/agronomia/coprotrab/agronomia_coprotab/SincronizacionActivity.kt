package agronomia.coprotrab.agronomia_coprotab

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import androidx.core.content.ContextCompat
import androidx.appcompat.widget.Toolbar
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.google.gson.Gson
import android.widget.Toast
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.replace
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.indeterminateProgressDialog
import org.jetbrains.anko.longToast
import org.jetbrains.anko.uiThread
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*
import com.github.kittinunf.fuel.android.extension.responseJson
import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.httpPost
import org.jetbrains.anko.db.TEXT
import org.jetbrains.anko.db.delete

class SincronizacionActivity : AppCompatActivity() {

    var toolbar: Toolbar? = null



    @SuppressLint("ResourceType")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sincronizacion)


        toolbar = findViewById(R.id.tb_act_sincro)
        toolbar?.title = "Sincronización"
        toolbar?.setTitleMargin(10, 10, 10, 10)
        setSupportActionBar(toolbar)

        val date = getCurrentDateTime()
        val dateInString = date.toString("yyyy/MM/dd HH:mm:ss")

        val id_instr: Int = SharedApp.prefs.id.toInt()
        FuelManager.instance.basePath = "http://demosmushtaq.16mb.com"

        val tv_registro_sincro = findViewById<TextView>(R.id.tv_Registro_Sincro)
        val bPruebaConex = findViewById<Button>(R.id.b_PruebaConex)
        val bSincroComplementarios = findViewById<Button>(R.id.b_SincroComplementarios)


        bPruebaConex.setOnClickListener {
            val dialog =
                indeterminateProgressDialog(
                    message = "Por favor espere un momento…",
                    title = "Estableciendo conexión"
                )
            dialog.setCancelable(false)
            if (Network.isConecctedToCoop()) {
                bSincroComplementarios.visibility = View.VISIBLE

                dialog.dismiss()
                Toast.makeText(this, "Conexíon a con el Servidor estable.", Toast.LENGTH_LONG).show()
            } else {
                dialog.dismiss()
                Toast.makeText(this, "Compruebe su conexión a Internet.", Toast.LENGTH_LONG)
                    .show()
            }
        }

        bSincroComplementarios.setOnClickListener {
            val dialog = indeterminateProgressDialog(
                message = "Por favor espere un momento…",
                title = "Descargando localizaciones"
            )
            dialog.setCancelable(false)

            doAsync {
                val respuesta =
                    URL("http://198.199.89.202:8080/pets-api/api/ubicacion/hoy").readText()
                val gson = Gson()
                val ubicaciones = gson.fromJson(respuesta, Array<Ubicacion_API>::class.java)
                DataAccess_RegistroAgrotecnico_App(applicationContext).delete_Ubicaciones_APIs()
                database.use {
                    ubicaciones.forEach {
                        insert(
                            "Ubicacion_APITable",
                            "Id" to it.Id,
                            "Latitud" to it.Latitud,
                            "Longitud" to it.Longitud,
                            "FechaHora" to it.FechaHora
                        )
                    }
                }
                dialog.dismiss()
                uiThread { longToast("Ubicaciones de mascotas sincronizadas") }
//                uiThread { longToast(ubicaciones[0].Latitud.toString())
//                    uiThread { longToast(ubicaciones[0].Longitud.toString())
            }


        }

    }

    fun Date.toString(format: String, locale: Locale = Locale.getDefault()): String {
        val formatter = SimpleDateFormat(format, locale)
        return formatter.format(this)
    }

    fun getCurrentDateTime(): Date {
        return Calendar.getInstance().time
    }}
