package EventLoop;

import io.vertx.core.Future;

// External thread to handle real time monitoring of trains
public class TrainMonitor implements Runnable {

    final TrainSolution library;
    final GUI gui;

    private static final long FREQUENCY = 1000;

    public TrainMonitor(final TrainSolution lib, GUI gui) {
        this.library = lib;
        this.gui = gui;
    }

    @Override
    public void run() {
        while(gui.isTrainRunning()) {
            Future<Details> future = library.getRealTimeTrainInfo(gui.getTrainCode());
            future.onSuccess(detail ->
                gui.getTrainArea().setText(
                    "--------------------------------------------------------------------\n" +
                    "Stazione di Partenza: \n" +
                    detail.getDepartureStation() + "\n\n" +
                    "Partenza Programmata: \n" +
                    detail.getProgrammedDeparture() + "\n\n" +
                    "Partenza Effettiva: \n" +
                    detail.getEffectiveDeparture() + "\n\n" +
                    checkTrainArrived(detail) +
                    "--------------------------------------------------------------------\n" +
                    "Stazione di Arrivo: \n" +
                    detail.getArrivalStation() + "\n\n" +
                    "Arrivo Programmato: \n" +
                    detail.getProgrammedArrival() + "\n\n" +
                    "Arrivo Effettivo: \n" +
                    detail.getEffectiveArrival() + "\n\n" +
                    "--------------------------------------------------------------------\n" +
                    detail.getInformation()
                )
            );
            try {
                Thread.sleep(FREQUENCY);
                //gui.getTrainArea().setText("");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private String checkTrainArrived(Details detail) {
        if(detail .getSize() < 3) { // train arrived
            return "";
        } else {
            return
                "--------------------------------------------------------------------\n" +
                "Ultima Fermata Effettuata: \n" +
                detail.getLastStation() + "\n\n" +
                "Arrivo Programmato: \n" +
                detail.getProgrammedLast() + "\n\n" +
                "Arrivo Effettivo: \n" +
                detail.getEffectiveLast() + "\n\n";
        }
    }
}
