package RecyclerViewHelper

import alejandro.murcia.crud_alejandro_murcia_2a.R
import android.icu.lang.UCharacter.IndicPositionalCategory
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import modelo.ClaseConexion
import modelo.DataClassProductos

class Adaptador(private var Datos: List<DataClassProductos>) : RecyclerView.Adapter<ViewHolder>()
{

    fun ActualizarLista(nuevaLista: List<DataClassProductos>){
        Datos = nuevaLista
        notifyDataSetChanged()

    }

    fun EliminarRegistro(nombreProducto:String, position: Int){
        //1-Crear un objeto de la clase conexión
        val objConexion = ClaseConexion().cadenaConexion()

        //Quitar el elemento de la lista
        val listaDatos = Datos.toMutableList()
        listaDatos.removeAt(position)

        //Quitar de la base de datos

        GlobalScope.launch(Dispatchers.IO){
            val delProducto = objConexion?.prepareStatement("DELETE TB_PRODUCTOS WHERE nombreProducto = ?")!!
            delProducto.setString(1,nombreProducto)
            delProducto.executeUpdate()

            val commit = objConexion.prepareStatement("commit")!!
            commit.executeUpdate()
        }


        //Le decimos al Adaptador que se eliminaron datos
        Datos = listaDatos.toList()
        notifyItemRemoved(position)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val vista = LayoutInflater.from(parent.context).inflate(R.layout.item_card, parent, false)
        return ViewHolder(vista)
    }

    override fun getItemCount() = Datos.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val producto = Datos[position]
        holder.textView.text = producto.nombreProducto

        val item = Datos[position]
        holder.imgBorrar.setOnClickListener {
        EliminarRegistro(item.nombreProducto, position)
        }
    }
}