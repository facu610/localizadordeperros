package agronomia.coprotrab.agronomia_coprotab

class SocioxUP(id_socioxup:Int, id_up:Int, codigo_up:Int, codigo_socio:Int, fet_socio:Int, nombre_socio:String, cupo_socio:Int?, zona:Int?) {

    var ID_SocioxUP: Int = 0
    var ID_UP: Int = 0
    var Codigo_UP:Int = 0
    var Codigo_Socio:Int = 0
    var Fet_Socio: Int = 0
    var Nombre_Socio: String = ""
    var Cupo_Socio: Int?
    var Zona: Int?

    init {
        this.ID_SocioxUP = id_socioxup
        this.ID_UP = id_up
        this.Codigo_UP = codigo_up
        this.Codigo_Socio = codigo_socio
        this.Fet_Socio = fet_socio
        this.Nombre_Socio = nombre_socio
        this.Cupo_Socio = cupo_socio
        this.Zona = zona
    }
}
