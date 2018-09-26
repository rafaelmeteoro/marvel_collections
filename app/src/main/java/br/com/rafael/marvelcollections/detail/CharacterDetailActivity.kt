package br.com.rafael.marvelcollections.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import br.com.rafael.marvelcollections.R

class CharacterDetailActivity : AppCompatActivity() {

    companion object {
        private const val CHARACTER_ID: String = "extra_character_id"
        private const val CHARACTER_IMAGE_URL: String = "extra_character_image_url"

        fun newIntent(context: Context, characterId: Int, imageUrl: String?): Intent {
            val intent = Intent(context, CharacterDetailActivity::class.java)
            intent.putExtra(CHARACTER_ID, characterId)
            intent.putExtra(CHARACTER_IMAGE_URL, imageUrl)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_character_detail)
    }
}