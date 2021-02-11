package agronomia.coprotrab.agronomia_coprotab

import android.content.Context
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import java.io.IOException


class Network {

    companion object {
        fun validaConeccion(activity: AppCompatActivity):Boolean{
            val connectivityManager = activity.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo = connectivityManager.activeNetworkInfo
            return networkInfo != null && networkInfo.isConnected

        }
        fun isConecctedToCoop(): Boolean {

            val runtime = Runtime.getRuntime()
            try {
                val ipProcess = runtime.exec("/system/bin/ping -c 1 198.199.89.202")
                val exitValue = ipProcess.waitFor()
                return exitValue == 0
            } catch (e: IOException) {
                e.printStackTrace()
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }

            return false
        }

    }




}