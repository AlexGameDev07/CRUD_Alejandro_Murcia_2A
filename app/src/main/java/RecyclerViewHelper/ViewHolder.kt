package RecyclerViewHelper

import alejandro.murcia.crud_alejandro_murcia_2a.R
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ViewHolder(view: View) : RecyclerView.ViewHolder(view)
{
    val textView: TextView = view.findViewById(R.id.txtProductoCard)
}