package com.bankmtk.samodelkin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bankmtk.samodelkin.CharacterGenerator.fetchCharacterData
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

private const val CHARACTER_DATA_KEY = "CHARACTER_DATA_KEY"

private var Bundle.characterData
get() = getSerializable(CHARACTER_DATA_KEY) as CharacterGenerator.CharacterData
set(value) = putSerializable(CHARACTER_DATA_KEY, value)

class MainActivity : AppCompatActivity() {
    private var characterData = CharacterGenerator.generate()

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.characterData = characterData
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        characterData = savedInstanceState?.characterData ?:
            CharacterGenerator.generate()

        generateButton.setOnClickListener {
            runBlocking {
                characterData = fetchCharacterData().await()
                displayCharacterData()
            }

        }
        displayCharacterData()
    }
    private fun displayCharacterData(){
        characterData.run {
            nameTextView.text = name
            raceTextView.text = race
            dexterityTextView.text = dex
            wisdomTextView.text = wis
            strengthTextView.text = str
        }
    }
}
