package com.example.hetogeneousrecyclerview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.AbsListView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {


    private val ViewList = mutableListOf<Int>()
    private lateinit var recyclerView: RecyclerView
    private lateinit var customAdapter: MyCustomAdapter
    private lateinit var layoutManager: LinearLayoutManager
    var isScrolling:Boolean = false



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


//        Adds the data to list
        for (i in 1..5) {
            when {
                i % 2 == 0 -> {
                    ViewList.add(MyCustomAdapter.TEXT_RECYCLER_VIEW)
                }
                i % 3 == 0 -> {
                    ViewList.add(MyCustomAdapter.IMAGE_RECYCLER_VIEW)
                }
                else -> {
                    ViewList.add(MyCustomAdapter.TEXT_IMAGE_RECYCLER_VIEW)
                }
            }
        }

        //Adapter
        customAdapter = MyCustomAdapter(this, ViewList)
        recyclerView.adapter = customAdapter


        // Set layout for RecyclerView
        layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager



        //Paging

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            //Checking if the scrolling happened or not
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if(newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL)
                {
                    isScrolling = true
                }

            }

            //After reaching the last element it fetches the data
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val currentItems:Int = layoutManager.childCount
                val totalItems:Int = layoutManager.itemCount
                val scrollOutItems:Int = layoutManager.findFirstVisibleItemPosition()
                if (isScrolling && (currentItems + scrollOutItems == totalItems))
                {
                    isScrolling = false
                    Log.i("last", "Lastpage")
                    fetchData()
                }
            }

        })


    }

    // Function to fetch Data with a delay of 2 Seconds
    private fun fetchData() {
//        val progressBar = findViewById<ProgressBar>(R.id.progress)
//        progressBar.visibility = View.VISIBLE
//
        ViewList.add(MyCustomAdapter.LOADING)
        customAdapter.notifyItemInserted(ViewList.size-1)
        val oldItem = ViewList.size
        //Delays for 2 sec and then fetches data
        val handler = Handler()
        handler.postDelayed({
            ViewList.remove(MyCustomAdapter.LOADING)
            customAdapter.notifyItemRemoved(ViewList.size-1)
            for (i in 1..5) {
                when {
                    i % 2 == 0 -> {
                        ViewList.add(MyCustomAdapter.TEXT_RECYCLER_VIEW)
                    }
                    i % 3 == 0 -> {
                        ViewList.add(MyCustomAdapter.IMAGE_RECYCLER_VIEW)
                    }
                    else -> {
                        ViewList.add(MyCustomAdapter.TEXT_IMAGE_RECYCLER_VIEW)
                    }
                }

                customAdapter.notifyItemRangeInserted(oldItem, ViewList.size)

                //progressBar.visibility = View.GONE
            }
        }, 2000)
    }

}
