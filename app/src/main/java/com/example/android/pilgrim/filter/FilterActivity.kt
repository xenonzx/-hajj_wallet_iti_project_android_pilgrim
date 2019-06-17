package com.example.android.pilgrim.filter

import android.app.Activity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.android.pilgrim.R
import com.example.android.pilgrim.model.responses.CategoryNationalityResponse
import com.example.android.pilgrim.nationality.FilterViewModel
import kotlinx.android.synthetic.main.content_filter.*

class FilterActivity : AppCompatActivity() {

    private lateinit var viewModel: FilterViewModel

    private var checkedId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.android.pilgrim.R.layout.activity_filter)

        val toolbar = findViewById<Toolbar>(com.example.android.pilgrim.R.id.toolbar)
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true);

        viewModel = ViewModelProviders.of(this).get(FilterViewModel::class.java)

        progress_bar.visibility = View.VISIBLE
        viewModel.getCategories()

        viewModel.response.observe(this, Observer { categories ->


            if (categories != null) {
                val allCategories: ArrayList<CategoryNationalityResponse> =
                    ArrayList<CategoryNationalityResponse>()

                allCategories.add(CategoryNationalityResponse("All", 0))

                categories.forEach { category ->
                    allCategories.add(category)
                }

                allCategories.forEach { category ->

                    val radioButton = RadioButton(this)
                    radioButton.id = category.id!!
                    radioButton.text = category.name!!
                    radio_group.addView(radioButton)
                }

                radio_group.setOnCheckedChangeListener(object : RadioGroup.OnCheckedChangeListener {
                    override fun onCheckedChanged(group: RadioGroup, checkedId: Int) {
                        // checkedId is the RadioButton selected
                        this@FilterActivity.checkedId = checkedId

                    }
                })

                val filter = intent.getIntExtra("ChosenFilter", 0)
                radio_group.check(filter)
            } else {
                Toast.makeText(this, getString(R.string.error), Toast.LENGTH_SHORT).show()
            }
            progress_bar.visibility = View.INVISIBLE

        })
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home ->
                onBackPressed()

            R.id.btn_done -> {
                val returnIntent = intent
                returnIntent.putExtra("filter", checkedId)
                setResult(Activity.RESULT_OK, returnIntent)
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.filter_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
}
