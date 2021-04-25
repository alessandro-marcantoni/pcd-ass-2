package controller;

import commons.Parameter;
import controller.tasks.MasterTask;

import java.io.IOException;
import java.util.concurrent.ForkJoinPool;

public class Controller {

    private final ForkJoinPool forkJoinPool = new ForkJoinPool();

    public void notifyStarted() throws IOException {
        Parameter.INSTANCE.setIgnoredWords();
        this.forkJoinPool.invoke(new MasterTask());
    }

    public void notifyStopped() {
        this.forkJoinPool.shutdownNow();
    }
    
}
