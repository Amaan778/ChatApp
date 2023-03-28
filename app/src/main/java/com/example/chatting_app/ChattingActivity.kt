package com.example.chatting_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class ChattingActivity : AppCompatActivity() {

    private lateinit var userRecycler:RecyclerView
    private lateinit var arraylist:ArrayList<ModelClass>
    private lateinit var adapter:RecyclerAdap
    private lateinit var mAuth:FirebaseAuth
    private lateinit var mDbref:DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chatting)

        mAuth=FirebaseAuth.getInstance()
        mDbref=FirebaseDatabase.getInstance().getReference()

        userRecycler=findViewById(R.id.recycler)
        arraylist=ArrayList()
        adapter=RecyclerAdap(this,arraylist)

        userRecycler.layoutManager=LinearLayoutManager(this)

        userRecycler.adapter=adapter

        mDbref.child("users").addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {

                arraylist.clear()
                for (postsnapshot in snapshot.children){
                    val currentuser=postsnapshot.getValue(ModelClass::class.java)
                    if (mAuth.currentUser?.uid!= currentuser?.uid){
                        arraylist.add(currentuser!!)
                    }
                }
                adapter.notifyDataSetChanged()

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }


        })



    }
}