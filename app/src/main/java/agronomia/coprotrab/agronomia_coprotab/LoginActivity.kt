package agronomia.coprotrab.agronomia_coprotab

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import android.text.TextUtils
import android.view.View
import android.widget.*
import java.lang.Exception

class LoginActivity : AppCompatActivity() {

    val TAGACT = "agronomia.coprotab.agronomia_coprotab.INSTRUCTOR"

    private lateinit var progressBar: ProgressBar
    private lateinit var et_login_user: EditText
    private lateinit var et_login_contra: EditText
    private lateinit var b_log_login: Button
    private lateinit var tv_log_msj: TextView
    var toolbar: Toolbar? = null


    override fun onCreate(savedInstanceState: Bundle?) {

        var usuario: Usuario?
        usuario = null

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        toolbar = findViewById(R.id.tb_act_sincro)
        toolbar?.title = "Log In"
        toolbar?.setTitleMargin(10, 10, 10, 10)
        setSupportActionBar(toolbar)

        et_login_user = findViewById(R.id.et_Log_User)
        et_login_contra = findViewById(R.id.et_Log_Contra)
        progressBar = ProgressBar(this)

        b_log_login = findViewById(R.id.b_Log_Login)
        b_log_login.setOnClickListener {

            val user: String = et_login_user.text.toString()
            val contra: String = et_login_contra.text.toString()

            if (!TextUtils.isEmpty(user) && !TextUtils.isEmpty(contra)) {
                progressBar.visibility = View.VISIBLE
                try {

                    usuario = DataAccess_RegistroAgrotecnico_App(this).select_Instructor(user)

                    if (usuario != null && usuario?.Pass_Instr == contra) {

                        SharedApp.prefs?.user = usuario?.User_Instr.toString()
                        SharedApp.prefs?.nombre = usuario?.Nombre_Instr.toString()
                        SharedApp.prefs?.zona = usuario?.Zona_Instr.toString()
                        SharedApp.prefs?.id = usuario?.ID_Instr.toString()


                        val intentMain = Intent(this, MainActivity::class.java)
                        intentMain.putExtra(TAGACT, usuario?.Nombre_Instr)
                        startActivity(intentMain)
                    } else {
                        Toast.makeText(this, "Instructor no registrado", Toast.LENGTH_LONG).show()
                    }
                } catch (e: Exception) {
                    Toast.makeText(this, "Instructor no registrado", Toast.LENGTH_LONG).show()
                }


            }
            progressBar.visibility = View.INVISIBLE
        }



    }


}
