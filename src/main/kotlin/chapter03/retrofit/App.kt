package chapter03.retrofit

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File

fun main() {
    val gitHubApi =
        Retrofit.Builder().baseUrl("https://api.github.com").addConverterFactory(GsonConverterFactory.create()).build()
            .create(GitHubApi::class.java)

    val response = gitHubApi.getRepository("Jetbrains", "kotlin")
    response.enqueue(object : Callback<Repository> {
        override fun onResponse(call: Call<Repository>, response: Response<Repository>) {
            val repository = response.body()
            if (repository == null) {
                println("Error! ${response.code()} - ${response.message()}")
            } else {
                println(repository.name)
                println(repository.owner.login)
                println(repository.stargazers_count)
                println(repository.forks_count)
                println(repository.html_url)

                File("Kotlin.html").writeText("""
                    <!DOCTYPE html>
                    <html>
                    <head>
                        <meta charset=''UTF-8>
                        <title>${repository.owner.login} - ${repository.name}</title>
                    </head>
                    <body>
                        <h1><a href='${repository.html_url}'>${repository.owner.login} - ${repository.name}</a></h1>
                        <p>${repository.description}</p>
                        <p>Stars: ${repository.stargazers_count}</p>
                        <p>Forks: ${repository.forks_count}</p>
                    </body>
                    </html>
                """.trimIndent()
                )
            }
        }

        override fun onFailure(call: Call<Repository>, t: Throwable) {
            println("Error! Throwable ${t.message}")
        }
    })
}