package com.chapter06.sealedclass

// 密封类
sealed class PlayerState

object Idle : PlayerState()

class Playing(val song: Song) : PlayerState() {
    fun start() {
        println("开始播放音乐")
    }

    fun stop() {
        println("停止播放音乐")
    }
}

class Error(val errorInfo: ErrorInfo) : PlayerState() {
    fun recover() {
        println("error recover")
    }
}

data class Song(val name: String, val url: String, var position: Int)

data class ErrorInfo(val code: Int, val message: String)

object Songs {
    val StarSky = Song("Star Sky", "https://fakeurl.com/321144.mp3", 0)
}

class Player {
    var state: PlayerState = Idle

    fun play(song: Song) {
        this.state = when (val state = this.state) {
            // 直接播放音乐
            Idle -> {
                Playing(song).also(Playing::start)
            }
            // 如果正在播放歌曲
            is Playing -> {
                // 先暂停
                state.stop()
                // 再播放新歌
                Playing(song).also(Playing::start)
            }
            // 如果报错
            is Error -> {
                // 先恢复状态
                state.recover()
                // 然后开始播放
                Playing(song).also(Playing::start)
            }
        }
    }
}

fun main() {
    val player = Player()
    player.play(Songs.StarSky)
}
