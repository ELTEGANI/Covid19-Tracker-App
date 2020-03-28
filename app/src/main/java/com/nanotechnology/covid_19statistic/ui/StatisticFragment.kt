package com.nanotechnology.covid_19statistic.ui

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.nanotechnology.covid_19statistic.MyApplication
import com.nanotechnology.covid_19statistic.R
import com.nanotechnology.covid_19statistic.databinding.StatisticFragmentBinding
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
             binding.textViewCases.text = "Cases"+it.data?.cases.toString()
             binding.textViewDeaths.text = "Death"+it.data?.deaths.toString()
             binding.textViewCovered.text = "Recovered"+it.data?.recovered.toString()
         })
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }

}
