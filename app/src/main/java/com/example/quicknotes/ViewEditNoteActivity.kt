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
 * Activity for viewing and editing a single note.
 * Retrieves the note based on its ID passed via intent and allows editing.
 */
class ViewEditNoteActivity : ComponentActivity() {

    // ID of the note being edited, initialized to an invalid value
    private var noteId: Int = -1
    // The note object retrieved based on ID
    private lateinit var note: Note

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Extract note ID from the intent
        noteId = intent.getIntExtra("noteId", -1)
        // Find the note in the global list or create an empty placeholder if not found
        note = MainActivity.notesList.find { it.id == noteId } ?: Note(-1, "", "")

        setContent {
            QuickNotesTheme {
                Surface(color = MaterialTheme.colors.background) {
                    EditNoteScreen()
                }
            }
        }
    }

    /**
     * Composable function for the edit note screen.
     * It allows editing the title and content of a note.
     */
    @Composable
    fun EditNoteScreen() {
        val activity = (LocalContext.current as? Activity)
        var title by remember { mutableStateOf(note.title) }  // State for note title
        var content by remember { mutableStateOf(note.content) }  // State for note content

        Scaffold(
            topBar = {
                TopAppBar(title = { Text("View/Edit Note") })  // Top bar with a title
            }
        ) { paddingValues ->
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
                    onClick = {
                        // Update the note object in the global list if it exists
                        val index = MainActivity.notesList.indexOfFirst { it.id == note.id }
                        if (index != -1) {
                            MainActivity.notesList[index] = note.copy(title = title, content = content)
                        }
                        activity?.finish()  // Close the activity after saving changes
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Save")
                }
            }
        }
    }
}
