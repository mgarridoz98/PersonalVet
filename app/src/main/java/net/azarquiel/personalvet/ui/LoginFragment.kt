package net.azarquiel.personalvet.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.nav_header_main.*
import kotlinx.android.synthetic.main.nav_header_main.view.*

import net.azarquiel.personalvet.R
import net.azarquiel.personalvet.picker.Picker

/**
 * A simple [Fragment] subclass.
 */
class LoginFragment : Fragment() {

    private lateinit var picker: Picker
    private lateinit var edpass: EditText
    private lateinit var ednombre: EditText
    val db = FirebaseFirestore.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ednombre = view.findViewById(R.id.edUserlogin) as EditText
        edpass = view.findViewById(R.id.edPasslogin) as EditText
        val btnAceptar = view.findViewById(R.id.btnlogin) as Button

        btnAceptar.setOnClickListener {
            login()
        }
    }

    private fun login() {
        getData()

        picker = Picker(requireActivity())
        ednombre.setText("")
        edpass.setText("")
    }


    private fun getData() {
        db.collection("users").whereEqualTo("name", ednombre.text.toString()).whereEqualTo("pass",edpass.text.toString())
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    for (document in task.result!!) {
                        Log.d("antonio",document.id + " => " + document.data)
                        updateHeader(document["name"] as String)
                    }
                } else {
                    Log.w("antonio","Error getting documents.", task.exception
                    )
                }
            }

    }

    private fun updateHeader(nombre: String) {
        val miivavatar = requireActivity().nav_view.getHeaderView(0).ivavatar
        miivavatar.setOnClickListener{
            picker.showPictureDialog()
        }
        val mitvavatar = requireActivity().nav_view.getHeaderView(0).tvavatar
        mitvavatar.text = nombre
    }

    override fun onActivityResult(requestCode:Int, resultCode:Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        picker.onActivityResult(requestCode, resultCode, data)
        nav_view.getHeaderView(0).ivavatar.setImageBitmap(picker.bitmap)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        picker.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
}
