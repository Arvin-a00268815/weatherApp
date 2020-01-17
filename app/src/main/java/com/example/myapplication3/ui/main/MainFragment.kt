package com.example.myapplication3.ui.main

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.SavedStateViewModelFactory
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication3.R
import com.example.myapplication3.TemperatureConverter
import com.example.myapplication3.databinding.MainFragmentBinding
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
        // Specify the current activity as the lifecycle owner.

        viewModel = ViewModelProvider(activity!!,
            SavedStateViewModelFactory(activity!!.application, activity!!)).get(MainViewModel::class.java)

        val binding = DataBindingUtil.bind<MainFragmentBinding>(view)
        binding!!.lifecycleOwner = this

        binding.vm = viewModel


        viewModel.onNoInternet().observe(viewLifecycleOwner, Observer {

            Toast.makeText(this.context, "No internet", Toast.LENGTH_LONG).show()
        })

        viewModel.getWeatherDetails().observe(viewLifecycleOwner, Observer{



            fahrenheit_textView.text = TemperatureConverter.celsiusToFahrenheit(it.weather!!.temp).toString()
            degree_textView.text = it.weather!!.temp.toString()
            wind_speed.text = it.clouds!!.cloudiness.toString()


        })

        sd_button.setOnClickListener {

            viewModel.getStandardDev().observe(viewLifecycleOwner, Observer {

                sd_textView.text = it.toString()
            })

        }




    }




}
