package agronomia.coprotrab.agronomia_coprotab

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import android.view.View
import android.widget.*
import com.google.android.gms.location.LocationResult
import kotlinx.android.synthetic.main.activity_registrar_visita.*
import org.jetbrains.anko.db.INTEGER
import org.jetbrains.anko.db.TEXT
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.toast
import java.text.SimpleDateFormat
import java.util.*







class RegistrarVisitaActivity : AppCompatActivity() {


    var ubicacion: Ubicacion? = null
    lateinit var ub: String
    var toolbar: Toolbar? = null

    var spinner: Spinner? = null
    var motivo:String = ""


    val id_instr: Int = SharedApp.prefs.id.toInt()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registrar_visita)

        val tv_ubic = findViewById<TextView>(R.id.textView_ubicacion)
        toolbar = findViewById(R.id.tb_registrarvisita )
        toolbar?.title = "Registrar Visita"

        spinner = findViewById(R.id.spinner_etapacultivo)
        val arrayAdapter: ArrayAdapter<CharSequence> = ArrayAdapter.createFromResource(this, R.array.etapascultivo,android.R.layout.simple_spinner_item)
        arrayAdapter.setDropDownViewResource(R.layout.spinner_item)
        spinner?.adapter = arrayAdapter



        var codigo_socio: Int = (intent.getStringExtra("agronomia.coprotab.agronomia_coprotab.CODIGO_SOCIO").toInt())

        val date = getCurrentDateTime()
        val dateInString = date.toString("yyyy/MM/dd HH:mm:ss")

        val CheckBox_TA = findViewById<CheckBox>(R.id.checkBox_TA)
        val CheckBox_STP = findViewById<CheckBox>(R.id.checkBox_STP)
        val CheckBox_TM = findViewById<CheckBox>(R.id.checkBox_TM)


        ubicacion = Ubicacion(this, object : UbicacionListener {
            override fun ubicacionResponse(locationResult: LocationResult) {
                val str: String = locationResult.lastLocation.latitude.toString() + locationResult.lastLocation.longitude.toString()
                tv_ubic?.text = str
                ub = str
            }
        })

        val button_guardar = findViewById<Button>(R.id.button_guardar_registrarvisita)
        button_guardar.setOnClickListener{

            if (editText_Finca.text.toString() == "" || editText_Localidad.text.toString() == "" )
            {
                Toast.makeText(this, "Complete todos los campos...", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            if (tv_ubic.text.toString() != "") {
                ub = tv_ubic.text.toString()

            } else {
                ub = "Ub. No encontrada"
            }

            if (CheckBox_TA.isChecked){
                motivo += "Técnica Agronómica, "}
            if (CheckBox_STP.isChecked){
                motivo += "STP - Cuestionarios, "}
            if (CheckBox_TM.isChecked){
                motivo += "Toma de Muestras, "}

            database.use {
                insert("AA_Registro_Visita",
                    "Codigo_Visita" to 0,
                    "Codigo_Instructor" to id_instr,
                    "Codigo_Socio" to codigo_socio,
                    "Fet_Socio" to 0,
                    "Fecha" to dateInString,
                    "Ubicacion" to ub,
                    "Motivo" to motivo,
                    "Etapa" to spinner!!.selectedItem.toString(),
                    "Finca" to editText_Finca.text.toString(),
                    "Localidad" to editText_Localidad.text.toString()
                )
            }
            toast("Visita Registrada!")
        }
    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        ubicacion?.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
    override fun onStart() {
        super.onStart()
        ubicacion?.inicializarUbicacion()
    }
    override fun onPause() {
        super.onPause()
        ubicacion?.detenerActualizacionUbicacion()
    }
    fun Date.toString(format: String, locale: Locale = Locale.getDefault()): String {
        val formatter = SimpleDateFormat(format, locale)
        return formatter.format(this)
    }
    fun getCurrentDateTime(): Date {
        return Calendar.getInstance().time
    }


}
