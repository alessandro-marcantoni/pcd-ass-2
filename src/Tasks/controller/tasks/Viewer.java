package Tasks.controller.tasks;

import Tasks.model.monitors.OccurrencesBuffer;
import Tasks.model.monitors.StopFlag;
import Tasks.view.GUI;

public class Viewer extends Thread {

    private final GUI gui;
    private final StopFlag stopFlag;

    public Viewer(GUI gui, StopFlag stopFlag) {
        this.gui = gui;
        this.stopFlag = stopFlag;
    }

    @Override
    public void run() {
        while (!this.stopFlag.isStopped()) {
            this.gui.modelUpdated(OccurrencesBuffer.INSTANCE.getOccurrences());
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        this.gui.modelUpdated(OccurrencesBuffer.INSTANCE.getOccurrences());
    }
}
