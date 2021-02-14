package agronomia.coprotrab.agronomia_coprotab

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import org.jetbrains.anko.db.*

class DBHelper (ctx: Context) : ManagedSQLiteOpenHelper(ctx, "RegistroAgrotecnico_App",null, 231) {

    companion object {
        private var instance: DBHelper? = null
        @Synchronized
        fun getInstance(ctx: Context): DBHelper {
            if (instance == null) {
                instance = DBHelper(ctx.applicationContext)
            }
            return instance!!
        }
    }

    override fun onCreate(db: SQLiteDatabase) {


        db.createTable( "Ubicacion_APITable", true,
            "Id" to TEXT,
        "Latitud" to TEXT,
        "Longitud" to TEXT,
        "FechaHora" to TEXT)

        db.createTable("AA_Instructores", true,
            "ID_Instr" to INTEGER,
            "User_Instr" to TEXT,
            "Nombre_Instr" to TEXT,
            "Zona_Instr" to INTEGER,
            "Issuper_Instr" to INTEGER,
            "Idvehiculo_Instr" to INTEGER,
            "Idmovil_Instr" to INTEGER,
            "Pass_Instr" to TEXT)

        db.insert("AA_Instructores",
            "ID_Instr" to 1,
            "User_Instr" to "admin",
            "Nombre_Instr" to "Administrador",
            "Zona_Instr" to 1,
            "Issuper_Instr" to 1,
            "Idvehiculo_Instr" to 1,
            "Idmovil_Instr" to 1,
            "Pass_Instr" to "root")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        //NOTA: Por simplicidad aquí utilizamos directamente la opción de
        //      eliminar la tabla anterior y crearla de nuevo vacía con el nuevo formato.
        //      Sin embargo lo normal será que haya que migrar datos de la tabla antigua
        //      a la nueva, por lo que este método debería ser más elaborado.

        //Se elimina la versión anterior de la tabla
//        db.dropTable("AA_Socios")
//
//        db.createTable("AA_MaeSocios", true,
//                "ID_Soc" to INTEGER + PRIMARY_KEY,
//                "FET_Soc" to INTEGER,
//                "Nombre_Soc" to TEXT,
//                "Kilos_Soc" to INTEGER,
//                "Domicilio_Soc" to TEXT,
//                "Localidad_Soc" to TEXT,
//                "Telefono_Soc" to TEXT)
        onCreate(db)

        db.dropTable("Ubicacion_APITable")
        db.dropTable("AA_Instructores")
        onCreate(db)
    }
}

val Context.database: DBHelper
    get() = DBHelper.getInstance(applicationContext)