package com.picpay.desafio.android

import MainViewModelFactory
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.picpay.desafio.android.ui.UserListAdapter

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: View
    private lateinit var adapter: UserListAdapter
    private val viewModel: MainViewModel by viewModels { MainViewModelFactory(UserRepository(Retrofit.service)) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        recyclerView = findViewById(R.id.recyclerView)
        progressBar = findViewById(R.id.user_list_progress_bar)

        adapter = UserListAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        setupObservers()
        viewModel.fetchUsers()
    }

    private fun setupObservers() {
        viewModel.userList.observe(this,  { users ->
            progressBar.visibility = View.GONE
            recyclerView.visibility = View.VISIBLE
            adapter.submitList(users)
        })

        viewModel.errorMessage.observe(this,  { errorMessage ->
            progressBar.visibility = View.GONE
            if (errorMessage != null) {
                Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
            }
        })
    }
}