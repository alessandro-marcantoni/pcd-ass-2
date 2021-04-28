package EventLoop;

public class Service {

    public static void main(String[] args) {
        TrainSolution trainSolution = new TrainSolutionLibrary();
        new GUI();
        SolutionDetails details =
                new SolutionDetails("ROMA TERMINI",
                        "BOLOGNA CENTRALE",
                        "28/04/2021",
                        "11");
        trainSolution.getTrainSolutions(details).onSuccess(res -> {});
    }

}
