package com.example.contact2

import android.Manifest
import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
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
    private val PICK_IMAGE = 100
    val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            // Permission is granted. Continue the action or workflow in your
            // app.
            Log.d("Permission", "Granted")
        } else {
            // Explain to the user that the feature is unavailable because the
            // features requires a permission that the user has denied. At the
            // same time, respect the user's decision. Don't link to system
            // settings in an effort to convince the user to change their
            // decision.
            Log.d("Permission", "Denied")
        }
    }

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

        //選照片囉!
        binding.pickImage.setOnClickListener {
            requestPermission()
            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(gallery, PICK_IMAGE)
        }


        val itemId = navigationArgs.itemId
        if (itemId > 0) {

        } else {
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
                Toast.makeText(
                    activity,
                    "inserted\n" +
                            "vocEnglish = ${vocEnglishText.text.toString()}\n" +
                            "vocChinese = ${vocChineseText.text.toString()}\n" +
                            "phone = ${phoneText.text.toString()}\n" +
                            "email   = ${emailText.text.toString()}\n" +
                            "birthday      = ${birthdayText.text.toString()}\n" +
                            "imageUri      = ${imageUriText.text.toString()}\"", Toast.LENGTH_LONG
                ).show()

                // step3: use cResolver to insert data, two params are:
                //        cResolver.insert(targetTableUri, contentValues)
                cResolver?.insert(dbUri, contentValues)
                cursor?.requery()
                //cResolver?.release()
                //cursor?.close()
                findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
            }
        }

        deleteBtn.setOnClickListener {

            if (cursor?.moveToFirst()!!) {
                var id = cursor.getInt(0) // id is at column position 0 of the table
                val uri2 = Uri.withAppendedPath(dbUri, idColumn.toString())
                val vocEng = cursor.getString(1)
                val vocCn = cursor.getString(2)
                val phoneString = cursor.getString(3)
                val emailString = cursor.getString(4)
                val birthdayString = cursor.getString(5)
                val imageUri2 = cursor.getString(6)

                Toast.makeText(activity, "You deleted current item !", Toast.LENGTH_LONG).show()

                // delete(targetTableUri, SqlWhereClause, Array<String!>?)
                cResolver.delete(
                    uri2,
                    "vocEnglish=? and vocChinese=? and phone=? and email=? and birthday=? and imageUri=?",
                    arrayOf(
                        vocEng,
                        vocCn,
                        phoneString,
                        emailString,
                        birthdayString,
                        imageUri2,
                        idColumn.toString()
                    )
                )

                cursor.requery()
            }
        }
    }

    private fun requestPermission() {
        when {
            ContextCompat.checkSelfPermission(
                this.requireContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED -> {
                //  permission is granted

            }
            ActivityCompat.shouldShowRequestPermissionRationale(
                this.requireActivity(),
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) -> {
                // Adsictional rationale should be display
                requestPermissionLauncher.launch(
                    Manifest.permission.READ_EXTERNAL_STORAGE
                )
            }
            else -> {
                // Permissison has not been asked yet
                requestPermissionLauncher.launch(
                    Manifest.permission.READ_EXTERNAL_STORAGE
                )
            }
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == PICK_IMAGE && data != null) {
            val selectedImage: Uri = data.data!!
            val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)
            val cursor: Cursor? =
                activity?.getContentResolver()
                    ?.query(selectedImage, filePathColumn, null, null, null)
            if (cursor != null) {
                cursor.moveToFirst()
                val columnIndex: Int = cursor.getColumnIndex(filePathColumn[0])
                //  filePath是圖片的檔案
                val filePath: String = cursor.getString(columnIndex)
                cursor.close()
                val yourSelectedImage = BitmapFactory.decodeFile(filePath)

                //  path to uri
                binding.image.setImageURI(Uri.parse(filePath))
                binding.imageUri.setText(filePath)
            }
            //Now do whatever processing you want to do on it.
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}