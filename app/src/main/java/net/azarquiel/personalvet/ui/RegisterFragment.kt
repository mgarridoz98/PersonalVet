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
import android.widget.Toast
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.nav_header_main.view.*
import net.azarquiel.personalvet.MainActivity

import net.azarquiel.personalvet.R
import net.azarquiel.personalvet.picker.Picker

/**
 * A simple [Fragment] subclass.
 */
class RegisterFragment : Fragment() {

    private lateinit var picker: Picker
    private lateinit var edpass: EditText
    private lateinit var ednombre: EditText
    val db = FirebaseFirestore.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ednombre = view.findViewById(R.id.edUserreg) as EditText
        edpass = view.findViewById(R.id.edPassreg) as EditText
        val btnAceptar = view.findViewById(R.id.btnregister) as Button
        picker = Picker(requireActivity())

        btnAceptar.setOnClickListener {
            login()
        }
    }

    private fun login() {
        addData()

        ednombre.setText("")
        edpass.setText("")

    }

    private fun addData() {
        val user: MutableMap<String, Any> = HashMap() // diccionario key value
        user["name"] = ednombre.text.toString()
        user["pass"] = edpass.text.toString()
        db.collection("users")
            .add(user)
            .addOnSuccessListener(OnSuccessListener<DocumentReference> { documentReference ->
                Log.d("a","DocumentSnapshot added with ID: " + documentReference.id)
                updateHeader()
            })
            .addOnFailureListener(OnFailureListener { e ->
                Log.w("a","Error adding document", e)
            })

    }

    private fun updateHeader() {
        val miivavatar = requireActivity().nav_view.getHeaderView(0).ivavatar
        miivavatar.setOnClickListener{
            picker.showPictureDialog()
        }
        val mitvavatar = requireActivity().nav_view.getHeaderView(0).tvavatar
        mitvavatar.text = ednombre.text.toString()
    }


}
