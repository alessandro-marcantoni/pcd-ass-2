package EventLoop;

import io.vertx.core.Future;
import io.vertx.core.json.JsonArray;

import java.util.List;
import java.util.Map;

public interface TrainSolution {

    Future<List<Solution>> getTrainSolutions(SolutionDetails details);

    Future<Map<String, String>> getRealTimeTrainInfo(String trainID);

}
