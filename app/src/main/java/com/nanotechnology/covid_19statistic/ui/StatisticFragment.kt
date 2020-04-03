package com.nanotechnology.covid_19statistic.ui

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.EntryXComparator
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.nanotechnology.covid_19statistic.MyApplication
import com.nanotechnology.covid_19statistic.R
import com.nanotechnology.covid_19statistic.databinding.StatisticFragmentBinding
import com.nanotechnology.covid_19statistic.util.convertLongToDate
import com.nanotechnology.covid_19statistic.util.convertLongToDateString
import kotlinx.android.synthetic.main.chart_bottom_sheet.view.*
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList


class StatisticFragment : Fragment() {


    @Inject
    lateinit var statisticViewModel: StatisticViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity!!.applicationContext as MyApplication).appComponent.inject(this)
    }

    lateinit var binding: StatisticFragmentBinding

    @SuppressLint("SetTextI18n")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.statistic_fragment,container,false)

        binding.lifecycleOwner = this.viewLifecycleOwner

        binding.statisticResult = statisticViewModel.statistic

         statisticViewModel.statistic.observe(viewLifecycleOwner, Observer {
             binding.totalCasesTextView.text = it.data?.cases.toString()
             binding.totalDeadTextView.text = it.data?.deaths.toString()
             binding.totalRecoveriesTextView.text = it.data?.recovered.toString()
             binding.lastUpdateTextView.text = "Updated In"+" "+it.data?.updated?.let { it1 ->
                 convertLongToDateString(it1)
             }
         })
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onOptionsItemSelected(item: MenuItem) =
        when (item.itemId) {
            R.id.menu_dark_mode -> {
                true
            }
            R.id.menu_share_statistic -> {
                val sendIntent: Intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(
                        Intent.EXTRA_TEXT,"Cases Confirmed:"+binding.totalCasesTextView.text.toString()
                                +"\n"+ "Recovery:"+binding.totalRecoveriesTextView.text.toString()
                                +"\n"+ "Deaths:"+binding.totalDeadTextView.text.toString()
                                +"\n"+ binding.lastUpdateTextView.text.toString())
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

    private fun openDialogChart() {
        val dailogBinding = DataBindingUtil
        .inflate<ViewDataBinding>(LayoutInflater.from(context),R.layout.chart_bottom_sheet,null,false)
        val dialog = context?.let { BottomSheetDialog(it) }
        dialog?.setContentView(dailogBinding.root.rootView)
        statisticViewModel.deathsAndUpdated.observe(viewLifecycleOwner, Observer {
            val entries: MutableList<PieEntry> = ArrayList()
            Collections.sort(entries, EntryXComparator())
            for (i in it) {
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
            val description: Description = dailogBinding.root.rootView.chart.description
            description.isEnabled = true
            description.text = "Statistics Deaths Per Time"
            description.textSize = 16f
            dailogBinding.root.rootView.chart.data = pieData
            dailogBinding.root.rootView.chart.invalidate() // refresh
        })
        dialog?.show()
    }

}
