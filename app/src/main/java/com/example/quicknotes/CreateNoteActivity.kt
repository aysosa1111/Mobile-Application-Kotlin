package com.example.quicknotes

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.quicknotes.ui.theme.QuickNotesTheme
import androidx.compose.ui.platform.LocalContext

/**
 * Activity for creating a new note.
 * Utilizes Jetpack Compose for the UI components.
 */
class CreateNoteActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            QuickNotesTheme {
                // Main surface container using material theme colors for the background.
                Surface(color = MaterialTheme.colors.background) {
                    CreateNoteScreen()
                }
            }
        }
    }

    /**
     * Composable function that displays the UI for creating a new note.
     */
    @Composable
    fun CreateNoteScreen() {
        // Get a reference to the current activity to finish it after saving the note.
        val activity = (LocalContext.current as? Activity)
        // State variables to hold the title and content of the new note.
        var title by remember { mutableStateOf("") }
        var content by remember { mutableStateOf("") }

        Scaffold(
            // Top bar with a simple title.
            topBar = {
                TopAppBar(title = { Text("Create Note") })
            }
        ) { paddingValues ->
            // Column layout for note title and content fields, and the save button.
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .padding(paddingValues)
            ) {
                TextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Title") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))
                TextField(
                    value = content,
                    onValueChange = { content = it },
                    label = { Text("Content") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    // Action to perform on button click.
                    onClick = {
                        // Create a new note object with an auto-incremented ID.
                        val newNote = Note(
                            id = MainActivity.notesList.size + 1,
                            title = title,
                            content = content
                        )
                        // Add the new note to the list in the MainActivity.
                        MainActivity.notesList.add(newNote)
                        // Finish the activity to return to the main screen.
                        activity?.finish()
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Save")
                }
            }
        }
    }
}
