package EventLoop.library;

import EventLoop.model.Details;
import EventLoop.GUI;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;

public class TrainMonitor extends AbstractVerticle {

    private final TrainSolution library;
    private final GUI gui;
    private long timerID;

    public TrainMonitor(final TrainSolution library, final GUI gui) {
        this.library = library;
        this.gui = gui;
    }

    @Override
    public void start() {
        this.timerID = vertx.setPeriodic(1000, time -> {
            Future<Details> future = this.library.getRealTimeTrainInfo(this.gui.getTrainCode());
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
        });
    }

    @Override
    public void stop() {
        this.vertx.cancelTimer(this.timerID);
        try {
            super.stop();
        } catch (Exception e) {
            e.printStackTrace();
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
