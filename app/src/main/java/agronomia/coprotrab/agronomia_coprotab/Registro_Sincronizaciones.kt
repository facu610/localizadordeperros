package agronomia.coprotrab.agronomia_coprotab

class Registro_Sincronizaciones(id_registro:Int, id_instr:Int, fecha:String) {

    var ID_Registro:Int = 0
    var ID_Instr:Int = 0
    var Fecha: String = ""

    init {
        this.ID_Registro = id_registro
        this.ID_Instr = id_instr
        this.Fecha = fecha
    }
}