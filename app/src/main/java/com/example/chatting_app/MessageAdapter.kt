package com.example.chatting_app

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth


class MessageAdapter(private var context: Context, private var arrayList: ArrayList<Message>):RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val ITEM_RECIVE=1
    val ITEM_SEND=2

    class SentViewHolder(itemView:View) : RecyclerView.ViewHolder(itemView){
        val sentMessage=itemView.findViewById<TextView>(R.id.txt_send)
    }

    class ReceiveViewHolder(itemView:View) : RecyclerView.ViewHolder(itemView){
        val receiveMessage=itemView.findViewById<TextView>(R.id.txt_receive)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        if (viewType==1){
//            inflate recive

            val view:View = LayoutInflater.from(context).inflate(R.layout.receiive,parent,false)
            return ReceiveViewHolder(view)

        }else{
//            inflate send
            val view:View=LayoutInflater.from(context).inflate(R.layout.send,parent,false)
            return SentViewHolder(view)
        }

    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentMessage=arrayList[position]
        if (holder.javaClass==SentViewHolder::class.java){
            //do the stuff for send viewholder

            val viewHolder=holder as SentViewHolder

            holder.sentMessage.text=currentMessage.message

        }else{
            //do stuff for receive viewholder

//            val viewholder=holder as ReceiveViewHolder
            val viewHolder=holder as ReceiveViewHolder
            holder.receiveMessage.text=currentMessage.message


        }
    }

    override fun getItemViewType(position: Int): Int {

        val currentMessage=arrayList[position]
        if (FirebaseAuth.getInstance().currentUser?.uid.equals(currentMessage.senderId)){
            return ITEM_SEND
        }else{
            return ITEM_RECIVE
        }

    }

}