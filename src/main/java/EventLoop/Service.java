package EventLoop;

import io.vertx.core.Vertx;

public class Service {

    public static void main(String[] args) {
        TrainSolution trainSolution = new TrainSolutionLibrary();
        SolutionDetails details =
                new SolutionDetails("ROMA TERMINI",
                        "BOLOGNA CENTRALE",
                        "A",
                        "28/04/2021",
                        "17",
                        "1",
                        "0",
                        "false",
                        "false");
        trainSolution.getTrainSolutions(details, solutions -> {});
    }

}
