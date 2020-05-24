package net.azarquiel.personalvet.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_patologia.*

import net.azarquiel.personalvet.R
import net.azarquiel.personalvet.adapter.AdapterPatologia
import net.azarquiel.personalvet.model.Patologia

/**
 * A simple [Fragment] subclass.
 */
class PatologiaFragment : Fragment() {

    private lateinit var adapter: AdapterPatologia
    val db = FirebaseFirestore.getInstance()
    private var patologia: ArrayList<Patologia> = ArrayList()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_patologia, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val datos = arguments

        val patologia = datos!!.getString("patologia")
        initRV()
        setListener(patologia!!)
    }

    private fun initRV() {
        adapter = AdapterPatologia(requireActivity().baseContext, R.layout.patologia_row)
        rvpatologia.adapter = adapter
        rvpatologia.layoutManager = LinearLayoutManager(activity)
    }

    private fun setListener(s:String) {
        val docRef = db.collection("patologia").whereGreaterThanOrEqualTo("sintomas",s)
        docRef.addSnapshotListener { snapshot, e ->
            if (e != null) {
                Log.w("antonio", "Listen failed.", e)
                return@addSnapshotListener
            }

            if (snapshot != null && !snapshot.isEmpty) {
                documentToList(snapshot.documents)
                adapter.setPatologia(patologia)
            } else {
                Log.d("antonio", "Current data: null")
            }
        }
    }



    private fun documentToList(documents: List<DocumentSnapshot>) {
        patologia.clear()
        documents.forEach { d ->
            val name = d["name"] as String
            val sintomas = d["sintomas"] as String
            patologia.add(Patologia(nombre = name,sintomas = sintomas))
        }
    }
}
