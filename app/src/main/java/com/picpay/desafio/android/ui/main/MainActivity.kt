package com.picpay.desafio.android.ui.main

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.picpay.desafio.android.R
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: View
    private lateinit var adapter: UserListAdapter

    private val viewModel: MainViewModel by viewModel()

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
        viewModel.state.observe(this) { state ->
            progressBar.isVisible = state.isLoading

            recyclerView.isVisible = state.users.isNotEmpty() && !state.isError

            adapter.submitList(state.users)

            if (state.isError) {
                Toast.makeText(this, state.errorMessage, Toast.LENGTH_SHORT).show()
            }
        }
    }

}