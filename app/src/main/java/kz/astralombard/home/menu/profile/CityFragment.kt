package kz.astralombard.home.menu.profile


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.fragment_city.*
import kz.astralombard.R
import kz.astralombard.base.ui.BaseFragment


class CityFragment : BaseFragment() {



    companion object {
        const val TAG = "CityFragment"
        fun newInstance() = CityFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_city, container, false)
    }

    override fun onStart() {
        super.onStart()

        ArrayAdapter.createFromResource(
            context,
            R.array.planets_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }
    }


}
