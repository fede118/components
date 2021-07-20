package com.section11.demoapp

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.section11.components.recycler.listener.RecyclerItemClickListener
import com.section11.components.recycler.model.ViewHolderModel
import com.section11.demoapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), RecyclerItemClickListener {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val testList = arrayListOf<ViewHolderModel>().apply {
            for (i in 1..6) {
                add(TestDto("$i", "title $i", "https://imdb-api.com/images/original/MV5BNTlkZDQ1ODEtY2ZiMS00OGNhLWJlZDctYzY0NTFmNmQ2NDAzXkEyXkFqcGdeQXVyMTkxNjUyNQ@@._V1_Ratio0.6699_AL_.jpg"))
            }
        }

        binding.recyclerComponent.title = "Recycler title"
        binding.recyclerComponent.bind(testList, this)
    }

    override fun onRecyclerItemClicked(model: ViewHolderModel) {
        Toast.makeText(this, "Touched: ${model.getTitle()}", Toast.LENGTH_SHORT).show()
    }
}

internal class TestDto(
    private val id: String,
    private val title: String,
    private val image: String
) : ViewHolderModel {
    override fun getId(): String? {
        return id
    }

    override fun getTitle(): String {
        return title
    }

    override fun getImageUrl(): String {
        return image
    }
}