package EventLoop.library;

import EventLoop.*;
import EventLoop.model.Details;
import EventLoop.model.Parameters;
import EventLoop.model.Solution;
import EventLoop.model.Train;
import io.vertx.core.Future;
import io.vertx.core.Vertx;

import java.util.List;

public class TrainSolutionLibrary implements TrainSolution {

    private final Vertx vertx = Vertx.vertx();
    private final Client client;
    private final GUI gui;
    private TrainMonitor trainMonitor;
    private StationMonitor stationMonitor;

    public TrainSolutionLibrary(final GUI gui) {
        this.client = new Client(vertx);
        vertx.deployVerticle(client);
        this.gui = gui;
    }

    @Override
    public Future<List<Solution>> getTrainSolutions(Parameters details) {
        return client.getTrainSolutions(details);
    }

    @Override
    public Future<Details> getRealTimeTrainInfo(String trainID) {
        return client.getRealTimeTrainInfo(trainID);
    }

    @Override
    public Future<List<Train>> getRealTimeStationInfo(String stationID, String mode) {
        return client.getRealTimeStationInfo(stationID, mode);
    }

    @Override
    public void startTrainMonitoring() {
        this.trainMonitor = new TrainMonitor(this, this.gui);
        this.vertx.deployVerticle(this.trainMonitor);
    }

    @Override
    public void stopTrainMonitoring() {
        this.trainMonitor.stop();
    }

    @Override
    public void startStationMonitoring() {
        this.stationMonitor = new StationMonitor(this, this.gui);
        this.vertx.deployVerticle(this.stationMonitor);
    }

    @Override
    public void stopStationMonitoring() {
        this.stationMonitor.stop();
    }


}
