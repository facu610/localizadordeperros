package agronomia.coprotrab.agronomia_coprotab

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.View

class SociosActivity : AppCompatActivity() {

    val TAGID = "agronomia.coprotab.agronomia_coprotab.CODIGO_SOCIO"

    var rv_socios: androidx.recyclerview.widget.RecyclerView? = null
    var adapter: SocioxUP_Adapter? = null
    var layoutManager: androidx.recyclerview.widget.RecyclerView.LayoutManager? = null

    var lista_socioxup: ArrayList<SocioxUP>? = null

    var toolbar: Toolbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_socios)

        lista_socioxup = DataAccess_RegistroAgrotecnico_App(this).select_SocioxUP()

        rv_socios = findViewById(R.id.rv_Socios)
        rv_socios?.setHasFixedSize(true) // Optimiza el tamaño de las celdas, para que no lo calcule en tiempo de ejec.

        layoutManager =
            androidx.recyclerview.widget.LinearLayoutManager(this) // Define el tipo del rv, que es un linear layout
        rv_socios?.layoutManager = layoutManager // Asociamos el lmanager al Rec View

        var tagactivity: String = intent.getStringExtra("agronomia.coprotab.agronomia_coprotab.ACTIVIDAD")

        adapter = SocioxUP_Adapter(lista_socioxup!!, object : rvSocioxUP_ClickListener {
            override fun onClick(vista: View, index: Int) {

                if (tagactivity == "DETALLE") {
                    val intentdetallesocio = Intent(applicationContext, DetalleSocioActivity::class.java)
                    var socioxup: SocioxUP = adapter?.getItem(index) as SocioxUP
                    intentdetallesocio.putExtra(TAGID, socioxup.Codigo_Socio.toString())
                    startActivity(intentdetallesocio)
                }

                if (tagactivity == "VISITA") {
                    val intentregistrarvisita = Intent(applicationContext, RegistrarVisitaActivity::class.java)
                    var socioxup: SocioxUP = adapter?.getItem(index) as SocioxUP
                    intentregistrarvisita.putExtra(TAGID, socioxup.Codigo_Socio.toString())
                    startActivity(intentregistrarvisita)
                }
            }
        })

        // Definimos el adaptador y lo asociamos al Rec View

        rv_socios?.adapter = adapter!!

        toolbar = this.findViewById(R.id.tb_socios)
        toolbar?.title = "Lista de Socios"
        toolbar?.setTitleMargin(10, 10, 10, 10)
        setSupportActionBar(toolbar)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_tb_soc, menu)
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val item_search = menu?.findItem(R.id.action_search)
        var searchView = item_search?.actionView as SearchView
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = " Escribe un nombre..."
        searchView.setOnQueryTextFocusChangeListener { v, hasFocus ->

        }
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter?.filtrar(newText!!)
                return true
            }
        })
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.action_search -> {
                //hace algo
                return true
            }
            else -> {
                return super.onOptionsItemSelected(item)
            }
        }
    }


}
