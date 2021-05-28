package com.example.masudreplayapp

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import com.example.masudreplayapp.databinding.ActivityMainBinding
import android.view.View
import com.google.firebase.ktx.Firebase
import com.google.mlkit.nl.smartreply.SmartReply
import com.google.mlkit.nl.smartreply.SmartReplySuggestionResult
import com.google.mlkit.nl.smartreply.TextMessage
import kotlinx.android.synthetic.main.activity_main.*




class MainActivity : AppCompatActivity() {

    private var conversation = ArrayList<TextMessage>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        message.setOnClickListener {

        }
        send.setOnClickListener {
            addMessage(message.text.toString())
            getHints()
        }
        clue.setOnClickListener {
            getHints();

        }
        clearButton.setOnClickListener {
            conversation = ArrayList()

            clue0.visibility = View.GONE
            clue1.visibility = View.GONE
            clue2.visibility = View.GONE
            errorText.text = ""

        }
        clue0.setOnClickListener {
            addMessage(clue0.text.toString())

        }
        clue1.setOnClickListener {
            addMessage(clue1.text.toString())
        }
        clue2.setOnClickListener {
            addMessage(clue2.text.toString())
        }
    }

    private fun addMessage(text: String) {
        conversation.add(
            TextMessage.createForRemoteUser(
                text, System.currentTimeMillis(), name.text.toString()
            )
        )
    }

    private fun getHints() {
        val smartReplyGenerator = SmartReply.getClient()
        if (!conversation.isEmpty()) {
            conversation.add(message.text.toString());
            smartReplyGenerator.suggestReplies(conversation)
                .addOnSuccessListener { result ->
                    if (result.getStatus() == SmartReplySuggestionResult.STATUS_NOT_SUPPORTED_LANGUAGE) {
                        errorText.text = "language not supportive"

                    } else if (result.getStatus() == SmartReplySuggestionResult.STATUS_SUCCESS) {
                        clue0.text = result.suggestions[0].text
                        clue1.text = result.suggestions[1].text
                        clue2.text = result.suggestions[2].text

                        clue2.visibility = View.VISIBLE
                        clue1.visibility = View.VISIBLE
                        clue0.visibility = View.VISIBLE

                    }

                }

                .addOnFailureListener {
                    errorText.text = it.toString()
                }
        }
    }

    private fun <E> java.util.ArrayList<E>.add(element : String) {

    }
}

