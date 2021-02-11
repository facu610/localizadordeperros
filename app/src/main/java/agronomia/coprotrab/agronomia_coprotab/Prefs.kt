package agronomia.coprotrab.agronomia_coprotab

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager

class Prefs(context: Context){

        val PREFS_INSTRUCTOR = "com.agronomia.coprotab.agrokot.sharedpreferences"
        val SHARED_USER = "shared_user"
        val SHARED_NOMBRE = "shared_nombre"
        val SHARED_ZONA = "shared_zona"
        val SHARED_ID = "shared_id"

    val prefs: SharedPreferences = context.getSharedPreferences(PREFS_INSTRUCTOR, 0)

        var user: String
            get() = prefs.getString(SHARED_USER, "")
            set(value) = prefs.edit().putString(SHARED_USER, value).apply()

        var nombre: String
            get() = prefs.getString(SHARED_NOMBRE, "")
            set(value) = prefs.edit().putString(SHARED_NOMBRE, value).apply()

        var zona: String
            get() = prefs.getString(SHARED_ZONA, "")
            set(value) = prefs.edit().putString(SHARED_ZONA, value).apply()

        var id: String
            get() = prefs.getString(SHARED_ID, "")
            set(value) = prefs.edit().putString(SHARED_ID, value).apply()


}
