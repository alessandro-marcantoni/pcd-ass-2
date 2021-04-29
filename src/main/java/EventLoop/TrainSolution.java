package EventLoop;

import io.vertx.core.Future;
import io.vertx.core.json.JsonArray;

import java.util.List;

public interface TrainSolution {

    Future<List<Solution>> getTrainSolutions(SolutionDetails details);

    Future<JsonArray> getRealTimeTrainInfo(int trainID);

    Future<List<Train>> getRealTimeStationInfo(int stationID);

}
