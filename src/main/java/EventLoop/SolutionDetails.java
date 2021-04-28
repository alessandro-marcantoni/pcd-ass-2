package EventLoop;

public class SolutionDetails {

    private final String departureStation;
    private final String arrivalStation;
    private final String departureDate;
    private final String departureTime;

    public SolutionDetails(String departureStation, String arrivalStation, String departureDate, String departureTime) {
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
