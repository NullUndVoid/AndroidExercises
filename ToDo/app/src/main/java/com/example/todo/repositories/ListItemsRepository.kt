package com.example.todo.repositories

import android.util.Log
import com.example.todo.TAG
import com.example.todo.models.ListItems
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore

object ListItemsRepository {
    val db = Firebase.firestore
    val auth = Firebase.auth

    fun getLists(onResult : (List<ListItems>) -> Unit ){
        val currentUser = auth.currentUser
        val userId = currentUser?.uid

        db.collection("lists")
            .whereArrayContains("owners", userId!!)
            .get() //Get collections owned by userId correct?

            .addOnSuccessListener { documents ->
                val listItemsList = arrayListOf<ListItems>()
                for (document in documents) {
                    Log.d(TAG, "${document.id} => ${document.data}")
                    val listItem = document.toObject(ListItems::class.java)
                    listItem.docId = document.id
                    listItemsList.add(listItem)
                }

                onResult(listItemsList)
            }

            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents: ", exception)
            }
    }

    fun addList(listItems: ListItems){
        val db = Firebase.firestore

        val auth = Firebase.auth
        val currentUser = auth.currentUser //auth keeps track of user, if we don't handle logout same user persists
        val userId = currentUser?.uid

        listItems.owners = arrayListOf(userId?:"")

        db.collection("lists")
            .add(listItems)
            .addOnSuccessListener { documentReference ->
                Log.d(TAG, "DocumentSnapshot written with ID: ${documentReference.id}")
            }

            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
            }
    }

    fun addUserToList(listId: String, docId: String) {
        val db = Firebase.firestore

        val auth = Firebase.auth
        val currentUser = auth.currentUser
        val userId = currentUser?.uid

        db.collection("lists")
            .document(listId)
            .get()
            .addOnSuccessListener { document ->
                val listItems = document.toObject(ListItems::class.java)
                listItems?.docId = document.id

                var owners = arrayListOf<String>()

                if( listItems?.owners.isNullOrEmpty() ) {
                    listItems?.owners = arrayListOf(userId?:"")
                }

                else{ //Don't really get whats the point of this, are we filtering to find the owner of the list?
                    var o = listItems?.owners?.filter { l ->
                        l == userId
                    }

                    if (o.isNullOrEmpty()){
                        owners = (listItems!!.owners!! + arrayListOf(userId?:"")) as ArrayList<String>
                    }
                }

                db.collection("lists")
                    .document(listId)
                    .update("owners", owners)

            }
    }
}