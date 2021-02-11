package agronomia.coprotrab.agronomia_coprotab

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView


class MenuItem_Adapter(var contex: Context, items:ArrayList<MenuItem>): BaseAdapter() {

    var items:ArrayList<MenuItem>? = null

    init{
        this.items = items
    }
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var vista = convertView
        var holder:ViewHolder? = null

        if(vista == null){
            vista = LayoutInflater.from(contex).inflate(R.layout.templates_menuitem, null)
            holder = ViewHolder(vista)
            vista.tag = holder
        }
        else{
            holder = vista.tag as? ViewHolder
        }
        val item = items?.get(position) as? MenuItem
        holder?.nombre?.text = item?.nombre
        holder?.imagen?.setImageResource(item?.imagen!!)
        return  vista!!

    }

    override fun getItem(position: Int): Any {
        return items?.get(position)!!
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return items?.count()!!
    }

    private class ViewHolder(vista: View){
        var nombre: TextView? = null
        var imagen: ImageView? = null
        init {
            nombre = vista.findViewById(R.id.tv_temp_nombreIcono)
            imagen = vista.findViewById(R.id.iv_temp_Icono)
        }

    }
}