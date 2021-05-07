package EventLoop.model;

public class Parameters {

    private final String departureStation;
    private final String arrivalStation;
    private final String departureDate;
    private final String departureTime;

    public Parameters(String departureStation, String arrivalStation, String departureDate, String departureTime) {
        this.departureStation = departureStation;
        this.arrivalStation = arrivalStation;
        this.departureDate = departureDate;
        this.departureTime = departureTime;
    }

    public String getDepartureStation() {
        return departureStation;
    }

    public String getArrivalStation() {
        return arrivalStation;
    }

    public String getDepartureDate() {
        return departureDate;
    }

    public String getDepartureTime() {
        return departureTime;
    }
}
