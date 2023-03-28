package com.example.chatting_app

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth

class RecyclerAdap(private var context: Context,private var arrayList: ArrayList<ModelClass>):
    RecyclerView.Adapter<RecyclerAdap.ViewHolder>() {


    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        val textv=itemView.findViewById<TextView>(R.id.name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerAdap.ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.users,parent,false))
    }

    override fun onBindViewHolder(holder: RecyclerAdap.ViewHolder, position: Int) {
        val currentUser=arrayList[position]
        holder.textv.text=currentUser.name

        holder.itemView.setOnClickListener(View.OnClickListener {
            val intent=Intent(context,ChattingRoom::class.java)
            intent.putExtra("name",currentUser.name)
            intent.putExtra("uid",currentUser.uid)
            context.startActivity(intent)
        })

    }

    override fun getItemCount(): Int {
        return arrayList.size
    }


}