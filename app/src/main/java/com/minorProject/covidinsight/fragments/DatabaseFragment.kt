package com.minorProject.covidinsight.fragments


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request

import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.minorProject.covidinsight.R
import com.minorProject.covidinsight.StateModel
import com.minorProject.covidinsight.StateRVAdapter
import org.json.JSONException


class DatabaseFragment : Fragment() {


    private lateinit var worldCasesTV:TextView
    private lateinit var worldRecoveredTV:TextView
    private lateinit var worldDeathsTV:TextView
    private lateinit var countryCasesTV:TextView
    private lateinit var countryRecoveredTV:TextView
    private lateinit var countryDeathsTV:TextView
    private lateinit var stateRV:RecyclerView
    private lateinit var stateRVAdapter: StateRVAdapter
    private lateinit var stateList:List<StateModel>

    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<StateRVAdapter.StateRVViewHolder>? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_database, container, false)

        worldCasesTV = view.findViewById(R.id.idTVWorldCases)
        worldRecoveredTV = view.findViewById(R.id.idTVWorldRecovered)
        worldDeathsTV = view.findViewById(R.id.idTVWorldDeaths)
        countryCasesTV = view.findViewById(R.id.idTVIndiaCases)
        countryRecoveredTV = view.findViewById(R.id.idTVIndiaRecovered)
        countryDeathsTV = view.findViewById(R.id.idTVIndiaDeaths)
        stateRV = view.findViewById(R.id.idRVStates) as RecyclerView
        stateList = ArrayList<StateModel>()
        getStateInfo()
        getWorldInfo()

        return view
    }

    private fun getStateInfo(){
        val url = "https://api.rootnet.in/covid19-in/stats/latest"
        val queue = Volley.newRequestQueue(activity)
        val request =
            JsonObjectRequest(Request.Method.GET, url, null, { response ->
                try {
                    val dataObj = response.getJSONObject("data")
                    val summaryObj = dataObj.getJSONObject("summary")
                    val cases:Int = summaryObj.getInt("total")
                    val recovered:Int = summaryObj.getInt("discharged")
                    val deaths:Int = summaryObj.getInt("deaths")

                    countryCasesTV.text = cases.toString()
                    countryRecoveredTV.text = recovered.toString()
                    countryDeathsTV.text = deaths.toString()

                    val regionalArray = dataObj.getJSONArray("regional")
                    for (i in 0 until regionalArray.length()){
                        val regionalObj = regionalArray.getJSONObject(i)
                        val stateName:String = regionalObj.getString("loc")
                        val cases:Int = regionalObj.getInt("totalConfirmed")
                        val deaths:Int = regionalObj.getInt("deaths")
                        val recovered:Int = regionalObj.getInt("discharged")

                        val stateModel = StateModel(stateName, recovered, deaths, cases)
                        stateList = stateList+stateModel
                    }
                    stateRVAdapter = StateRVAdapter(stateList)
                    stateRV.layoutManager = LinearLayoutManager(activity)
                    stateRV.adapter = stateRVAdapter
                    Log.d("LOG HELP", "getStateInfo: "+"\\\\\\\\\\\\\\\\\\\\\\rakshit")
                }
                catch (e:JSONException){
                    e.printStackTrace()
                }
            }, { error ->
                {
                    Toast.makeText(getContext(), "Fail to get data", Toast.LENGTH_SHORT).show()
                }
            })

        queue.add(request)
    }

    private fun getWorldInfo(){
        val url = "https://disease.sh/v3/covid-19/all"
        val queue = Volley.newRequestQueue(activity)
        val request =
            JsonObjectRequest(Request.Method.GET, url, null,{ response->
                try {
                    val worldCases:Int = response.getInt("cases")
                    val worldRecovered:Int = response.getInt("recovered")
                    val worldDeaths:Int = response.getInt("deaths")
                    worldRecoveredTV.text = worldRecovered.toString()
                    worldCasesTV.text = worldCases.toString()
                    worldDeathsTV.text = worldDeaths.toString()
                }
                catch (e:JSONException){
                    e.printStackTrace()
                }
            }, {
                error->
                {
                Toast.makeText(getContext(), "Fail to get data", Toast.LENGTH_SHORT).show()
                }
            })
        queue.add(request)
    }


}