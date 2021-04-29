package EventLoop;

import io.vertx.core.Future;
import io.vertx.core.Vertx;

import java.util.List;
import java.util.Map;

public class TrainSolutionLibrary implements TrainSolution {

    private final Client client;

    public TrainSolutionLibrary() {
        Vertx vertx = Vertx.vertx();
        this.client = new Client(vertx);
        vertx.deployVerticle(client);
    }

    @Override
    public Future<List<Solution>> getTrainSolutions(SolutionDetails details) {
        return client.getTrainSolutions(details);
    }

    @Override
    public Future<Map<String, String>> getRealTimeTrainInfo(String trainID) {
        return client.getRealTimeTrainInfo(trainID);
    }

    @Override
    public Future<List<Train>> getRealTimeStationInfo(String stationID) {
        return client.getRealTimeStationInfo(stationID);
    }
}
