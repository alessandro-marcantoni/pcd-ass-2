package EventLoop;


import io.vertx.core.Vertx;

public class TrainSolutionLibrary implements TrainSolution {

    private final Vertx vertx;
    private final Client client;

    public TrainSolutionLibrary() {
        this.vertx = Vertx.vertx();
        this.client = new Client(vertx);
        this.vertx.deployVerticle(client);
    }

    @Override
    public void getTrainSolutions(SolutionDetails details, SolutionsHandler handler) {
        client.getTrainSolutions(details, handler);
    }
}
