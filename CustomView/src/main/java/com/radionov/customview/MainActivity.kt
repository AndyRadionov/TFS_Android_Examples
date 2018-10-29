package com.radionov.customview

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.chip.Chip
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val stationClickListener = View.OnClickListener { stationView ->
        if (stationView.parent == stations_container_1) {
            swapContainer(stations_container_1, stations_container_2, stationView)
        } else {
            swapContainer(stations_container_2, stations_container_1, stationView)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        for (i in 0 until stations.size) {
            if (i % 2 == 0) {
                addStationView(stations_container_1, stations[i].first, stations[i].second)
            } else {
                addStationView(stations_container_2, stations[i].first, stations[i].second)
            }
        }
    }

    private fun swapContainer(container1: CustomViewGroup, container2: CustomViewGroup, view: View) {
        container1.removeView(view)
        container2.addView(view)
    }

    private fun addStationView(container: CustomViewGroup, text: String, color: Int) {
        val stationView = layoutInflater.inflate(R.layout.child_view, container, false) as Chip

        stationView.text = text
        stationView.chipIconTint = ColorStateList.valueOf(color)
        stationView.closeIconTint = ColorStateList.valueOf(color)
        stationView.setTextColor(color)

        stationView.setOnClickListener(stationClickListener)
        stationView.setOnCloseIconClickListener(stationClickListener)

        container.addView(stationView)
    }

    companion object {
        private val darkGreenColor = Color.parseColor("#FF388E3C")
        private val orangeColor = Color.parseColor("#FFFFA500")
        private val stations = listOf (
                "Сокольники" to Color.RED,
                "Фрунзенская" to Color.RED,
                "Спортивная" to Color.RED,
                "Университет" to Color.RED,
                "Водный стадион" to darkGreenColor,
                "Ховрино" to darkGreenColor,
                "Сокол" to darkGreenColor,
                "Павелецкая" to darkGreenColor,
                "Белорусская" to darkGreenColor,
                "Крылатское" to Color.BLUE,
                "Молодежная" to Color.BLUE,
                "Электрозаводская" to Color.BLUE,
                "Спартак" to Color.MAGENTA,
                "Кузнецкий Мост" to Color.MAGENTA,
                "Таганская" to Color.MAGENTA,
                "Пролетарская" to Color.MAGENTA,
                "Выхино" to Color.MAGENTA,
                "Медведково" to orangeColor,
                "ВДНХ" to orangeColor,
                "Профсоюзная" to orangeColor
        )
    }
}
