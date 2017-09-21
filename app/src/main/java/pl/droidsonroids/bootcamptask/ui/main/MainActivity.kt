package pl.droidsonroids.bootcamptask.ui.main

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import pl.droidsonroids.bootcamptask.R
import pl.droidsonroids.bootcamptask.model.Image
import pl.droidsonroids.bootcamptask.ui.details.DetailsActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView.apply {
            layoutManager = GridLayoutManager(this@MainActivity, 2)
            adapter = ImageAdapter(generateImages(), { image, imageView, titleView -> DetailsActivity.showDetailsActivity(this@MainActivity, image, imageView, titleView) })
        }
        setSupportActionBar(toolbar)
    }

    private fun generateImages(): List<Image> {
        val images = mutableListOf<Image>()
        for (i in (0..99)) {
            images.add(Image.values()[i % Image.values().size])
        }
        return images
    }
}

