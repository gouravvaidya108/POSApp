package com.example.posapp.fragment

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.posapp.viewmodels.HomeViewModel

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.posapp.R
import com.example.posapp.adapters.ProductAdapter
import com.example.posapp.models.Product

class HomeFragment : Fragment() {
    private lateinit var viewModel: HomeViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var searchEditText: EditText
    private var skip = 0
    private val take = 10  // change this value to load more items at a time

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        recyclerView = view.findViewById(R.id.productRecyclerView)
        val displayMetrics = context?.resources?.displayMetrics
        val dpWidth = displayMetrics!!.widthPixels / displayMetrics.density
        val numberOfColumns = (dpWidth / 180).toInt()  // where 180 is your minimum column width in dp
        recyclerView.layoutManager = GridLayoutManager(context, numberOfColumns)

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!recyclerView.canScrollVertically(1) && searchEditText.text.isNullOrEmpty()) {
                    skip += take
                    viewModel.getProducts(skip, take)
                }
            }
        })

        searchEditText = view.findViewById(R.id.et_search)
        searchEditText.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH ||
                actionId == EditorInfo.IME_ACTION_DONE ||
                event?.action == KeyEvent.ACTION_DOWN &&
                event.keyCode == KeyEvent.KEYCODE_ENTER) {
                val adapter = recyclerView.adapter as ProductAdapter
                if (!v.text.isNullOrEmpty()) {
                    viewModel.searchProducts(v.text.toString())
                }else{
                    adapter.clearData()
                    viewModel.getProducts(skip, take)
                }
                val view = activity?.currentFocus
                view?.let { v ->
                    val imm =
                        activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager // or context
                    imm.hideSoftInputFromWindow(v.windowToken, 0)
                }
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        viewModel.getProducts(skip, take)

        viewModel.productLiveData.observe(viewLifecycleOwner, Observer { product ->
            if(product != null) {
                (recyclerView.adapter as? ProductAdapter)?.apply {
                    addData(product)
                } ?: run {
                    recyclerView.adapter = ProductAdapter(product as MutableList<Product>)
                }
            }
        })

        viewModel.searchLiveData.observe(viewLifecycleOwner, Observer { product ->
            if(product != null) {
                recyclerView.adapter = ProductAdapter(product as MutableList<Product>)
            }
        })

        viewModel.errorLiveData.observe(viewLifecycleOwner, Observer { errorMessage ->
            Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
        })
    }
}

