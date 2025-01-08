package com.example.todo.ui.lists.items

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.todo.models.Item
import com.example.todo.repositories.ItemRepository

data class ListItemsState(
    val items : List<Item> = arrayListOf(),
    val isLoading: Boolean = false,
    val error: String? = null
)

class ListItemsViewModel : ViewModel() {
    var state = mutableStateOf(ListItemsState())
        private set

    fun getItems(listId: String) {
        ItemRepository.getItems(listId) { items, error ->
            state.value = state.value.copy(
                error = error,
                items = items
            )
        }
    }

    fun toggleItemChecked(listId: String, item: Item) {
        ItemRepository.setChecked(listId, item, !item.checked)
    }
}