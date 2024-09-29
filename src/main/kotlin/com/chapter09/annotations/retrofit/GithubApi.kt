package com.chapter09.annotations.retrofit

/**
 * Project: KotlinBasic
 * Create By: Chen.F.X
 * DateTime: 2024-09-28 23:40
 *
 * Desc:
 */
@Api("https://api.github.com")
interface GithubApi {
    @Api("users")
    interface Users {
        @Get("{name}")
        fun get(name: String): User

        @Get("{name}/followers")
        fun followers(name: String): List<User>
    }

    @Api("repos")
    interface Repos {
        @Get("{owner}/{repo}/forks")
        fun forks(owner: String, repo: String)
    }
}