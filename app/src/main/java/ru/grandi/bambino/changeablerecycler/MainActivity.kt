package ru.grandi.bambino.changeablerecycler

import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.observe
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private var adapter: MainAdapter? = null
    private lateinit var changeableRecycler: RecyclerView

    private val mainViewModel by viewModel<MainViewModel>()

    private var deleteItemListener = object : DeleteItemListener {
        override fun onClick(position: Int) {
            adapter?.deleteItem(position)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()
        initField()
        mainViewModel.asyncCreateItem()
        mainViewModel.createItemLiveData.observe(this) { item ->
            if (adapter == null) {
                initRecycler(item)
            } else {
                adapter?.updateData(item)
            }
        }
    }

    override fun onStop() {
        super.onStop()
        mainViewModel.createItemLiveData.removeObserver {}
    }

    private fun getOrientationScreen() : Boolean {
        return resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT
    }

    private fun initRecycler(data: MutableList<ChangeableItem>) {
        adapter = MainAdapter(data, deleteItemListener)
        if (getOrientationScreen()){
            changeableRecycler.layoutManager =
                GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false)
        } else {
            changeableRecycler.layoutManager =
                GridLayoutManager(this, 4, GridLayoutManager.VERTICAL, false)
        }
        changeableRecycler.adapter = adapter
    }

    private fun initField() {
        changeableRecycler = findViewById(R.id.changeable_recycler_view)
        PULL_DELETE_ITEM = mutableListOf()
    }
}