package com.example.contact2

import android.content.ClipData
import android.database.Cursor
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

/**
 * [ListAdapter] implementation for the recyclerview.
 */
class FirstFragmentAdapter(
    private val cursor: Cursor
) :
    RecyclerView.Adapter<FirstFragmentAdapter.ItemViewHolder>() {

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


//        fun bind(item: ClipData.Item, cursor: Cursor) {
//
//
//            binding.apply {
//                vocEnglish.text = item.vocEnglish
//                vocChinese.text = item.vocChinese
//                val dateFormat: DateFormat = SimpleDateFormat("yyyy.MM.dd")
//                birthday.text = dateFormat.format(item.birthday)
//                imageView.setImageURI(item.photo)
//                phone.text = item.phone
//                email.text = item.email
//
//
//                if (item.vocFavorite == true) {
//                    vocFavorite.setBackgroundResource(R.drawable.ic_star)
//                } else {
//                    vocFavorite.setBackgroundResource(R.drawable.ic_star_border)
//                }
//                vocFavorite.setOnClickListener {
//                    viewModel.updateItem(
//                        item.id,
//                        item.vocEnglish,
//                        item.vocChinese,
//                        !item.vocFavorite,
//                        item.birthday,
//                        item.phone,
//                        item.email,
//                        item.photo
//                    )
//                }
//            }
//        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_first_item, parent, false)
        return ItemViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
//        val current = getItem(position)
//        holder.itemView.setOnClickListener {
//            onItemClicked(current)
//        }

//        holder.bind(current, cursor)
    }

    override fun getItemCount(): Int {
        return cursor.count
    }

    //    class ItemViewHolder(view: View?) : RecyclerView.ViewHolder(view!!) {
//        val dogImage = view?.findViewById<ImageView>(R.id.dog_image)
//        val dogName = view?.findViewById<TextView>(R.id.dog_name)
//        val dogAge = view?.findViewById<TextView>(R.id.dog_age)
//        val dogHobbies = view?.findViewById<TextView>(R.id.dog_hobbies)
//        val cardView = view?.findViewById<MaterialCardView>(R.id.card_view)
//    }


//    companion object {
//        private val DiffCallback = object : DiffUtil.ItemCallback<ClipData.Item>() {
//            override fun areItemsTheSame(oldItem: ClipData.Item, newItem: ClipData.Item): Boolean {
//                return oldItem === newItem
//            }
//
//            override fun areContentsTheSame(
//                oldItem: ClipData.Item,
//                newItem: ClipData.Item
//            ): Boolean {
//                return oldItem.vocEnglish == newItem.vocEnglish
//            }
//        }
//    }
}