package EventLoop;


import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonArray;

public class TrainSolutionLibrary implements TrainSolution {

    private final Vertx vertx;
    private final Client client;

    public TrainSolutionLibrary() {
        this.vertx = Vertx.vertx();
        this.client = new Client(vertx);
        this.vertx.deployVerticle(client);
    }

    @Override
    public Future<JsonArray> getTrainSolutions(SolutionDetails details) {
        return client.getTrainSolutions(details);
    }
}
