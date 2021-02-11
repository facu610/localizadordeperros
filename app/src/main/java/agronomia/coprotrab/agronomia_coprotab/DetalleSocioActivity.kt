package agronomia.coprotrab.agronomia_coprotab

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import android.widget.*
import android.content.ClipData
import android.content.ClipboardManager
import android.view.View
import kotlinx.android.synthetic.main.activity_detallesocio.*

class DetalleSocioActivity : AppCompatActivity() {

    var socioxup:SocioxUP? = null
    var textView_codigo: TextView? = null
    var textView_fet: TextView? = null
    var textView_nombre: TextView? = null
    var textView_up: TextView? = null
    var textView_cupo: TextView? = null
    var textView_zona: TextView? = null

    var toolbar: Toolbar? = null

    var myClipboard: ClipboardManager? = null
    var myClip: ClipData? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detallesocio)


        val codigo_socio = intent.getStringExtra("agronomia.coprotab.agronomia_coprotab.CODIGO_SOCIO")
        myClipboard = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager?

        toolbar = findViewById(R.id.tb_act_ficha)
        toolbar?.title = "Detalle del Socio"
        toolbar?.setTitleMargin(10,10,10,10)
        setSupportActionBar(toolbar)

        socioxup = DataAccess_RegistroAgrotecnico_App(this).select_SocioxUP(codigo_socio.toInt())

        textView_codigo = findViewById(R.id.textView_detalle_Codigo)
        textView_fet = findViewById(R.id.textView_detalle_FET)
        textView_nombre = findViewById(R.id.textView_detalle_Nombre)
        textView_up = findViewById(R.id.textView_detalle_UP)
        textView_cupo = findViewById(R.id.textView_detalle_Cupo)
        textView_zona = findViewById(R.id.textView_detalle_Zona)

        textView_codigo?.text = socioxup?.Codigo_Socio.toString()
        textView_fet?.text = socioxup?.Fet_Socio.toString()
        textView_nombre?.text = socioxup?.Nombre_Socio.toString()
        textView_up?.text = socioxup?.Codigo_UP.toString()
        textView_cupo?.text =  socioxup?.Cupo_Socio.toString()
        textView_zona?.text = socioxup?.Zona.toString()

    }

    fun copyText(view: View) {
        myClip = ClipData.newPlainText("text", textView_detalle_Codigo.text)
        myClipboard?.primaryClip = myClip

        Toast.makeText(this, "Código de socio copiado al portapapeles.", Toast.LENGTH_SHORT).show()
    }


}
