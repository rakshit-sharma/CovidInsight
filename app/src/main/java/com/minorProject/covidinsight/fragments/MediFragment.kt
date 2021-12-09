package com.minorProject.covidinsight.fragments

import android.app.DatePickerDialog
import android.os.Bundle
//import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.minorProject.covidinsight.CenterRVAdapter
import com.minorProject.covidinsight.CenterRVModel
import com.minorProject.covidinsight.R
import org.json.JSONException
import java.util.*
import kotlin.collections.ArrayList


class MediFragment : Fragment() {

    private lateinit var searchButton : Button
    private lateinit var pinCodeEdt : EditText
    private lateinit var centerRV : RecyclerView
    private lateinit var loadingPB : ProgressBar
    private lateinit var centerList : List<CenterRVModel>
    private lateinit var centerRVAdapter: CenterRVAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_medi, container, false)

        searchButton = view.findViewById(R.id.idBtnSearch)
        pinCodeEdt = view.findViewById(R.id.idEditPinCode)
        centerRV = view.findViewById(R.id.idRVCenters) as RecyclerView
        loadingPB = view.findViewById(R.id.idPBLoading)
        centerList = ArrayList<CenterRVModel>()

        searchButton.setOnClickListener{
            val pinCode = pinCodeEdt.text.toString()

            if(pinCode.length != 6){
                Toast.makeText(activity, "Please Enter a valid Pin Code", Toast.LENGTH_SHORT).show()
            }else {
                (centerList as ArrayList<CenterRVModel>).clear()
                val c = Calendar.getInstance()
                val year = c.get(Calendar.YEAR)
                val month = c.get(Calendar.MONTH)
                val day = c.get(Calendar.DAY_OF_MONTH)

                val dpd = DatePickerDialog(
                    requireActivity(),
                    DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                        loadingPB.visibility = View.VISIBLE
                        val dateStr: String = """$dayOfMonth-${month + 1}-$year"""
                        getAppointmentDetails(pinCode, dateStr)
                    },
                    year,
                    month,
                    day
                )
                dpd.show()
            }
        }
        return view
    }

    private fun getAppointmentDetails(pinCode: String, date: String){
        
        val url = "https://cdn-api.co-vin.in/api/v2/appointment/sessions/public/calendarByPin?pincode="+pinCode+"&date="+date //replace https from http
        val queue = Volley.newRequestQueue(activity)
        val request =
            JsonObjectRequest(Request.Method.GET,url,null, Response.Listener{ response ->
            loadingPB.setVisibility(View.GONE)
            try {
                val centerArray = response.getJSONArray("centers")
                if(centerArray.length() == 0){
                    Toast.makeText(getContext(), "No Vaccination Centers Available", Toast.LENGTH_SHORT).show()
                    }
                for (i in 0 until centerArray.length()){
                    val centerObj = centerArray.getJSONObject(i)
                    val centerName:String = centerObj.getString("name")
                    val centerAddress:String = centerObj.getString("address")
                    val centerFromTime:String = centerObj.getString("from")
                    val centerToTime:String = centerObj.getString("to")
                    val fee_type:String = centerObj.getString("fee_type")
                    val sessionObj = centerObj.getJSONArray("sessions").getJSONObject(0)
                    val availableCapacity:Int = sessionObj.getInt("available_capacity")
                    val ageLimit:Int = sessionObj.getInt("min_age_limit")
                    val vaccineName:String = sessionObj.getString("vaccine")

                    val center = CenterRVModel(
                        centerName,
                        centerAddress,
                        centerFromTime,
                        centerToTime,
                        fee_type,
                        ageLimit,
                        vaccineName,
                        availableCapacity
                    )
                    centerList = centerList+center
                }
                centerRVAdapter = CenterRVAdapter(centerList)
                centerRV.layoutManager = LinearLayoutManager(activity)
                centerRV.adapter = centerRVAdapter

            }catch (e : JSONException){
                loadingPB.visibility = View.GONE
                e.printStackTrace()
            }
        },
            Response.ErrorListener{ error ->
                loadingPB.setVisibility(View.GONE)
//                Log.d("rakshitHelp", "getAppointmentDetails: "+ error)
                Toast.makeText(getContext(), error.localizedMessage, Toast.LENGTH_SHORT).show()
            })
        queue.add(request)
    }
}