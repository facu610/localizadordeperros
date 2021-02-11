package agronomia.coprotrab.agronomia_coprotab

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.GridView
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    //// VARIABLE QUE INDICA EL TIPO DE ACTIVIDAD; SI ES UNA VISITA O SOLO CONSULTA
    // ESTA VARIABLE TOMA DE VALOR VISITA
    val TAGACT = "agronomia.coprotab.agronomia_coprotab.ACTIVIDAD"


    //SHARED PREFERENCES

    val EMPTY_VALUE = ""
    //VARIABLES

    var tvInstr: TextView? = null
    var tvZona: TextView? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvInstr = findViewById<TextView>(R.id.tv_Instr)
        tvZona = findViewById<TextView>(R.id.tv_Zona)

        var menuitems = ArrayList<MenuItem>()


        menuitems.add(MenuItem("Sincronización", R.drawable.ic_sync_green))
        menuitems.add(MenuItem("Registrar Visita", R.drawable.ic_visita_green))
        menuitems.add(MenuItem("Ver Socios", R.drawable.ic_socios_green))



        val grid: GridView = findViewById(R.id.gv_menuprin)

        val adaptador = MenuItem_Adapter(this, menuitems)
        grid.adapter = adaptador

        SharedApp.prefs = Prefs(applicationContext)
        configView()

        grid.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->


            if (menuitems.get(position).nombre == "Sincronización") {
                val intentSincronizacion = Intent(this, SincronizacionActivity::class.java)
                startActivity(intentSincronizacion)
            }
            if (menuitems.get(position).nombre == "Registrar Visita") {
                val intentSocios = Intent(this, Google_Maps_API::class.java) //tipo pantalla
                intentSocios.putExtra(TAGACT, "VISITA")
                startActivity(intentSocios)
            }
            if (menuitems.get(position).nombre == "Ver Socios") {
                val intentSocios = Intent(this, SociosActivity::class.java)
                intentSocios.putExtra(TAGACT, "DETALLE")
                startActivity(intentSocios)
            }

        }
        b_Main_Ingresa.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
        b_Main_Salir.setOnClickListener {
            SharedApp.prefs?.user = ""
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }




    }
    fun configView(){
        if(isSavedName()) {
            tvInstr?.visibility = View.VISIBLE
            tvInstr?.text = "Usuario: ${SharedApp.prefs?.nombre}"
            tvInstr?.visibility = View.VISIBLE
            tvZona?.text = "Zona: ${SharedApp.prefs?.zona}"
            tvZona?.visibility = View.VISIBLE
            b_Main_Salir.visibility = View.VISIBLE
            gv_menuprin.visibility = View.VISIBLE



        }
        else{
            tvInstr?.text = ""
            b_Main_Ingresa.visibility = View.VISIBLE
            gv_menuprin.visibility = View.INVISIBLE


        }
    }

    fun isSavedName():Boolean{
        val myUser = SharedApp.prefs?.user
        return myUser != EMPTY_VALUE
    }
}
