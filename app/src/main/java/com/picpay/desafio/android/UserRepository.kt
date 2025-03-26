package com.picpay.desafio.android


import com.picpay.desafio.android.User
import com.picpay.desafio.android.PicPayService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserRepository(private val service: PicPayService) {

    fun getUsers(
        onSuccess: (List<User>) -> Unit,
        onError: (String) -> Unit
    ) {
        service.getUsers().enqueue(object : Callback<List<User>> {
            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                if (response.isSuccessful && response.body() != null) {
                    onSuccess(response.body()!!)
                } else {
                    onError("Erro ao carregar os usuários.")
                }
            }

            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                onError(t.message ?: "Erro desconhecido.")
            }
        })
    }
}