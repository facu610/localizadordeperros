package agronomia.coprotrab.agronomia_coprotab

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class SocioxUP_Adapter(items: ArrayList<SocioxUP>, var listener: rvSocioxUP_ClickListener): androidx.recyclerview.widget.RecyclerView.Adapter<SocioxUP_Adapter.ViewHolder>(){


    var items: ArrayList<SocioxUP>? = null
    var copiaItems: ArrayList<SocioxUP>? = null

    init{
        this.items = ArrayList(items)
        this.copiaItems = items
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val vista = LayoutInflater.from(parent?.context).inflate(R.layout.template_socioxups, parent, false)
        val viewHolder = ViewHolder(vista, listener)
        return  viewHolder
    }
    override fun getItemCount(): Int {
        return items?.count()!!
    }

    fun getItem(position: Int): Any {
        return this.items?.get(position)!!
    }

    fun filtrar(str: String){
        items?.clear()
        if(str.isEmpty()){
            items = ArrayList(copiaItems)
            notifyDataSetChanged()
            return
        }

        var busqueda = str
        busqueda = busqueda.toLowerCase()
        for (item in copiaItems!!){
            val nombre = item.Nombre_Socio.toLowerCase() + item.Codigo_Socio.toString()
            if(nombre.contains(busqueda)){
                items?.add(item)
            }
        }
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items?.get(position)
        holder.id_soc?.text = item?.Codigo_Socio.toString()
        holder.nombre_soc?.text = item?.Nombre_Socio.toString()
        holder.loc_soc?.text = "UP: " + item?.Codigo_UP.toString()
    }

    class ViewHolder(vista:View, listener: rvSocioxUP_ClickListener): androidx.recyclerview.widget.RecyclerView.ViewHolder(vista), View.OnClickListener{

        var vista = vista
        var id_soc: TextView? = null
        var nombre_soc: TextView? = null
        var loc_soc: TextView? = null
        var listener: rvSocioxUP_ClickListener? = null
        init {
            id_soc = vista.findViewById(R.id.tv_temp_codigo_socio)
            nombre_soc = vista.findViewById(R.id.tv_temp_nombre_socio)
            loc_soc = vista.findViewById(R.id.tv_temp_up_socio)
            this.listener = listener
            vista.setOnClickListener(this)
        }
        override fun onClick(v: View?) {
            this.listener?.onClick(v!!,adapterPosition)
        }
    }
}

