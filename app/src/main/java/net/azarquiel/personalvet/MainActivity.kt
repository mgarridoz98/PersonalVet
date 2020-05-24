package net.azarquiel.personalvet

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import com.google.android.material.navigation.NavigationView
import androidx.navigation.ui.AppBarConfiguration
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.nav_header_main.view.*
import net.azarquiel.personalvet.picker.Picker
import net.azarquiel.personalvet.ui.HomeFragment
import net.azarquiel.personalvet.ui.LoginFragment
import net.azarquiel.personalvet.ui.RegisterFragment

class MainActivity : AppCompatActivity(),NavigationView.OnNavigationItemSelectedListener {

    private lateinit var picker: Picker
    private lateinit var appBarConfiguration: AppBarConfiguration

    companion object {
        const val REQUEST_PERMISSION = 200
        const val REQUEST_GALLERY = 1
        const val REQUEST_CAMERA = 2
        const val TAG = "ImgPicker"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        val db = FirebaseFirestore.getInstance()
        setSupportActionBar(toolbar)

        picker = Picker(this)

        nav_view.setNavigationItemSelectedListener(this)
        setInitialFragment()

        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        var fragment: Fragment? = null
        when (item.itemId) {
            R.id.nav_login -> {
                fragment = LoginFragment()
            }
            R.id.nav_registro -> {
                fragment = RegisterFragment()
            }
            R.id.nav_home -> {
                fragment = HomeFragment()
            }
        }
        replaceFragment(fragment!!)
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    private fun setInitialFragment() {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.frame, HomeFragment())
        fragmentTransaction.commit()
    }

    // Cambiamos de fragmento
    // cada vez que elegimos una opcion del menu del BottonNavigationView

    private fun replaceFragment(fragment: Fragment) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame, fragment)
        fragmentTransaction.commit()
    }

}
