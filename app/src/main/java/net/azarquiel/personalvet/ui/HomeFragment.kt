package net.azarquiel.personalvet.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import net.azarquiel.personalvet.R
import java.util.*


/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : Fragment() {

    private lateinit var transacion: FragmentTransaction
    private lateinit var fragmentoP: PatologiaFragment
    private lateinit var edPatologias: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        edPatologias = view.findViewById(R.id.edPatologias) as EditText

        val  btnbuscar = view.findViewById(R.id.btnBuscar) as Button
        btnbuscar.setOnClickListener{
            btnbuscarOnClick()
        }

        val btnforo = view.findViewById(R.id.btnForo) as Button
        btnforo.setOnClickListener {
            btnMapsOnClick()
        }
    }

    private fun btnMapsOnClick() {
        val uri: String = java.lang.String.format(
            "http://maps.google.com/maps?q=%s",
            "veterinario"
        )
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
        startActivity(intent)

    }


    private fun btnbuscarOnClick() {
        fragmentoP= PatologiaFragment()
        val datos = Bundle()
        datos.putString("patologia", edPatologias.text.toString())
        fragmentoP.arguments = datos
        transacion = parentFragmentManager.beginTransaction()
        transacion.replace(R.id.frame,fragmentoP)
        transacion.commit();
    }



}
