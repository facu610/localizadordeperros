package agronomia.coprotrab.agronomia_coprotab

import android.content.Context
//import androidx.core.app.INotificationSideChannel
import org.jetbrains.anko.db.*

class DataAccess_RegistroAgrotecnico_App(val context: Context) {






    fun delete_Ubicaciones_APIs() = context.database.use {
        delete("Ubicacion_APITable")
    }
    fun select_Ubicaciones_API(): ArrayList<Ubicacion_API> = context.database.use {

        val lista_ubicacion_api = ArrayList<Ubicacion_API>()

        select("Ubicacion_APITable")
            .parseList(object : MapRowParser<List<Ubicacion_API>>{
                override fun parseRow(columns: Map<String, Any?>): List<Ubicacion_API> {
                    val Id = columns.getValue("Id")
                    val Latitud = columns.getValue("Latitud")
                    val Longitud = columns.getValue("Longitud")
                    val FechaHora = columns.getValue("FechaHora")


                    val ubicacion_api = Ubicacion_API(Id.toString(),
                        Latitud.toString(),
                        Longitud.toString(),
                        FechaHora.toString()
                    )
                    lista_ubicacion_api.add(ubicacion_api)
                    return lista_ubicacion_api.sortedWith(compareBy{ it.FechaHora})
                }
            })

        lista_ubicacion_api
    }

    fun select_Instructor(user: String): Usuario = context.database.use {

        var usuario: Usuario? = null

        select("AA_Instructores")
            .where("(User_Instr = {user})",
                "user" to user)
            .parseOpt(object : MapRowParser<Usuario> {
                override fun parseRow(columns: Map<String, Any?>): Usuario {
                    val ID_Instr = columns.getValue("ID_Instr")
                    val User_Instr = columns.getValue("User_Instr")
                    val Nombre_Instr = columns.getValue("Nombre_Instr")
                    val Zona_Instr = columns.getValue("Zona_Instr")
                    val Issuper_Instr = columns.getValue("Issuper_Instr")
                    val Idvehiculo_Instr = columns.getValue("Idvehiculo_Instr")
                    val Idmovil_Instr = columns.getValue("Idmovil_Instr")
                    val Pass_Instr = columns.getValue("Pass_Instr")

                    usuario = Usuario(ID_Instr.toString().toInt(),
                        User_Instr.toString(),
                        Nombre_Instr.toString(),
                        Zona_Instr.toString().toInt(),
                        Issuper_Instr.toString().toInt(),
                        Idvehiculo_Instr.toString().toInt(),
                        Idmovil_Instr.toString().toInt(),
                        Pass_Instr.toString())

                    return usuario!!
                }
            })

        usuario!!
    }





}


