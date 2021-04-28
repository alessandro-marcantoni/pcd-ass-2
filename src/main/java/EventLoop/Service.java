package EventLoop;

import io.vertx.core.Vertx;

public class Service {

    public static void main(String[] args) {
        TrainSolution trainSolution = new TrainSolutionLibrary();
        SolutionDetails details =
                new SolutionDetails("ROMA TERMINI",
                        "BOLOGNA CENTRALE",
                        "28/04/2021",
                        "11");
        trainSolution.getTrainSolutions(details).onSuccess(res -> System.out.println(res));
    }

}
