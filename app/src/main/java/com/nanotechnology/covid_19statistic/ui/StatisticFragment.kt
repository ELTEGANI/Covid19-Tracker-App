package com.nanotechnology.covid_19statistic.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.EntryXComparator
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.nanotechnology.covid_19statistic.R
import com.nanotechnology.covid_19statistic.databinding.StatisticFragmentBinding
import com.nanotechnology.covid_19statistic.util.convertLongToDate
import com.nanotechnology.covid_19statistic.util.convertLongToDateString
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import kotlin.collections.ArrayList
import kotlinx.android.synthetic.main.chart_bottom_sheet.view.*
import kotlinx.coroutines.ExperimentalCoroutinesApi

@AndroidEntryPoint
class StatisticFragment : Fragment() {

    @ExperimentalCoroutinesApi
    private val statisticViewModel:StatisticViewModel by viewModels()

    private lateinit var binding: StatisticFragmentBinding

    @ExperimentalCoroutinesApi
    @SuppressLint("SetTextI18n")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.statistic_fragment, container, false)

        binding.lifecycleOwner = this.viewLifecycleOwner

        statisticViewModel.statistic.observe(viewLifecycleOwner, { statistics ->
             if (statistics != null) {
                 binding.totalCasesTextView.text = statistics.cases.toString()
                 binding.totalDeadTextView.text = statistics.deaths.toString()
                 binding.totalRecoveriesTextView.text = statistics.recovered.toString()
                 binding.lastUpdateTextView.text = getString(R.string.update_in) + " " + convertLongToDateString(
                     statistics.updated
                 )
             }
         })
        setHasOptionsMenu(true)
        return binding.root
    }

    @ExperimentalCoroutinesApi
    override fun onOptionsItemSelected(item: MenuItem) =
        when (item.itemId) {
            R.id.menu_dark_mode -> {
                // Get new mode.
                val mode = if ((resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) ==
                             Configuration.UI_MODE_NIGHT_NO) {
                    AppCompatDelegate.MODE_NIGHT_YES
                } else {
                    AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY
                }
                // Change UI Mode
                AppCompatDelegate.setDefaultNightMode(mode)
                true
            }

            R.id.menu_share_statistic -> {
                val sendIntent: Intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(
                        Intent.EXTRA_TEXT, getString(R.string.cases_confirmed) + binding.totalCasesTextView.text.toString() +
                                "\n" + getString(R.string.recovery_cases) + binding.totalRecoveriesTextView.text.toString() +
                                "\n" + getString(R.string.deaths_cases) + binding.totalDeadTextView.text.toString() +
                                "\n" + binding.lastUpdateTextView.text.toString())
                    type = "text/plain"
                }
                val shareIntent = Intent.createChooser(sendIntent, null)
                startActivity(shareIntent)
                true
            }

            R.id.menu_chart -> {
                openDialogChart()
                true
            }

            else -> false
        }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.statistic_menu, menu)
    }

    @ExperimentalCoroutinesApi
    private fun openDialogChart() {
        val dialogBinding = DataBindingUtil
        .inflate<ViewDataBinding>(LayoutInflater.from(context), R.layout.chart_bottom_sheet, null, false)
        val dialog = context?.let { BottomSheetDialog(it) }
        dialog?.setContentView(dialogBinding.root.rootView)
        statisticViewModel.deathsAndUpdated.observe(viewLifecycleOwner, Observer {
            val entries: MutableList<PieEntry> = ArrayList()
            Collections.sort(entries, EntryXComparator())
            it.forEach { i ->
                entries.add(PieEntry(i.deaths.toFloat(), convertLongToDate(i.updated)))
            }
            val pieDataSet = PieDataSet(entries, "")
            pieDataSet.setColors(
                intArrayOf(
                    R.color.covid19_green,
                    R.color.covid19_blue_200,
                    R.color.covid19_red,
                    R.color.covid19_yellow
                ), context
            )
            val pieData = PieData(pieDataSet)
            val description: Description = dialogBinding.root.rootView.chart.description
            description.isEnabled = true
            description.text = getString(R.string.statistics_deaths_per_time)
            description.textSize = 16f
            dialogBinding.root.rootView.chart.data = pieData
            dialogBinding.root.rootView.chart.invalidate() // refresh
        })
        dialog?.show()
    }
}
