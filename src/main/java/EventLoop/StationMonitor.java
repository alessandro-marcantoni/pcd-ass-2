package EventLoop;

// External thread to handle real time monitoring of stations situation
public class StationMonitor implements Runnable {

    final TrainSolution library;
    final GUI gui;

    private static final long FREQUENCY = 1000;

    public StationMonitor(final TrainSolution lib, GUI gui) {
        this.library = lib;
        this.gui = gui;
    }

    @Override
    public void run() {
        while(gui.isStationRunning()) {
            gui.getStationArea().setText(gui.getStationCode());
            try {
                Thread.sleep(FREQUENCY);
                //gui.getStationArea().setText("");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
