package EventLoop;

import java.util.List;

@FunctionalInterface
public interface SolutionsHandler {

    void handle(List<Solution> solutions);

}
