package com.example.chatting_app

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class ChattingRoom : AppCompatActivity() {

    private lateinit var messageRecylerView:RecyclerView
    private lateinit var messageBox:EditText
    private lateinit var sendbtn:ImageView


    private lateinit var messageAdapter:MessageAdapter
    private lateinit var messagelist:ArrayList<Message>
    private lateinit var mDbref:DatabaseReference

    var receiverRoom:String?=null
    var senderRoom:String?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chatting_room)

        messageRecylerView=findViewById(R.id.recycler)
        messageBox=findViewById(R.id.messageBox)
        sendbtn=findViewById(R.id.send)


        val name=intent.getStringExtra("name")
        val receiveruid=intent.getStringExtra("uid")





        val senderuid=FirebaseAuth.getInstance().currentUser?.uid
        mDbref=FirebaseDatabase.getInstance().getReference()

        senderRoom=receiveruid+senderuid
        receiverRoom=senderuid+receiveruid

        supportActionBar?.title=name

        messagelist= ArrayList()
        messageAdapter= MessageAdapter(this,messagelist)

        messageRecylerView.layoutManager=LinearLayoutManager(this)
        messageRecylerView.adapter=messageAdapter

        //logic for adding data in recylerview

        mDbref.child("chats").child(senderRoom!!).child("messages").addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {

                messagelist.clear()

                for (postsnapshot in snapshot.children){
                    val message=postsnapshot.getValue(Message::class.java)
                    messagelist.add(message!!)
                }
                messageAdapter.notifyDataSetChanged()

            }

            override fun onCancelled(error: DatabaseError) {

            }

        })


        sendbtn.setOnClickListener(View.OnClickListener {

            val message=messageBox.text.toString()
            val messageobject=Message(message,senderuid,)

            mDbref.child("chats").child(senderRoom!!).child("messages").push()
                .setValue(messageobject).addOnSuccessListener {

                    mDbref.child("chats").child(receiverRoom!!).child("messages").push()
                        .setValue(messageobject)

                }
            messageBox.setText("")


        })


    }
}