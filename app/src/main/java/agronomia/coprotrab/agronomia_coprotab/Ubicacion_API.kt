package agronomia.coprotrab.agronomia_coprotab

class Ubicacion_API (id:String, latitud:String, longitud:String, fechahora:String){

    var Id: String = ""
    var Latitud: String = ""
    var Longitud: String = ""
    var FechaHora: String = ""

    init {
        this.Id = id
        this.Latitud = latitud
        this.Longitud = longitud
        this.FechaHora = fechahora
    }
}