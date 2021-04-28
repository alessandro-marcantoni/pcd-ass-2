package Tasks.commons;

public interface CommonParameter extends MasterParameter, WorkerParameter {

    void setParameters(String directory, String path, int nWords);

}
