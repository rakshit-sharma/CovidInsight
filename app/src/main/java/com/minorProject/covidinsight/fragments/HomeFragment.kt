package com.minorProject.covidinsight.fragments

import android.os.Bundle
import android.renderscript.ScriptGroup
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.activity.OnBackPressedCallback
import com.minorProject.covidinsight.R


class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_home, container, false)

        val dataBtn : Button = view.findViewById(R.id.btnData)
        val mediBtn : Button = view.findViewById(R.id.btnMedi)
        val newsBtn : Button = view.findViewById(R.id.btnNews)
        val cautionBtn : Button = view.findViewById(R.id.btnCaution)


        dataBtn.setOnClickListener{
            val fragment = DatabaseFragment()   //navigate to Covid data fragment
            val transaction = fragmentManager?.beginTransaction()
            transaction?.replace(R.id.nav_host_fragment, fragment)?.commit()
        }

        mediBtn.setOnClickListener{
            val fragment = MediFragment()   //navigate to vaccination center fragment
            val transaction = fragmentManager?.beginTransaction()
            transaction?.replace(R.id.nav_host_fragment, fragment)?.commit()
        }

        newsBtn.setOnClickListener{
            val fragment = NewsFragment()   //navigate to news fragment
            val transaction = fragmentManager?.beginTransaction()
            transaction?.replace(R.id.nav_host_fragment, fragment)?.commit()

        }
        cautionBtn.setOnClickListener{
            val fragment = CautionFragment()   //navigate to precaution fragment
            val transaction = fragmentManager?.beginTransaction()
            transaction?.replace(R.id.nav_host_fragment, fragment)?.commit()
        }


        return view
    }
}


