package com.dheeraj.mynotes.ui

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.dheeraj.mynotes.R
import com.dheeraj.mynotes.data.DataOfNotes


class AdapterOfNotes(
    private val list: LiveData<List<DataOfNotes>>?,
    val deleteUser: (Int) -> Unit,
    val editData: (Int, String, String,String) -> Unit
):RecyclerView.Adapter<ViewHolderOfNotes>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderOfNotes {
        val inflater= LayoutInflater.from(parent.context)
        val view=inflater.inflate(R.layout.item_of_notes_adapter, parent,false)
        return ViewHolderOfNotes(view)
    }
    override fun getItemCount(): Int {
        return list?.value?.size ?: Log.e("=====================","$list")
    }
    override fun onBindViewHolder(holder: ViewHolderOfNotes, position: Int) {
        val currentUser = list?.value?.get(position)
        if (currentUser != null) {
            holder.bind(currentUser)
        }
        holder.delete.setOnClickListener {
            deleteUser(currentUser!!.id)
        }
        holder.edit.setOnClickListener {
            if (currentUser != null) {
                editData(currentUser.id,currentUser.title,currentUser.description,currentUser.date)
            }
        }
    }
}

class ViewHolderOfNotes( view : View):RecyclerView.ViewHolder(view) {
    val title1=view.findViewById<TextView>(R.id.title)
    val description1=view.findViewById<TextView>(R.id.description)
    val date1=view.findViewById<TextView>(R.id.date)
    val cardItem=view.findViewById<CardView>(R.id.cardItem)
    val delete=view.findViewById<ImageView>(R.id.delete)
    val edit=view.findViewById<ImageView>(R.id.edit)

    fun bind(dataOfNotes: DataOfNotes) {
        title1.text = dataOfNotes.title
        description1.text = dataOfNotes.description
        date1.text = dataOfNotes.date
    }


}