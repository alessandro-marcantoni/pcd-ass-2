package Reactive;

public class StopFlag {

    private boolean stopped = false;

    public synchronized boolean isStopped() {
        return this.stopped;
    }

    public synchronized void stop() {
        this.stopped = true;
    }

}
