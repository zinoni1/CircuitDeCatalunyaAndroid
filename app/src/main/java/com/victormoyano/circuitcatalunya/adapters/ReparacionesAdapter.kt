package com.victormoyano.circuitcatalunya.adapters
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.victormoyano.circuitcatalunya.R
import com.victormoyano.circuitcatalunya.models.Averias
import retrofit2.Response
import com.squareup.picasso.Picasso;


class ReparacionesAdapter(private val context: Context, val response: Response<List<Averias>>) :
    RecyclerView.Adapter<ReparacionesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.reparacionscard, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = response.body()?.get(position)
        data?.let { holder.bindData(it) }
    }

    override fun getItemCount(): Int {
        return response.body()?.size ?: 0
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        private val image: ImageView = itemView.findViewById(R.id.image)
        fun bindData(data: Averias) {
            titleTextView.text = data.descripcion
            Picasso.get().load(data.image_url).into(image)
        }
    }
}
