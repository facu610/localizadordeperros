package agronomia.coprotrab.agronomia_coprotab

import agronomia.coprotrab.agronomia_coprotab.R
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import android.os.Bundle
import androidx.appcompat.widget.ButtonBarLayout
import android.view.View
import android.widget.Button
import kotlinx.android.synthetic.main.activity_ayuda.*

class AyudaActivity : AppCompatActivity() {

    var toolbar: Toolbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ayuda)

        toolbar = findViewById(R.id.tb_act_ayuda)
        toolbar?.title = "Ayuda"
        toolbar?.setTitleMargin(10,10,10,10)
        setSupportActionBar(toolbar)


        val b_ayuda_1 = findViewById<Button>(R.id.b_Ayuda_1)
        b_ayuda_1.setOnClickListener {
            tv_Ayuda_1.visibility = View.VISIBLE
            tv_Ayuda_2.visibility = View.GONE
            tv_Ayuda_3.visibility = View.GONE
            tv_Ayuda_4.visibility = View.GONE
            tv_Ayuda_5.visibility = View.GONE
        }

        val b_ayuda_2 = findViewById<Button>(R.id.b_Ayuda_2)
        b_ayuda_2.setOnClickListener {
            tv_Ayuda_1.visibility = View.GONE
            tv_Ayuda_2.visibility = View.VISIBLE
            tv_Ayuda_3.visibility = View.GONE
            tv_Ayuda_4.visibility = View.GONE
            tv_Ayuda_5.visibility = View.GONE
        }
        val b_ayuda_3 = findViewById<Button>(R.id.b_Ayuda_3)
        b_ayuda_3.setOnClickListener {
            tv_Ayuda_1.visibility = View.GONE
            tv_Ayuda_2.visibility = View.GONE
            tv_Ayuda_3.visibility = View.VISIBLE
            tv_Ayuda_4.visibility = View.GONE
            tv_Ayuda_5.visibility = View.GONE
        }
        val b_ayuda_4 = findViewById<Button>(R.id.b_Ayuda_4)
        b_ayuda_4.setOnClickListener {
            tv_Ayuda_1.visibility = View.GONE
            tv_Ayuda_2.visibility = View.GONE
            tv_Ayuda_3.visibility = View.GONE
            tv_Ayuda_4.visibility = View.VISIBLE
            tv_Ayuda_5.visibility = View.GONE
        }
        val b_ayuda_5 = findViewById<Button>(R.id.b_Ayuda_5)
        b_ayuda_5.setOnClickListener {
            tv_Ayuda_1.visibility = View.GONE
            tv_Ayuda_2.visibility = View.GONE
            tv_Ayuda_3.visibility = View.GONE
            tv_Ayuda_4.visibility = View.GONE
            tv_Ayuda_5.visibility = View.VISIBLE
        }

    }
}
