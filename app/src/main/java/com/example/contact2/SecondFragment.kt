package com.example.contact2

import android.content.ContentValues
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.contact2.databinding.FragmentSecondBinding

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val navigationArgs: SecondFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // TODO: 從 floating button 新增資料時，不能顯示 deleteBtn
        //  declare a val for addData button and setOnClickListener
        //  use contentResolver.insert(TargetTableToInsertTo: Uri, contentValues)

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d("title", navigationArgs.titleAction!!)
        /*binding.saveAction.setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }*/

        val AUTHORITY = "com.example.inventory"
        val URL = "content://$AUTHORITY/item"
        val dbUri: Uri = Uri.parse(URL)
        val CONTACT_TABLE_NAME = "item"

        // because here is fragment, we need to get the activity first then get the resolver
        val cResolver = requireActivity().contentResolver.acquireContentProviderClient(dbUri)
        val cursor: Cursor? = cResolver?.query(dbUri, null, null, null, null)

        // column names of the table
        val id = "_id"
        val vocEnglish = "vocEnglish"
        val vocChinese = "vocChinese"
        val phone = "phone"
        val birthday = "birthday"
        val email = "email"
        val photo = "photo"
        val imageUri = "imageUri"
        val idColumn = 0

        // editTexts names in second fragment
        val vocEnglishText = binding.vocEnglish
        val vocChineseText = binding.vocChinese
        val phoneText = binding.phone
        val emailText = binding.email
        val birthdayText = binding.date
        val imageUriText = binding.imageUri

        // button names in second fragment
        val insertSaveBtn = binding.saveAction
        val deleteBtn = binding.deleteAction
        val saveUpdateBtn = binding.saveAction
        // val queryNextBtn

        insertSaveBtn.setOnClickListener {
            Log.w("InsertSaveBtn", "Into the section of InsertSaveBtn !")

            // step1: define contentValues with user entered data
            // ContentValues are key -> value pairs, for cResolver to process
            // the put() will put values according to given column names(first param)
            val contentValues = ContentValues()
            // put(columnName, valueOfTheData)
            contentValues.put(vocEnglish, binding.vocEnglish.text.toString())
            contentValues.put(vocChinese, binding.vocChinese.text.toString())
            contentValues.put(phone, binding.phone.text.toString())
            contentValues.put(email, binding.email.text.toString())
            contentValues.put(birthday, binding.date.text.toString())
            contentValues.put(imageUri, binding.imageUri.text.toString())

            // step2: Show success toast when operation is completed
            Toast.makeText(activity,
                "inserted\n" +
                        "vocEnglish = ${vocEnglishText.text.toString()}\n" +
                        "vocChinese = ${vocChineseText.text.toString()}\n" +
                        "phone = ${phoneText.text.toString()}\n" +
                        "email   = ${emailText.text.toString()}\n" +
                        "birthday      = ${birthdayText.text.toString()}\n" +
                        "imageUri      = ${imageUriText.text.toString()}\""
                , Toast.LENGTH_LONG).show()

            // step3: use cResolver to insert data, two params are:
            //        cResolver.insert(targetTableUri, contentValues)
            cResolver?.insert(dbUri, contentValues)
            cursor?.requery()
            //cResolver?.release()
            //cursor?.close()
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }

        deleteBtn.setOnClickListener {

            if(cursor?.moveToFirst()!!) {
                var id = cursor.getInt(0) // id is at column position 0 of the table
                val uri2 = Uri.withAppendedPath(dbUri, idColumn.toString())
                val vocEng = cursor.getString(1)
                val vocCn = cursor.getString(2)
                val phoneString = cursor.getString(3)
                val emailString = cursor.getString(4)
                val birthdayString = cursor.getString(5)
                val imageUri2 = cursor.getString(6)

                Toast.makeText(activity,"You deleted current item !", Toast.LENGTH_LONG).show()

                // delete(targetTableUri, SqlWhereClause, Array<String!>?)
                cResolver.delete(uri2
                    ,"vocEnglish=? and vocChinese=? and phone=? and email=? and birthday=? and imageUri=?"
                    , arrayOf(vocEng, vocCn, phoneString, emailString, birthdayString, imageUri2, idColumn.toString()))

                cursor.requery()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}