package EventLoop.library;

import EventLoop.GUI;
import EventLoop.model.Train;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class StationMonitor extends AbstractVerticle {

    private final TrainSolution library;
    private final GUI gui;
    private long timerID;

    public StationMonitor(final TrainSolution library, final GUI gui) {
        this.library = library;
        this.gui = gui;
    }

    @Override
    public void start() {
        this.timerID = vertx.setPeriodic(1000, time -> {
            Future<List<Train>> future = library.getRealTimeStationInfo(gui.getStationCode(), gui.getStationMode());
            future.onSuccess(trains -> gui.getStationArea().setText(trains.stream()
                    .map(t ->
                            "Numero: " + t.getNumber() + "\n" +
                                    "Tipo: " + t.getCategory() + "\n" +
                                    "Stazione di Origine: " + t.getOrigin() + "\n" +
                                    "Stazione di Destinazione: " + t.getDestination() + "\n" +
                                    "Binario di Arrivo: " + t.getPlatformArrival() + "\n" +
                                    "Orario di Arrivo: " + getTime(t.getArrivalTime()) + "\n" +
                                    "Binario di Partenza: " + t.getPlatformDeparture() + "\n" +
                                    "Orario di Partenza: " + getTime(t.getDepartureTime()) + "\n" +
                                    "Ritardo: " + t.getDelay() + " minuti\n\n")
                    .collect(Collectors.toList()).toString()
                    .replaceAll("\\[", "")
                    .replaceAll("\\]", "")
                    .replaceAll(",", "")));
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

    private String getTime(Date date) {
        return (date.getHours() < 10 ? "0"+date.getHours() : date.getHours()) +":"+ (date.getMinutes() < 10 ? "0"+date.getMinutes() : date.getMinutes());
    }
}

