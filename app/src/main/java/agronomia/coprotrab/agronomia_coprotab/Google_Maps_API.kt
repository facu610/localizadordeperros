package agronomia.coprotrab.agronomia_coprotab

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.gson.Gson
import org.jetbrains.anko.longToast
import org.jetbrains.anko.uiThread
import java.net.URL

class Google_Maps_API : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var map:GoogleMap
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_google__maps__a_p_i)
        createFragment()
    }

    private fun createFragment() {
        val mapFragment: SupportMapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

    }

    override fun onMapReady(googleMap: GoogleMap) {
       map = googleMap
        createMarker()

    }

    private fun createMarker() {


        val favoritePlace = LatLng(-24.7823709,-65.4309822)
        map.addMarker(MarkerOptions().position(favoritePlace).title("que perron negro!!"))
        map.animateCamera(
            CameraUpdateFactory.newLatLngZoom(favoritePlace, 18f),
            4000,
            null)
    }


}