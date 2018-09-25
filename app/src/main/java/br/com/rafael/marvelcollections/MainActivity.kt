package br.com.rafael.marvelcollections

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.view.MenuItem
import br.com.rafael.marvelcollections.characters.CharactersFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    private lateinit var navigationBar: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, CharactersFragment(), "characters")
                    .commitNow()
            title = getString(R.string.characters)
        }

        navigationBar = bottomNavigationView
        navigationBar.setOnNavigationItemSelectedListener(this)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        if (item.itemId == navigationBar.selectedItemId) {
            return false
        }

        when (item.itemId) {
            R.id.action_characters -> {
                supportFragmentManager.beginTransaction()
                        .replace(R.id.container, CharactersFragment(), "characters")
                        .commitNow()
                title = getString(R.string.characters)
            }
        }

        return true
    }
}
