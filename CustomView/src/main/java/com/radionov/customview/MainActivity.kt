package com.radionov.customview

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.transition.ChangeTransform
import androidx.transition.TransitionManager
import com.google.android.material.chip.Chip
import kotlinx.android.synthetic.main.activity_main.*
import java.util.ArrayList

class MainActivity : AppCompatActivity() {

    private val stationClickListener = View.OnClickListener { v ->
        val stationView = v.parent as View
        val name = stationView.tag as String
        if (stationView.parent == stations_container_1) {
            swapList(firstStations, secondStations, name)
            swapContainer(stations_container_1, stations_container_2, stationView)
        } else {
            swapList(secondStations, firstStations, name)
            swapContainer(stations_container_2, stations_container_1, stationView)
        }
    }

    private lateinit var firstStations: ArrayList<String>
    private lateinit var secondStations: ArrayList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initLists(savedInstanceState)
        initContainers()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putSerializable(EXTRA_FIRST_STATIONS, firstStations)
        outState.putSerializable(EXTRA_SECOND_STATIONS, secondStations)
    }

    private fun initLists(savedInstanceState: Bundle?) {
        if (savedInstanceState != null) {
            firstStations = savedInstanceState
                    .getSerializable(EXTRA_FIRST_STATIONS) as ArrayList<String>
            secondStations = savedInstanceState
                    .getSerializable(EXTRA_SECOND_STATIONS) as ArrayList<String>
        } else {
            firstStations = ArrayList()
            secondStations = ArrayList()
            for ((index, entry) in stations.entries.withIndex()) {
                if (index % 2 == 0) {
                    firstStations.add(entry.key)
                } else {
                    secondStations.add(entry.key)
                }
            }
        }
    }

    private fun initContainers() {
        for (station in firstStations) {
            stations_container_1.addView(getStationView(station, stations[station]!!))
        }

        for (station in secondStations) {
            stations_container_2.addView(getStationView(station, stations[station]!!))
        }
    }

    private fun swapList(firstList: ArrayList<String>, secondList: ArrayList<String>, name: String) {
        firstList.remove(name)
        secondList.add(name)
    }

    private fun swapContainer(container1: CustomViewGroup, container2: CustomViewGroup, view: View) {
        container1.removeView(view)
        container2.addView(view)
    }

    private fun getStationView(text: String, color: Int): View {
        val stationView = layoutInflater.inflate(R.layout.child_view, null, false)
        val stationChip = stationView.findViewById<Chip>(R.id.item_station)

        stationChip.text = text
        stationChip.chipIconTint = ColorStateList.valueOf(color)
        stationChip.closeIconTint = ColorStateList.valueOf(color)
        stationChip.setTextColor(color)

        stationChip.setOnClickListener(stationClickListener)
        stationChip.setOnCloseIconClickListener(stationClickListener)

        stationView.tag = text
        return stationView
    }

    companion object {
        const val EXTRA_FIRST_STATIONS = "first_stations"
        const val EXTRA_SECOND_STATIONS = "second_stations"

        private val darkGreenColor = Color.parseColor("#FF388E3C")
        private val orangeColor = Color.parseColor("#FFFFA500")
        private val stations = mapOf(
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
