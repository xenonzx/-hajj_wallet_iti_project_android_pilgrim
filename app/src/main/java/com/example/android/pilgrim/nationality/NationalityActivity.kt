package com.example.android.pilgrim.nationality

import android.app.Activity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.content_nationality.*


class NationalityActivity : AppCompatActivity() {
    private lateinit var viewModel: NationalityViewModel
    private lateinit var mAdapter: NationalityAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.android.pilgrim.R.layout.activity_nationality)

        val toolbar = findViewById<Toolbar>(com.example.android.pilgrim.R.id.toolbar)
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        viewModel = ViewModelProviders.of(this).get(NationalityViewModel::class.java)

        viewModel.getNationalities()

        recyclerView.layoutManager = LinearLayoutManager(this)

        viewModel.response.observe(this, Observer { nationalities ->
            mAdapter = NationalityAdapter(nationalities, this) { nationality, position: Int ->
                val returnIntent = intent
                returnIntent.putExtra("nationality", nationality)
                setResult(Activity.RESULT_OK, returnIntent)
                finish()
            }
            recyclerView.adapter = mAdapter
        })
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home)
            onBackPressed()
        return super.onOptionsItemSelected(item)
    }
}
