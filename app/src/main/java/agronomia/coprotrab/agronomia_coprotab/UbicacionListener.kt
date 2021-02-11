package agronomia.coprotrab.agronomia_coprotab

import com.google.android.gms.location.LocationResult

interface UbicacionListener {

    fun ubicacionResponse(locationResult: LocationResult)

}