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
import org.jetbrains.anko.db.delete

class SincronizacionActivity : AppCompatActivity() {

    var toolbar: Toolbar? = null
    var registro_Sincro: Registro_Sincronizaciones? = null

    var registrovisitas_ToSincro: ArrayList<AA_Registro_Visita>? = null

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
        val bSincroSoc = findViewById<Button>(R.id.b_SincroSocios)
        val bSincroVisitas = findViewById<Button>(R.id.b_SincroVisitas)


        registro_Sincro = DataAccess_RegistroAgrotecnico_App(this).select_RegistroSincronizaciones(id_instr)
        if (registro_Sincro != null) {
            tv_registro_sincro.text = "Última Sincronización: ${registro_Sincro!!.Fecha}"
        }

        registrovisitas_ToSincro = DataAccess_RegistroAgrotecnico_App(this).select_RegistroVisitaToSincro()
        tv_registro_sincro.text = "Visitas por Sincronizar: " + registrovisitas_ToSincro!!.count().toString()





        bPruebaConex.setOnClickListener {
            val dialog =
                indeterminateProgressDialog(message = "Por favor espere un momento…", title = "Estableciendo conexión")
            dialog.setCancelable(false)
            if (Network.isConecctedToCoop()) {
                bSincroComplementarios.visibility = View.VISIBLE
                bSincroSoc.visibility = View.VISIBLE
                bSincroVisitas.visibility = View.VISIBLE
                dialog.dismiss()
                Toast.makeText(this, "Conexíon a Coprotab estable.", Toast.LENGTH_LONG).show()
            } else {
                dialog.dismiss()
                Toast.makeText(this, "Compruebe su conexión con Coprotab.", Toast.LENGTH_LONG).show()
            }
        }

        bSincroComplementarios.setOnClickListener {
            val dialog = indeterminateProgressDialog(
                message = "Por favor espere un momento…",
                title = "Descargando complementarios"
            )
            dialog.setCancelable(false)

            doAsync {
                val respuesta = URL("http://198.199.89.202:8080/pets-api/api/ubicacion/hoy").readText()
                val gson = Gson()
                val ubicaciones = gson.fromJson(respuesta, Array<Ubicacion_API>::class.java)


                dialog.dismiss()
                //uiThread { longToast("Instructores Sincronizados")
                uiThread { longToast(ubicaciones[0].Latitud.toString())
                    uiThread { longToast(ubicaciones[0].Longitud.toString())


                    }
            }

        }
        }


        bSincroSoc.setOnClickListener {
            val dialog =
                indeterminateProgressDialog(message = "Por favor espere un momento…", title = "Descargando socios")
            dialog.setCancelable(false)
            doAsync {
                val respuestasocios = URL("http://192.168.50.108/AppAgronomia/api/SocioxUPs").readText()
                val gsonsocios = Gson()
                val maesocios = gsonsocios.fromJson(respuestasocios, Array<SocioxUP>::class.java)
                DataAccess_RegistroAgrotecnico_App(applicationContext).delete_AllSocioxUP()
                database.use {
                    maesocios.forEach {
                        insert(
                            "SocioxUP",
                            "ID_SocioxUP" to it.ID_SocioxUP,
                            "ID_UP" to it.ID_UP,
                            "Codigo_UP" to it.Codigo_UP,
                            "Codigo_Socio" to it.Codigo_Socio,
                            "Fet_Socio" to it.Fet_Socio,
                            "Nombre_Socio" to it.Nombre_Socio,
                            "Cupo_Socio" to it.Cupo_Socio,
                            "Zona" to it.Zona
                        )
                    }
                }
                dialog.dismiss()
                uiThread { longToast("Socios Sincronizados. Total: " + maesocios.count().toString()) }

            }
            importaSincronizacion(0, id_instr, dateInString)
        }
        bSincroVisitas.setOnClickListener {
            val dialog =
                indeterminateProgressDialog(message = "Por favor espere un momento…", title = "Sincronizando Visitas")
            dialog.setCancelable(false)


            doAsync {
                for (item in registrovisitas_ToSincro!!) {
                    val registro_visita = AA_Registro_Visita(item.Codigo_Visita, item.Codigo_Instructor, item.Codigo_Socio, item.Fet_Socio, item.Fecha, item.Ubicacion, item.Motivo, item.Etapa, item.Finca, item.Localidad)
                    val fichaJson = Gson().toJson(registro_visita)
                    "http://192.168.50.108/AppAgronomia/api/AA_Registro_Visita"
                        .httpPost()
                        .header("Content-Type" to "application/json")
                        .body(fichaJson.toString())
                        .responseJson { request, response, result ->
                        }

                    database.use {
                        delete("AA_Registro_Visita", "Codigo_Visita = {codigo_visita}", "codigo_visita" to item.Codigo_Visita)
                    }


                }
                dialog.dismiss()
                uiThread { longToast("Visitas Sincronizadas") }
            }
            importaSincronizacion(0, id_instr, dateInString)
        }
    }


    fun importaSincronizacion(id_registro: Int, id_instr: Int, fecha: String): Boolean {
        val registro_Sincro = Registro_Sincronizaciones(0, id_instr, fecha)
        val fichaJson = Gson().toJson(registro_Sincro)
        "http://192.168.50.108/AppAgronomia/api/AA_Registro_Sincronizaciones"
            .httpPost()
            .header("Content-Type" to "application/json")
            .body(fichaJson.toString())
            .responseJson { request, response, result ->
            }
        database.use {
            replace(
                "AA_Registro_Sincronizaciones",
                "ID_Registro" to 0,
                "ID_Instr" to id_instr,
                "Fecha" to fecha
            )
        }
        return true
    }

    fun Date.toString(format: String, locale: Locale = Locale.getDefault()): String {
        val formatter = SimpleDateFormat(format, locale)
        return formatter.format(this)
    }

    fun getCurrentDateTime(): Date {
        return Calendar.getInstance().time
    }
}
