package com.example.todo.ui.lists

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.todo.models.ListItems
import com.example.todo.repositories.ListItemsRepository
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

data class ListListsState(
    val listItemsList : List<ListItems> = arrayListOf(),
    val isLoading: Boolean = false,
    val error: String? = null
)

class ListListsViewModel : ViewModel() {
    var state = mutableStateOf(ListListsState())
        private set

    fun getLists() {
        ListItemsRepository.getLists { listItemsList ->
            state.value = state.value.copy(
                listItemsList = listItemsList
            )
        }
    }

    fun logout() {
        val auth = Firebase.auth
        val currentUser = auth.signOut()
    }
}