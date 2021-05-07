package EventLoop.library;

import EventLoop.model.Details;
import EventLoop.model.Parameters;
import EventLoop.model.Solution;
import EventLoop.model.Train;
import io.vertx.core.Future;

import java.util.List;

public interface TrainSolution {

    Future<List<Solution>> getTrainSolutions(Parameters details);

    Future<Details> getRealTimeTrainInfo(String trainID);

    Future<List<Train>> getRealTimeStationInfo(String stationID, String mode);

    void startTrainMonitoring();

    void stopTrainMonitoring();

    void startStationMonitoring();

    void stopStationMonitoring();
}
