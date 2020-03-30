package com.nanotechnology.covid_19statistic.ui

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.nanotechnology.covid_19statistic.MyApplication
import com.nanotechnology.covid_19statistic.R
import com.nanotechnology.covid_19statistic.databinding.StatisticFragmentBinding
import com.nanotechnology.covid_19statistic.util.convertLongToDateString
import javax.inject.Inject


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
                 convertLongToDateString(
                     it1
                 )
             }

         })
        return binding.root
    }

    override fun onOptionsItemSelected(item: MenuItem) =
        when (item.itemId) {
            R.id.menu_dark_mode -> {
                true
            }
            R.id.menu_share_statistic -> {
                true
            }
            R.id.menu_chart -> {
                true
            }
            R.id.menu_statistic_by_country ->{
                true
            }
            else -> false
        }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.statistic_menu, menu)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }

}
