package com.example.flo

import android.annotation.SuppressLint
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.flo.RoomDB.SongDatabase
import com.example.flo.data.Song
import com.example.flo.databinding.ActivitySongBinding
import java.util.*
import java.util.concurrent.TimeUnit


class SongActivity : AppCompatActivity() {
    lateinit var binding : ActivitySongBinding

    private var mediaPlayer : MediaPlayer? = null
    lateinit var timer: Timer
    private var songs = ArrayList<Song>()
    private var nowPos = 0
    private lateinit var songDB: SongDatabase


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySongBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initPlayList()
        initSong()
        initClickListener()
    }

    override fun onPause() {
        super.onPause()

        songs[nowPos].second = (songs[nowPos].playTime * binding.songProgressBar.progress) / 1000
        songs[nowPos].isPlaying = false
        setPlayerStatus(false)

        val sharedPreferences = getSharedPreferences("song", MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        editor.putInt("songId", songs[nowPos].id)
        editor.apply()
    }
    // 앱이 종료될 때 리소스 해제
    override fun onDestroy() {
        super.onDestroy()

        timer.interrupt() // 스레드를 해제함
        mediaPlayer?.release() // 미디어플레이어가 가지고 있던 리소스를 해방
        mediaPlayer = null // 미디어플레이어 해제
    }

    private fun initPlayList(){
        songDB = SongDatabase.getInstance(this)!!
        songs.addAll(songDB.songDao().getSongs())
    }

    private fun startTimer() {
        timer = Timer(songs[nowPos].playTime, songs[nowPos].isPlaying,songs[nowPos].second)
        timer.start()
    }

    private fun initSong() {
        val spf = getSharedPreferences("song", MODE_PRIVATE)
        val songId = spf.getInt("songId", 0)
        val seconds = spf.getLong("songSecond",0L)

        nowPos = getPlayingSongPosition(songId)
        songs[nowPos].second = seconds.toInt()

        Log.d("now Song ID",songs[nowPos].id.toString())

        startTimer()
        setPlayer(songs[nowPos])
    }

    private fun getPlayingSongPosition(songId: Int): Int{
        for (i in 0 until songs.size){
            if (songs[i].id == songId){
                return i
            }
        }
        return 0
    }

    private fun setPlayer(song: Song) {
        val music = resources.getIdentifier(song.music, "raw", this.packageName)

        binding.songTitleTv.text = song.title
        binding.songSingerTv.text = song.singer
        binding.songStartTimeTv.text =
            String.format("%02d:%02d", song.second / 60, song.second % 60)
        binding.songEndTimeTv.text =
            String.format("%02d:%02d", song.playTime / 60, song.playTime % 60)
        binding.songAlbumCoverIv.setImageResource(song.coverImg!!)
        binding.songProgressBar.progress = (song.second * 1000 / song.playTime)

        setPlayerStatus(song.isPlaying)

        if (song.isLike) {
            binding.songLikeOffIv.setImageResource(R.drawable.ic_my_like_on)
        } else {
            binding.songLikeOffIv.setImageResource(R.drawable.ic_my_like_off)
        }

        mediaPlayer = MediaPlayer.create(this, music)
    }

    private fun initClickListener() {
        binding.songDownIb.setOnClickListener {
            val editor = getSharedPreferences("song", MODE_PRIVATE).edit()
            editor.putLong("songSecond", timer.second)
            editor.apply()
            timer.interrupt()
            mediaPlayer?.release()
            mediaPlayer = null
            finish()
        }

        binding.songMiniplayerIv.setOnClickListener {
            setPlayerStatus(true)
        }

        binding.songPauseIv.setOnClickListener {
            setPlayerStatus(false)
        }

        binding.songPreviousIv.setOnClickListener {
            moveSong(-1)
        }

        binding.songNextIv.setOnClickListener {
            moveSong(+1)
        }

        binding.songLikeOffIv.setOnClickListener {
            setLike(songs[nowPos].isLike)
        }

    }
    private fun moveSong(direct: Int){

        if (nowPos + direct < 0){
            Toast.makeText(this,"first song", Toast.LENGTH_SHORT).show()
            return
        }
        if (nowPos + direct >= songs.size){
            Toast.makeText(this,"last song", Toast.LENGTH_SHORT).show()
            return
        }

        nowPos += direct

        timer.interrupt()
        startTimer()

        mediaPlayer?.release() // 미디어플레이어가 가지고 있던 리소스를 해방
        mediaPlayer = null // 미디어플레이어 해제

        setPlayer(songs[nowPos])
    }

    private fun setLike(isLike: Boolean){
        songs[nowPos].isLike = !isLike
        songDB.songDao().updateIsLikeById(!isLike,songs[nowPos].id)

        if (isLike){
            binding.songLikeOffIv.setImageResource(R.drawable.ic_my_like_off)
        }else{
            binding.songLikeOffIv.setImageResource(R.drawable.ic_my_like_on)
        }
    }


    private fun setPlayerStatus(isPlaying: Boolean) {
        timer.isPlaying = isPlaying
        songs[nowPos].isPlaying = isPlaying

        if (isPlaying) {
            binding.songMiniplayerIv.visibility = View.GONE
            binding.songPauseIv.visibility = View.VISIBLE

            mediaPlayer?.seekTo((((timer.second*5) * (songs[nowPos].playTime)).toInt()))
            mediaPlayer?.start()
        } else {
            binding.songMiniplayerIv.visibility = View.VISIBLE
            binding.songPauseIv.visibility = View.GONE
            songs[nowPos].second = timer.second.toInt()

            mediaPlayer?.pause()
        }
    }

    inner class Timer(private val playTime: Int = 0, var isPlaying: Boolean = false, private val playsecond : Int) : Thread() {
        var second: Long = playsecond.toLong()

        @SuppressLint("SetTextI18n")
        override fun run() {
            try {
                while (true) {
                    if (second >= playTime) {
                        break
                    }

                    if (isPlaying) {
                        sleep(1000)
                        second++

                        runOnUiThread {
                            binding.songProgressBar.progress =
                                (second * 1000 / playTime).toInt()
                            binding.songStartTimeTv.text = String.format(
                                "%02d:%02d",
                                TimeUnit.SECONDS.toMinutes(second),
                                second % 60
                            )
                        }
                    }
                }
            } catch (e: InterruptedException) {
                Log.d("SONG", "쓰레드가 죽었습니다. ${e.message}")
            }
        }
    }

    //        player = Player(song.playTime,song.isPlaying)
//        //player 쓰레드 시작 (run 함수가 실행)
//        player.start()

//        binding.songDownIb.setOnClickListener {
//            intent.putExtra("isPlaying",song.isPlaying)
//            val intent = Intent(this, MainActivity::class.java)
//            song.playmilis = player.second
//            intent.putExtra("songJson", song)
//            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
//            player.interrupt()
//            startActivity(intent)
//            finish()
//        }

//        binding.songMiniplayerIv.setOnClickListener {
//            song.isPlaying = true
//            setPlayerStatus(song.isPlaying)
//        }
//        binding.songPauseIv.setOnClickListener {
//            song.isPlaying = false
//            setPlayerStatus(song.isPlaying)
//        }

//        binding.songRepeatInactiveIv.setOnClickListener { setRepeatStatus(1) }
//        binding.songRepeatOnIv.setOnClickListener { setRepeatStatus(2) }
//        binding.songRepeatOn1Iv.setOnClickListener { setRepeatStatus(3) }
//
//        binding.songRandomInactiveIv.setOnClickListener { setRandomStatus(false) }
//        binding.songRandomActiveIv.setOnClickListener { setRandomStatus(true) }
//
//        binding.songLikeOffIv.setOnClickListener { setlikeStatus(likeStatus) }
//        binding.songUnlikeOffIv.setOnClickListener {  setUnlikeStatus(unLikeStatus) }


//    private fun initSong() {
//        if(intent.hasExtra("title") && intent.hasExtra("singer") && intent.hasExtra("isPlaying") && intent.hasExtra("playTime")){
//            song.title = intent.getStringExtra("title").toString()
//            song.singer = intent.getStringExtra("singer").toString()
//            song.playTime = intent.getIntExtra("playTime",0)
//            song.isPlaying = intent.getBooleanExtra("isPlaying",false)
//
//            binding.songTitleTv.text = intent.getStringExtra("title")
//            binding.songSingerTv.text = intent.getStringExtra("singer")
//            binding.songEndTimeTv.text = String.format("%02d:%02d", song.playTime/60, song.playTime%60)
//
//            setPlayerStatus(song.isPlaying)
//        }
//    }
//
//    fun setPlayerStatus(isPlaying : Boolean){
//        if(isPlaying){
//            binding.songMiniplayerIv.visibility = View.GONE
//            binding.songPauseIv.visibility = View.VISIBLE
//        }
//        else{
//            binding.songMiniplayerIv.visibility = View.VISIBLE
//            binding.songPauseIv.visibility = View.GONE
//        }
//    }


//    override fun onPause() {
//        super.onPause()
//        mediaPlayer?.pause()
//        player.isPlaying = false
//        song.isPlaying = true
//        song.playmilis = (binding.songProgressBar.progress * song.playTime) / 1000
//        setPlayerStatus(song.isPlaying)
//
//        val sharedPreferences = getSharedPreferences("song", MODE_PRIVATE)
//        val editor = sharedPreferences.edit()
//
//        //song 데이터 객체를 json으로 변환
//        val json = gson.toJson(song)
//        editor.putString("song",json)
//
//        editor.apply()
//    }

//    override fun onDestroy() {
////        player.interrupt()
//        super.onDestroy()
//        mediaPlayer?.release()  // 미디어 플레이어가 갖고 있던 리소스 해제
//        mediaPlayer = null  // 미디어 플레이어 해제
//    }
//inner class Player(private val playTime : Int, var isPlaying: Boolean) : Thread() {
//
//    var second = 0
//
//    override fun run() {
//        try {
//            while(true){
//
//                if(second >= playTime) {
//                    second = 0
//                    binding.songProgressBar.progress = 0
//                }
//
//                Log.e("playerTHREAD",song.isPlaying.toString())
//                if(song.isPlaying){
//                    sleep(1000) //1초마다
//                    second++
//
//                    //또는 handler.post
//                    runOnUiThread {
//                        binding.songProgressBar.progress = second*1000 / playTime
//                        binding.songStartTimeTv.text = String.format("%02d:%02d",second/60,second%60)
//                    }
//
//                }
//            }
//
//        }catch (e : InterruptedException){
//            Log.d("interrupt","Thread Finsihed")
//        }
//    }
//}


}