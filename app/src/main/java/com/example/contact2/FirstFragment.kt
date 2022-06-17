package com.example.contact2

import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.contact2.databinding.FragmentFirstBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val AUTHORITY = "com.example.inventory"
        val URL = "content://$AUTHORITY/item"
        val dbUri: Uri = Uri.parse(URL)
        val CONTACT_TABLE_NAME = "item"
        val idColumn = 0

        // because here is fragment, we need to get the activity first then get the resolver
        val cResolver = requireActivity().contentResolver.acquireContentProviderClient(dbUri)
        val cursor: Cursor? = cResolver?.query(dbUri, null, null, null, null)

        binding.buttonFirst.setOnClickListener {
            // navigate from first fragment to second
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }

        binding.fab.setOnClickListener { view ->
            /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()*/
            val directions = FirstFragmentDirections.actionFirstFragmentToSecondFragment(
                "Add Contact",
                null,
                null,
                null,
                null,
                null,
                null,
                0
            )
            this.findNavController().navigate(directions)
        }

        binding.deleteAction1.setOnClickListener {
            2
            val idEnterd = binding.itemId.text.toString()

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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}