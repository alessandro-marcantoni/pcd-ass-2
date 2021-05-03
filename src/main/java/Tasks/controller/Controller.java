package Tasks.controller;

import Tasks.commons.Parameter;
import Tasks.controller.tasks.MasterTask;
import Tasks.controller.tasks.Viewer;
import Tasks.model.monitors.OccurrencesBuffer;
import Tasks.model.monitors.StopFlag;
import Tasks.view.GUI;

import java.io.IOException;
import java.util.concurrent.ForkJoinPool;

public class Controller {

    private final GUI gui;
    private ForkJoinPool forkJoinPool;
    private StopFlag stopFlag;

    public Controller(GUI gui) {
        this.gui = gui;
    }

    public void notifyStarted() throws IOException {
        this.forkJoinPool = new ForkJoinPool();
        Parameter.INSTANCE.setIgnoredWords();
        OccurrencesBuffer.INSTANCE.clear();
        this.stopFlag = new StopFlag();
        final Thread viewer = new Viewer(this.gui, this.stopFlag);
        viewer.start();
        new Thread(() -> {
            this.forkJoinPool.invoke(new MasterTask());
            this.stopFlag.stop();
        }).start();
    }

    public void notifyStopped() {
        this.forkJoinPool.shutdownNow();
        this.stopFlag.stop();
    }
    
}
