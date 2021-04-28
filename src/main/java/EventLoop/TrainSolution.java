package EventLoop;

import io.vertx.core.Future;
import io.vertx.core.json.JsonArray;

public interface TrainSolution {

    Future<JsonArray> getTrainSolutions(SolutionDetails details);

    Future<JsonArray> getRealTimeTrainInfo(int trainID);

}
