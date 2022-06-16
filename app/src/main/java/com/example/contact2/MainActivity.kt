package com.example.contact2

import android.net.Uri
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import com.example.contact2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    // TODO:
    //  Notes:
    //  1. set buttons' Listeners in the onCreate()
    //  2. moveToNext() means move to the next row of the item table
    //     想像一下 cursor正指著 table row 的畫面，想想 CRUD分別要做什麼事
    //  3. 每個操作都是先判斷 Cursor不為空的話，再做其他事情 (順便在 if裡面加個 Log.d(TAG, "Cursor is not null!"))
    //     加入 if(cursor!=null && cursor.getCount>0){ when(cursor.moveToNext(){ ... }) }, getCount() => 總資料項數
    //  4. getAnyType(int): 回傳第 int個 table column的值，依據型態改變 function名稱.
    //     ex: getString(0) means 回傳第 0個 column的值，且它的值是 string型態的
    //  5. contentResolver.query(CONTENT_URI, mProjection, selection(if null then all), selectionArgs, order(ORDER BY columnName) )
    //      5-1. 第一個參數指向 dbURI,第二個 projection是 StringArray,放所有colNames. ex: []:{ DB.colName1,DB.colName2,DB.colName3..... }
    //           第三個是選擇要查詢的 col,如果null就全部cols的資料都查,第四跳過,第五 order 就是 SQL的 ORDER BY,根據哪個 col做排序


    // TODO:
    //  1. Acknowledge what is Cursor
    //      1-1 ContentValues(): values: ContentValues()
    //          1-1.1. values.put(): to insert data for a column name. ex: values.put(columnName, values(different types))
    //          1-1.2. db.insert(tableName, null, values)
    //  2. Acknowledge how to use it
    //  3. How can Cursor work with ContentResolver
    //  4. How 3. can be use in our Room case

    // TODO: Button settings
    //  1. Add button functions at second fragment:
    //     When fields are filled, pressing the "SAVE" button to insert the data
    //  2. Delete button functions at second fragment
    //  3. Update button functions at ?????
    //  4. Query button functions at ????

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    // for this app to send request to our target db
    private var CONTENT_URI = Uri.parse("content://com.example.inventory.provider/item")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        binding.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }


}