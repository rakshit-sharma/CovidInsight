package com.minorProject.covidinsight.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.minorProject.covidinsight.CautionModel
import com.minorProject.covidinsight.CautionRVAdapter
import com.minorProject.covidinsight.R


class CautionFragment : Fragment() {

    private lateinit var cautionsRV : RecyclerView
    private lateinit var cautionRVAdapter : CautionRVAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_caution, container, false)

        cautionsRV = view.findViewById(R.id.CautionRV) as RecyclerView


        val cautionsList = generateList()
        cautionRVAdapter = CautionRVAdapter(cautionsList)
        cautionsRV.layoutManager = LinearLayoutManager(activity)
        cautionsRV.adapter = cautionRVAdapter

        cautionsRV.setHasFixedSize(true)

        return view
    }

    private fun generateList(): List<CautionModel> {
        val list = ArrayList<CautionModel>()


            val drawable = R.drawable.ic_precaution_arrow
            val item1 = CautionModel(drawable, "Maintain a safe distance from others (at least 1 metre), even if they don’t appear to be sick.")
            val item2 = CautionModel(drawable, "Wear a mask in public, especially indoors or when physical distancing is not possible.")
            val item3 = CautionModel(drawable, "Choose open, well-ventilated spaces over closed ones. Open a window if indoors.")
            val item4 = CautionModel(drawable, "Clean your hands often. Use soap and water, or an alcohol-based hand rub.")
            val item5 = CautionModel(drawable, "Get vaccinated when it’s your turn. Follow local guidance about vaccination.")
            val item6 = CautionModel(drawable, "Cover your nose and mouth with your bent elbow or a tissue when you cough or sneeze.")
            val item7 = CautionModel(drawable, "Stay home if you feel unwell.")
            list +=item1
            list +=item2
            list +=item3
            list +=item4
            list +=item5
            list +=item6
            list +=item7

        return list
    }

}