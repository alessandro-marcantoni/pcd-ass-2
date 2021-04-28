package Tasks.model.monitors;

public class StopFlag {

    private boolean stopped = false;

    public boolean isStopped() {
        return this.stopped;
    }

    public void stop() {
        this.stopped = true;
    }

}
