package agronomia.coprotrab.agronomia_coprotab

class Instructor (id_instr:Int, user_inst:String, nombre_instr:String, zona_instr:Int, issuper_instr:Int, idvehi_instr:Int, idmovil_instr:Int, pass_instr: String ){

    var ID_Instr: Int = 0
    var User_Instr: String = ""
    var Nombre_Instr: String = ""
    var Zona_Instr: Int = 0
    var Issuper_Instr: Int = 0
    var Idvehiculo_Instr:Int = 0
    var Idmovil_Instr:Int = 0
    var Pass_Instr: String = ""


    init {
        this.ID_Instr = id_instr
        this.User_Instr = user_inst
        this.Nombre_Instr = nombre_instr
        this.Zona_Instr = zona_instr
        this.Issuper_Instr = issuper_instr
        this.Idvehiculo_Instr = idvehi_instr
        this.Idmovil_Instr = idmovil_instr
        this.Pass_Instr = pass_instr
    }
}
