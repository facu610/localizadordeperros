package agronomia.coprotrab.agronomia_coprotab

import android.content.Context
//import androidx.core.app.INotificationSideChannel
import org.jetbrains.anko.db.*

class DataAccess_RegistroAgrotecnico_App(val context: Context) {


    fun select_RegistroSincronizaciones(id_instr: Int): Registro_Sincronizaciones? = context.database.use {

        var registro_sincro: Registro_Sincronizaciones?
        select("AA_Registro_Sincronizaciones")
            .where(
                "(ID_Instr = {id})",
                "id" to id_instr
            ).limit(1)
            .parseOpt(object : MapRowParser<Registro_Sincronizaciones> {
                override fun parseRow(columns: Map<String, Any?>): Registro_Sincronizaciones {
                    val ID_Registro = columns.getValue("ID_Registro")
                    val ID_Instr = columns.getValue("ID_Instr")
                    val Fecha = columns.getValue("Fecha")

                    registro_sincro = Registro_Sincronizaciones(
                        ID_Registro.toString().toInt(),
                        ID_Instr.toString().toInt(),
                        Fecha.toString()
                    )


                    return registro_sincro!!
                }
            })

    }


    fun select_RegistroVisitaToSincro(): ArrayList<AA_Registro_Visita> = context.database.use {

        val lista_visitas = ArrayList<AA_Registro_Visita>()

        select("AA_Registro_Visita")
            .parseList(object : MapRowParser<List<AA_Registro_Visita>> {
                override fun parseRow(columns: Map<String, Any?>): List<AA_Registro_Visita> {
                    val Codigo_Visita = columns.getValue("Codigo_Visita")
                    val Codigo_Instructor = columns.getValue("Codigo_Instructor")
                    val Codigo_Socio = columns.getValue("Codigo_Socio")
                    val Fet_Socio = columns.getValue("Fet_Socio")
                    val Fecha = columns.getValue("Fecha")
                    val Ubicacion = columns.getValue("Ubicacion")
                    val Motivo = columns.getValue("Motivo")
                    val Etapa = columns.getValue("Etapa")
                    val Finca = columns.getValue("Finca")
                    val Localidad = columns.getValue("Localidad")

                    val registrovisita_sincro = AA_Registro_Visita(
                        Codigo_Visita.toString().toInt(),
                        Codigo_Instructor.toString().toInt(),
                        Codigo_Socio.toString().toInt(),
                        Fet_Socio.toString().toInt(),
                        Fecha.toString(),
                        Ubicacion.toString(),
                        Motivo.toString(),
                        Etapa.toString(),
                        Finca.toString(),
                        Localidad.toString()
                    )

                    lista_visitas.add(registrovisita_sincro)

                    return lista_visitas
                }
            })
        lista_visitas
    }

    fun delete_Instructores() = context.database.use {
        delete("AA_Instructores")
    }
    fun delete_AllSocioxUP() = context.database.use {
        delete("SocioxUP")
    }

    fun select_Instructor(user: String): Instructor = context.database.use {

        var instructor: Instructor? = null

        select("AA_Instructores")
            .where("(User_Instr = {user})",
                "user" to user)
            .parseOpt(object : MapRowParser<Instructor> {
                override fun parseRow(columns: Map<String, Any?>): Instructor {
                    val ID_Instr = columns.getValue("ID_Instr")
                    val User_Instr = columns.getValue("User_Instr")
                    val Nombre_Instr = columns.getValue("Nombre_Instr")
                    val Zona_Instr = columns.getValue("Zona_Instr")
                    val Issuper_Instr = columns.getValue("Issuper_Instr")
                    val Idvehiculo_Instr = columns.getValue("Idvehiculo_Instr")
                    val Idmovil_Instr = columns.getValue("Idmovil_Instr")
                    val Pass_Instr = columns.getValue("Pass_Instr")

                    instructor = Instructor(ID_Instr.toString().toInt(),
                        User_Instr.toString(),
                        Nombre_Instr.toString(),
                        Zona_Instr.toString().toInt(),
                        Issuper_Instr.toString().toInt(),
                        Idvehiculo_Instr.toString().toInt(),
                        Idmovil_Instr.toString().toInt(),
                        Pass_Instr.toString())

                    return instructor!!
                }
            })

        instructor!!
    }

    fun select_SocioxUP(): ArrayList<SocioxUP> = context.database.use {
        val lista_socioxup = ArrayList<SocioxUP>()

        select("SocioxUP", "ID_SocioxUP", "ID_UP", "Codigo_UP", "Codigo_Socio", "Fet_Socio", "Nombre_Socio", "Cupo_Socio", "Zona")
            .parseList(object : MapRowParser<List<SocioxUP>> {
                override fun parseRow(columns: Map<String, Any?>): List<SocioxUP> {
                    val ID_SocioxUP = columns.getValue("ID_SocioxUP")
                    val ID_UP = columns.getValue("ID_UP")
                    val Codigo_UP = columns.getValue("Codigo_UP")
                    val Codigo_Socio = columns.getValue("Codigo_Socio")
                    val Fet_Socio = columns.getValue("Fet_Socio")
                    val Nombre_Socio = columns.getValue("Nombre_Socio")
                    val Cupo_Socio = columns.getValue("Cupo_Socio")
                    val Zona = columns.getValue("Zona")

                    val socioxup = SocioxUP(ID_SocioxUP.toString().toInt(),
                        ID_UP.toString().toInt(),
                        Codigo_UP.toString().toInt(),
                        Codigo_Socio.toString().toInt(),
                        Fet_Socio.toString().toInt(),
                        Nombre_Socio.toString(),
                        Cupo_Socio.toString().toIntOrNull(),
                        Zona.toString().toIntOrNull()
                    )

                    lista_socioxup.add(socioxup)

                    return lista_socioxup
                }
            })

        lista_socioxup
    }

    fun select_SocioxUP(codigo_socio: Int): SocioxUP = context.database.use {

        var socioxUP: SocioxUP? = null

        select("SocioxUP", "ID_SocioxUP", "ID_UP", "Codigo_UP", "Codigo_Socio", "Fet_Socio", "Nombre_Socio", "Cupo_Socio", "Zona")
            .where("(Codigo_Socio = {id})",
                "id" to codigo_socio)
            .parseOpt(object : MapRowParser<SocioxUP> {
                override fun parseRow(columns: Map<String, Any?>): SocioxUP {
                    val ID_SocioxUP = columns.getValue("ID_SocioxUP")
                    val ID_UP = columns.getValue("ID_UP")
                    val Codigo_UP = columns.getValue("Codigo_UP")
                    val Codigo_Socio = columns.getValue("Codigo_Socio")
                    val Fet_Socio = columns.getValue("Fet_Socio")
                    val Nombre_Socio = columns.getValue("Nombre_Socio")
                    val Cupo_Socio = columns.getValue("Cupo_Socio")
                    val Zona = columns.getValue("Zona")

                    socioxUP = SocioxUP(ID_SocioxUP.toString().toInt(),
                        ID_UP.toString().toInt(),
                        Codigo_UP.toString().toInt(),
                        Codigo_Socio.toString().toInt(),
                        Fet_Socio.toString().toInt(),
                        Nombre_Socio.toString(),
                        Cupo_Socio.toString().toIntOrNull(),
                        Zona.toString().toIntOrNull())

                    return socioxUP!!
                }

            })

socioxUP!!
    }

}


