package com.example.workleft

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AlertDialog

class MainActivity : AppCompatActivity() {
    lateinit var editText: EditText
    lateinit var button: Button
    lateinit var listView: ListView
    var itemlist=ArrayList<String>()
    var filehelper=helper()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        editText=findViewById(R.id.editTextid)
        button=findViewById(R.id.buttonid)
        listView=findViewById(R.id.listviewid)
        itemlist=filehelper.readData(this)

        var arrayadapter=ArrayAdapter(this,android.R.layout.simple_list_item_1,android.R.id.text1,itemlist)
        listView.adapter=arrayadapter
        button.setOnClickListener {

            val itemname:String = editText.text.toString()
            if(itemname==""  || itemname==" " || itemname=="  " || itemname=="   " || itemname=="    "
                    || itemname=="     " || itemname=="      "|| itemname=="       "){
                Toast.makeText(this,"Please type something first",Toast.LENGTH_SHORT).show()
            }else {
                itemlist.add(itemname)
                editText.setText("")
                filehelper.writeData(itemlist, applicationContext)
                arrayadapter.notifyDataSetChanged()
            }

        }
        listView.setOnItemClickListener { adapterView, view, position, l ->

               var alert=AlertDialog.Builder(this)
               alert.setTitle("Delete")
               alert.setMessage("Do you want to delete this item?")
            alert.setCancelable(false)
            alert.setNegativeButton(
                "No",DialogInterface.OnClickListener { dialogInterface, i ->
                    dialogInterface.cancel()
                }
            )
        alert.setPositiveButton("YES",DialogInterface.OnClickListener { dialogInterface, i ->
            itemlist.removeAt(position)
            arrayadapter.notifyDataSetChanged()
            filehelper.writeData(itemlist,applicationContext)

        })
            alert.create().show()

        }
    }
}