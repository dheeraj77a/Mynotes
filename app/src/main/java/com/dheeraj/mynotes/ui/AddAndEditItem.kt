package com.dheeraj.mynotes.ui

import android.content.ClipDescription
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dheeraj.mynotes.R
import android.content.Intent
import android.util.Log
import android.view.Gravity
import android.view.KeyEvent
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.isInvisible
import androidx.core.widget.doOnTextChanged
import com.dheeraj.mynotes.databinding.ActivityAddAndEditItemBinding
import com.dheeraj.mynotes.ui.MainActivity.Companion.c
import com.dheeraj.mynotes.ui.MainActivity.Companion.viewModel

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Calendar

class AddAndEditItem : AppCompatActivity() {
    lateinit var binding: ActivityAddAndEditItemBinding

    var userTitle:String=""
    var userDescription:String=" "
    val year = c.get(Calendar.YEAR)
    val month = c.get(Calendar.MONTH)+1
    val day = c.get(Calendar.DAY_OF_MONTH)
    var id1: Int =-1
    var title1: String? = null
    var description1: String? =null
    var date1: String? =null
    var n=-1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddAndEditItemBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val intent = intent
        id1= intent.getIntExtra("data1",-1)
        title1= intent.getStringExtra("data2")
        description1= intent.getStringExtra("data3")
        date1= intent.getStringExtra("data4")

        if (id1!=-1){
            binding.save.visibility= View.INVISIBLE
            editData()
        }else{
            binding.btnEdit.visibility= View.INVISIBLE
            binding.date.setText("$day/$month/$year")
        }

        binding.save.setOnClickListener {
            saveData(binding.title.text.toString(),binding.description.text.toString())
    }
    }
    fun saveData( title0:String,description0: String){

        val title=title0
        val date="$day/$month/$year"
        val description=description0

        Log.e("----------Dheeraj--------","$title,$date,$description")
        if (title.isNotEmpty() && description.isNotEmpty()) {
            CoroutineScope(Dispatchers.Main).launch {
                viewModel.insertNewData(title, description, date)
                viewModel.getReverseList()
                Toast.makeText(this@AddAndEditItem, "Data Saved", Toast.LENGTH_SHORT).show()
            }
            Intent(this, MainActivity::class.java)
            finish()
        }
        else{
            binding.title.setHintTextColor(ContextCompat.getColor(this, R.color.red))
            binding.description.setHintTextColor(ContextCompat.getColor(this, R.color.red))
            val toast = Toast.makeText(this, "Please enter title or description", Toast.LENGTH_LONG)
            toast.setGravity(Gravity.TOP or Gravity.CENTER_HORIZONTAL, 0, 0)
            toast.show()
        }

    }

    fun editData(){
        binding.title.setText(title1)
        binding.description.setText(description1)
        binding.date.setText(date1)
        userTitle=binding.title.text.toString()
        userDescription=binding.description.text.toString()
        binding.title.doOnTextChanged { text, start, before, count ->
            userTitle=binding.title.text.toString()
        }
        binding.description.doOnTextChanged { text, start, before, count ->
            userDescription =binding.description.text.toString()
        }
        binding.btnEdit.setOnClickListener {
            if (userTitle.isNotEmpty() && userDescription.isNotEmpty()&&id1 != null){
                CoroutineScope(Dispatchers.Main).launch {
                    viewModel.editData(id1!!,userTitle,userDescription,"$day/$month/$year")
                    Toast.makeText(this@AddAndEditItem, "Data Updated", Toast.LENGTH_SHORT).show()
                }

                finish()
            }else{
                binding.title.setHintTextColor(ContextCompat.getColor(this, R.color.red))
                binding.description.setHintTextColor(ContextCompat.getColor(this, R.color.red))
                val toast = Toast.makeText(this, "Please enter title or description", Toast.LENGTH_LONG)
                toast.setGravity(Gravity.TOP or Gravity.CENTER_HORIZONTAL, 0, 0)
                toast.show()
            }
        }
    }


}
