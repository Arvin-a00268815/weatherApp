package com.example.myapplication3.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication3.R
import com.example.myapplication3.TemperatureConverter
import com.example.myapplication3.databinding.MainFragmentBinding
import com.example.myapplication3.network.retrofit.ApiBuilder
import com.example.myapplication3.network.retrofit.Repository
import kotlinx.android.synthetic.main.main_fragment.*

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

//        val binding = DataBindingUtil.inflate<MainFragmentBinding>(inflater,R.layout.main_fragment, container, false)
//        // Specify the current activity as the lifecycle owner.
//        binding.lifecycleOwner = this
//
//        return binding.root

        return inflater.inflate(R.layout.main_fragment, container, false)

    }




    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        val repo = Repository.getInstance(this.context!!, ApiBuilder.getInstance())
        val factory = MainViewModelFactory(requireActivity(), null, repo)
        viewModel = ViewModelProvider(activity!!, factory).get(MainViewModel::class.java)

        val binding = DataBindingUtil.bind<MainFragmentBinding>(view)
        binding!!.lifecycleOwner = this

        binding.vm = viewModel


        viewModel.onNoInternet().observe(viewLifecycleOwner, Observer {

            Toast.makeText(this.context, "No internet", Toast.LENGTH_LONG).show()
        })

        viewModel.fetchWeatherDetails().observe(viewLifecycleOwner, Observer{



            fahrenheit_textView.text = TemperatureConverter.celsiusToFahrenheit(it.weather.temp).toString()
            degree_textView.text = it.weather.temp.toString()
            wind_speed.text = it.wind.speed.toString()

            viewModel.checkCloudiness(it.clouds.cloudiness)

        })

        viewModel.observeStandardDev().observe(viewLifecycleOwner, Observer {
            Log.e("--","--")
            sd_textView.text = it.toString()
        })

        sd_button.setOnClickListener {

            viewModel.fetchStandardDev()

        }




    }




}
