package com.smart.tvpos.util;

import android.app.Application;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.util.Log;

import com.smart.tvpos.R;

/**
 * Created by zj on 2017/7/3.
 * 邮箱：zjuan@yonyou.com
 * 描述：
 */
public abstract class CountDownUtils extends CountDownTimer {

    private MediaPlayer mMediaPlayer;
    /**
     * @param millisInFuture    The number of millis in the future from the call
     *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
     *                          is called.
     * @param countDownInterval The interval along the way to receive
     *                          {@link #onTick(long)} callbacks.
     */
    public CountDownUtils(long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
    }

    @Override
    public void onTick(long millisUntilFinished) {

        if(mMediaPlayer.isPlaying()){
            return;
        }
        mMediaPlayer.start();
        Log.e("TAG", "millisUntilFinished="+millisUntilFinished);
    }

    @Override
    public void onFinish() {

        finishTask();
        Log.e("TAG", "onFinish");
    }

    public CountDownUtils setMediaPlayer(Context context) {
        this.mMediaPlayer = MediaPlayer.create(context, R.raw.warning);
        return this;
    }

    public MediaPlayer getMediaPlayer() {
        return mMediaPlayer;
    }

    public CountDownUtils warningPlay(){
        if(!mMediaPlayer.isPlaying()){
            mMediaPlayer.start();
        }
        return this;
    }

    public abstract void finishTask();
}
