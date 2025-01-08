package com.example.todo.ui.share

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.todo.models.User
import com.example.todo.repositories.ListItemsRepository
import com.example.todo.repositories.UserRepository

data class ShareState(
    val users : List<User> = arrayListOf(),
    val isLoading: Boolean = false,
    val error: String? = null
)

class ShareViewModel : ViewModel() {

    var state = mutableStateOf(ShareState())
        private set

    fun getUsers() {
        UserRepository.getUsers { users ->
            state.value = state.value.copy(
                users = users
            )
        }
    }

    fun shareWithUser(listId: String, user: User) {
        ListItemsRepository.addUserToList(listId, user.docId!!)
    }
}