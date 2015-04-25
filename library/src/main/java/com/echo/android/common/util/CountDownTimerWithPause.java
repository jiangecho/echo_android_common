package com.echo.android.common.util;

import android.os.CountDownTimer;

/**
 * Created by jiangecho on 15/4/25.
 * Custom CountDownTimer, support pause and resume
 */
public abstract class CountDownTimerWithPause {
    private long mMillisInfuture;
    private long mCountDownInterval;
    private long escapeMillis;

    private CountDownTimer countDownTimer;

    private STATE state = STATE.READY;

    private enum STATE{
        READY,
        STARTED,
        PAUSED
    }

    public CountDownTimerWithPause(long millisInfuture, long countDownInterval){
        this.mMillisInfuture = millisInfuture;
        this.mCountDownInterval = countDownInterval;
    }

    public void start(){
        if (state == STATE.READY){
            countDownTimer = new MyCountDownTimer(mMillisInfuture, mCountDownInterval);
            countDownTimer.start();
            escapeMillis = 0;
            state = STATE.STARTED;
        }

    }

    public void cancel(){
        if (state == STATE.STARTED){
            countDownTimer.cancel();
            countDownTimer = null;
            escapeMillis = 0;
            state = STATE.READY;
        }
    }

    public void pause(){
        if (state == STATE.STARTED){
            countDownTimer.cancel();
            countDownTimer = null;
            state = STATE.PAUSED;
        }
    }

    public void resume(){
        if (state == STATE.PAUSED){
            countDownTimer = new MyCountDownTimer(mMillisInfuture - escapeMillis, mCountDownInterval);
            countDownTimer.start();
            state = STATE.STARTED;
        }
    }

    public abstract void onTick(long millisEscaped, long millisUntilFinished);
    public abstract void onFinish();

    private class MyCountDownTimer extends CountDownTimer{

        private final long countDownInterval;

        /**
         * @param millisInFuture    The number of millis in the future from the call
         *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
         *                          is called.
         * @param countDownInterval The interval along the way to receive
         *                          {@link #onTick(long)} callbacks.
         */
        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
            this.countDownInterval = countDownInterval;
        }

        /**
         * Callback fired on regular interval.
         *
         * @param millisUntilFinished The amount of time until finished.
         */
        @Override
        public void onTick(long millisUntilFinished) {
            escapeMillis += countDownInterval;
            CountDownTimerWithPause.this.onTick(escapeMillis, millisUntilFinished);
        }

        /**
         * Callback fired when the time is up.
         */
        @Override
        public void onFinish() {
            CountDownTimerWithPause.this.onFinish();
        }
    }
}
