package com.example.android.pilgrim.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.bumptech.glide.Glide
import com.example.android.pilgrim.R
import com.example.android.pilgrim.model.pojo.Pilgrim
import com.example.android.pilgrim.profile.ProfileFragment
import com.example.android.pilgrim.qrScanner.QrScannerFragment
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.nav_header_home.view.*

class HomeActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    val TAG = "HomeAcitivity"

    //TODO fix rotation bug

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val toggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()


        navView.setNavigationItemSelectedListener(this)


        val intent = intent
        val token = intent.getStringExtra("token")
        val user = intent.getSerializableExtra("user") as Pilgrim
        Log.i(TAG, "token ${token}")
        Log.i(TAG, "user ${user}")

        navView.getHeaderView(0).username.text = user.username
        navView.getHeaderView(0).email.text = user.email

        if (!user.image.isNullOrEmpty())
            Glide.with(this).load(user.image).into(navView.getHeaderView(0).imageView)


        var fragment: Fragment = HomeFragment()
        setFragment(fragment, "Home")
    }

    override fun onBackPressed() {
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        var fragment: Fragment = HomeFragment()
        var tag = "Home"

        when (item.itemId) {
            R.id.nav_home -> {
                fragment = HomeFragment()
                tag = "Home"
            }
            R.id.nav_profile -> {
                fragment = ProfileFragment()
                tag = "Profile"
            }
            R.id.nav_qr_scanner -> {
                fragment = QrScannerFragment()
                tag = "Qr Scanner"
            }
        }

        setFragment(fragment, tag)

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun setFragment(fragment: Fragment, tag: String) {
        val fragmentTransaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
        fragmentTransaction.replace(R.id.frame, fragment, tag)
        fragmentTransaction.commitAllowingStateLoss()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }

}
