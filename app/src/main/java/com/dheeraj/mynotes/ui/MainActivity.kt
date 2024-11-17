package com.dheeraj.mynotes.ui

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.Window
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.dheeraj.mynotes.R
import com.dheeraj.mynotes.data.ViewModelOfNotes
import com.dheeraj.mynotes.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Calendar

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    val year = c.get(Calendar.YEAR)
    val month = c.get(Calendar.MONTH)+1
    val day = c.get(Calendar.DAY_OF_MONTH)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        viewModel= ViewModelProvider(this).get(ViewModelOfNotes::class.java)
        viewModel.start(this)


        CoroutineScope(Dispatchers.Main).launch {
            if (viewModel.getRowCount() == 0) {

                Companion.viewModel.insertNewData("Dheeraj Paswan", "I am a software engineer","$day/$month/$year",)
            }
        }


        CoroutineScope(Dispatchers.Main).launch {
            viewModel.getReverseList()
        }

        viewModel.noteList?.observe(this) { newList ->
            adapter = AdapterOfNotes(viewModel.noteList,::deleteUser,::editData)
            binding.RVOfNotes.adapter = adapter
            adapter.notifyDataSetChanged()
        }

        binding.fab.setOnClickListener {
            intent = Intent(this, AddAndEditItem::class.java)
            startActivity(intent)
        }
    }
    fun editData(id:Int,title:String,description:String,date:String){
        intent = Intent(this, AddAndEditItem::class.java)
        intent.putExtra("data1",id)
        intent.putExtra("data2",title)
        intent.putExtra("data3",description)
        intent.putExtra("data4",date)
        startActivity(intent)
    }

    fun deleteUser(id:Int){


        val dialog = Dialog(this).apply {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            setCancelable(false)
            setContentView(R.layout.permition_for_delete)
        }
        dialog.show()
        dialog.findViewById<TextView>(R.id.yes_btn).setOnClickListener {
            dialog.dismiss()
            CoroutineScope(Dispatchers.Main).launch {
                viewModel.deleteData(id)
            }
            Toast.makeText(this, "Deleted Successfully", Toast.LENGTH_SHORT).show()

        }
        dialog.findViewById<TextView>(R.id.no_btn).setOnClickListener {
            dialog.dismiss()
            Toast.makeText(this, "Cancelled", Toast.LENGTH_SHORT).show()
        }

//        CoroutineScope(Dispatchers.Main).launch {
//            viewModel.deleteData(id)}
    }
    fun descList(){
        CoroutineScope(Dispatchers.Main).launch {
            viewModel.descList()
        }
    }
    fun ascendingList(){
        CoroutineScope(Dispatchers.Main).launch {
            viewModel.ascList()
        }
    }

    fun reverseList(){
        CoroutineScope(Dispatchers.Main).launch {
            viewModel.getList()
        }
    }
    companion object{
        lateinit  var viewModel: ViewModelOfNotes
        lateinit var  adapter:AdapterOfNotes
        val c = Calendar.getInstance()

    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings ->{ descList()
                return true}
            R.id.ascending ->{ ascendingList()
                return true}
            R.id.reverse ->{ reverseList()
                return true}
            else -> super.onOptionsItemSelected(item)
        }
    }


}