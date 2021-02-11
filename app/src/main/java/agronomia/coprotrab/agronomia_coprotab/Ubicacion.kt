package agronomia.coprotrab.agronomia_coprotab

import agronomia.coprotrab.agronomia_coprotab.UbicacionListener
import android.annotation.SuppressLint
import android.app.Activity
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult


class Ubicacion(var activity: AppCompatActivity, ubicacionListener: UbicacionListener) {

    private val permisoFineLocation = android.Manifest.permission.ACCESS_FINE_LOCATION
    private val permisoCoarseLocation = android.Manifest.permission.ACCESS_COARSE_LOCATION

    private val CODIGO_SOLICITUD_UBICACION = 100
    private var fusedLocationClient: FusedLocationProviderClient? = null
    private var locationRequest: LocationRequest? = null
    private var callback: LocationCallback? = null

    init {
        fusedLocationClient = FusedLocationProviderClient(activity.applicationContext)
        inicializarLocationRequest()

        callback = object: LocationCallback(){
            override fun onLocationResult(p0: LocationResult?){
                super.onLocationResult(p0)

                ubicacionListener.ubicacionResponse(p0!!)
            }
        }
    }

    private fun inicializarLocationRequest(){
        locationRequest = LocationRequest()
        locationRequest?.interval = 10000
        locationRequest?.fastestInterval = 5000
        locationRequest?.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
    }

    private fun validarPermisosUbicacion():Boolean{
        val hayUbicacionPrecisa = ActivityCompat.checkSelfPermission(activity.applicationContext, permisoFineLocation) == PackageManager.PERMISSION_GRANTED
        val hayUbicacionOrdinaria = ActivityCompat.checkSelfPermission(activity.applicationContext, permisoCoarseLocation) == PackageManager.PERMISSION_GRANTED

        return hayUbicacionPrecisa && hayUbicacionOrdinaria
    }

    private fun pedirPermisos(){
        val deboProveerContexto = ActivityCompat.shouldShowRequestPermissionRationale(activity, permisoFineLocation)

        if(deboProveerContexto){
            //mandar un mensaje con explicacion adicional
            Toast.makeText(activity.applicationContext,"", Toast.LENGTH_LONG).show()
            }
        solicitudPermiso()

    }
    private fun solicitudPermiso(){
        ActivityCompat.requestPermissions(activity, arrayOf(permisoFineLocation, permisoCoarseLocation), CODIGO_SOLICITUD_UBICACION)
    }

    fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray){
        when(requestCode){
            CODIGO_SOLICITUD_UBICACION -> {
                if(grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    obtenerUbicacion()
                }
                else{
                    Toast.makeText(activity.applicationContext,"No diste permiso!", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    fun detenerActualizacionUbicacion(){
        this.fusedLocationClient?.removeLocationUpdates(callback)
    }

    fun inicializarUbicacion(){
        if(validarPermisosUbicacion()){
            obtenerUbicacion()
        }
        else{
            pedirPermisos()
        }
    }

    @SuppressLint("MissingPermission")
    private fun obtenerUbicacion(){
        validarPermisosUbicacion()
        fusedLocationClient?.requestLocationUpdates(locationRequest, callback, null)
    }




}