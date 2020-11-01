package com.example.afop.activity

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.example.afop.R
import kotlinx.android.synthetic.main.fragment_market_sale.*

class MarketSaleSpinnerActivity : Activity(), AdapterView.OnItemSelectedListener{

    override fun onItemSelected(parent: AdapterView<*>, view: View, pos: Int, id: Long) {
        // An item was selected. You can retrieve the selected item using
        marketSaleKategorieTextView.text = parent.getItemAtPosition(pos).toString()
    }

    override fun onNothingSelected(parent: AdapterView<*>) {
        // Another interface callback
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val spinner: Spinner = findViewById(R.id.marketSaleKategorieSpinner)

        spinner.onItemSelectedListener = this
        ArrayAdapter.createFromResource(
            this,
            R.array.카테고리,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinner.adapter = adapter
        }
    }



}