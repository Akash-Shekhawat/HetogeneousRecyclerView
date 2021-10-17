package com.example.hetogeneousrecyclerview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MyCustomAdapter(private val context: Context, private val ViewList: List<Int>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

        //values to recognize view
        companion object {
            const val TEXT_RECYCLER_VIEW = 0
            const val IMAGE_RECYCLER_VIEW = 1
            const val  TEXT_IMAGE_RECYCLER_VIEW = 2
            const val LOADING = 3
        }

        //Creating viewholder for each viewtype

        //View that contains only Text
        class ViewHolderText(view: View) : RecyclerView.ViewHolder(view) {
            val textViewcard: TextView = view.findViewById(R.id.textViewRow)
        }

        //Loader
        class ViewHolderLoader(view: View) : RecyclerView.ViewHolder(view) {
            val progressBarView: ProgressBar = view.findViewById(R.id.progress_adp)
        }

        //View that contains only Image
        class ViewHolderImage(view: View) : RecyclerView.ViewHolder(view) {
            val imageViewcard: ImageView = view.findViewById(R.id.imageViewRow)
        }

        //View that contains both Text and Image
        class ViewHolderImageText(view: View) : RecyclerView.ViewHolder(view) {
            val cardTextView: TextView = view.findViewById(R.id.imageViewText)
            val cardImageView: ImageView = view.findViewById(R.id.imageView)
        }


        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            //Based on the view type value it inflates the view
            return when (viewType) {
                TEXT_RECYCLER_VIEW -> ViewHolderText(LayoutInflater.from(parent.context).inflate(R.layout.row_text, parent, false))
                IMAGE_RECYCLER_VIEW -> ViewHolderImage(LayoutInflater.from(parent.context).inflate(R.layout.row_image, parent, false))
                TEXT_IMAGE_RECYCLER_VIEW -> ViewHolderImageText(LayoutInflater.from(parent.context).inflate(R.layout.row_image_text, parent, false))
                LOADING -> ViewHolderLoader(LayoutInflater.from(parent.context).inflate(R.layout.loading_more, parent, false))

                else -> throw IllegalArgumentException("No ViewHolder")
            }
        }

        override fun getItemViewType(position: Int): Int {
            return ViewList[position]
        }

        override fun getItemCount(): Int {
            return ViewList.size
        }


        //Binds the view and set the data to respected views
        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            when(ViewList[position]) {
                TEXT_RECYCLER_VIEW -> {
                    val viewHolderText = holder as ViewHolderText
                    viewHolderText.textViewcard.text = context.getString(R.string.deals)
                }
                IMAGE_RECYCLER_VIEW -> {
                    val viewHolderImage = holder as ViewHolderImage
                    viewHolderImage.imageViewcard.setImageResource(R.drawable.sale)
                }
                TEXT_IMAGE_RECYCLER_VIEW -> {
                    val viewHolderImage = holder as ViewHolderImageText
                    viewHolderImage.cardImageView.setImageResource(R.drawable.textsale)
                    viewHolderImage.cardTextView.text = context.getString(R.string.imagetextview)
                }
                LOADING -> {

                }
            }

        }

}