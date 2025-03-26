package com.picpay.desafio.android.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.picpay.desafio.android.R
import com.picpay.desafio.android.User
import com.picpay.desafio.android.databinding.ListItemUserBinding
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

class UserListAdapter :
    ListAdapter<User, UserListAdapter.UserViewHolder>(UserDiffCallback()) {


    // Criação do ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = ListItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(view)
    }

    // Bind do ViewHolder com os dados
    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    // ViewHolder como classe interna
    class UserViewHolder(val binding: ListItemUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User) {
            // Vincula os dados do usuário à interface
            binding.name.text = user.name
            binding.username.text = user.username
            binding.progressBar.visibility = View.VISIBLE

            // Carregamento da imagem com Picasso
            Picasso.get()
                .load(user.img)
                .error(R.drawable.ic_round_account_circle)
                .into(binding.picture, object : Callback {
                    override fun onSuccess() {
                        binding.progressBar.visibility = View.GONE
                    }

                    override fun onError(e: Exception?) {
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(itemView.context, "Erro ao carregar imagem", Toast.LENGTH_SHORT).show()
                    }
                })
        }
    }

    // Callback para calcular diferenças na lista
    class UserDiffCallback : DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem.id == newItem.id // Comparação de IDs únicos
        }

        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem == newItem // Verifica se os itens são exatamente iguais
        }
    }
}