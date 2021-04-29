package EventLoop;

import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonArray;

import java.util.List;

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
    public Future<JsonArray> getRealTimeTrainInfo(int trainID) {
        return null;
    }

    @Override
    public Future<List<Train>> getRealTimeStationInfo(int stationID) {
        return client.getRealTimeStationInfo(stationID);
    }
}
