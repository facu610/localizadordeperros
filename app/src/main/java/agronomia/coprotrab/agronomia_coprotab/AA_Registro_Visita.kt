package agronomia.coprotrab.agronomia_coprotab

class  AA_Registro_Visita(codigo_visita:Int, codigo_instructor:Int?, codigo_socio:Int?, fet_socio:Int?, fecha:String, ubicacion:String, motivo: String, etapa:String, finca:String, localidad:String){
    var Codigo_Visita: Int = 0
    var Codigo_Instructor: Int = 0
    var Codigo_Socio: Int = 0
    var Fet_Socio: Int = 0
    var Fecha: String =""
    var Ubicacion: String =""
    var Motivo: String =""
    var Etapa: String =""
    var Finca: String =""
    var Localidad: String =""

    init {
        this.Codigo_Visita = codigo_visita
        this.Codigo_Instructor = codigo_instructor!!
        this.Codigo_Socio = codigo_socio!!
        this.Fet_Socio = fet_socio!!
        this.Fecha = fecha
        this.Ubicacion = ubicacion
        this.Motivo = motivo
        this.Etapa = etapa
        this.Finca = finca
        this.Localidad = localidad
    }
}

