package com.picpay.desafio.android.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.picpay.desafio.android.R
import com.picpay.desafio.android.data.model.User
import com.picpay.desafio.android.databinding.ListItemUserBinding


class UserListAdapter : ListAdapter<User, UserListAdapter.UserViewHolder>(UserDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = ListItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class UserViewHolder(val binding: ListItemUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User) {
            binding.name.text = user.name
            binding.username.text = user.username
            binding.progressBar.visibility = View.VISIBLE

            binding.picture.load(user.img) {
                error(R.drawable.ic_round_account_circle)
                listener(
                    onSuccess = { _, _ ->
                        binding.progressBar.visibility = View.GONE
                    },
                    onError = { _, _ ->
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(
                            itemView.context,
                            "Erro ao carregar imagem",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                )
            }
        }
    }

    class UserDiffCallback : DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem == newItem
        }
    }
}