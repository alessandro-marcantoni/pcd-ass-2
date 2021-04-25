package controller.tasks;

import commons.Parameter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;

public class MasterTask extends RecursiveTask<Void> {

    private final List<RecursiveTask<Void>> tasks = new ArrayList<>();

    @Override
    protected Void compute() {
        Parameter.INSTANCE.getFilesInDirectory().stream()
                .map(StripTask::new)
                .peek(this.tasks::add)
                .forEach(RecursiveTask::fork);
        this.tasks.forEach(RecursiveTask::join);
        return null;
    }
}
