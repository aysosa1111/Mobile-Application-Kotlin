package com.example.quicknotes

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.quicknotes.ui.theme.QuickNotesTheme
import androidx.compose.ui.platform.LocalContext

/**
 * Main activity class that acts as the entry point of the QuickNotes app.
 * It initializes and displays the home screen of the app which includes a list of notes.
 */
class MainActivity : ComponentActivity() {

    companion object {
        // List to hold all notes, shared across activities to maintain state
        var notesList = mutableStateListOf<Note>()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            QuickNotesTheme {
                Surface(color = MaterialTheme.colors.background) {
                    HomeScreen()
                }
            }
        }
    }

    /**
     * Composable function to render the home screen.
     * This includes the top app bar and a floating action button to add new notes.
     */
    @Composable
    fun HomeScreen() {
        val context = LocalContext.current
        Scaffold(
            topBar = {
                TopAppBar(title = { Text("QuickNotes") })
            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = {
                        // Start the CreateNoteActivity when the button is clicked
                        val intent = Intent(context, CreateNoteActivity::class.java)
                        context.startActivity(intent)
                    }
                ) {
                    Icon(Icons.Filled.Add, contentDescription = "Add Note")
                }
            }
        ) { paddingValues ->
            NoteList(
                notes = notesList,
                modifier = Modifier.padding(paddingValues)
            )
        }
    }

    /**
     * Composable function to render a list of notes.
     * @param notes List of Note objects to display.
     * @param modifier Modifiers to adjust the layout.
     */
    @Composable
    fun NoteList(notes: List<Note>, modifier: Modifier = Modifier) {
        LazyColumn(
            modifier = modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp)
        ) {
            items(notes) { note ->
                NoteItem(note)
            }
        }
    }

    /**
     * Composable function to render a single note item within a list.
     * @param note The Note object to display.
     */
    @Composable
    fun NoteItem(note: Note) {
        val context = LocalContext.current
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .clickable {
                    // Launch ViewEditNoteActivity when a note is clicked, passing the note ID
                    val intent = Intent(context, ViewEditNoteActivity::class.java)
                    intent.putExtra("noteId", note.id)
                    context.startActivity(intent)
                },
            elevation = 4.dp
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = note.title, style = MaterialTheme.typography.h6)
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = if (note.content.length > 50) note.content.substring(0, 47) + "..." else note.content,
                    style = MaterialTheme.typography.body2
                )
            }
        }
    }
}
