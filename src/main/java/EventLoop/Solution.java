package EventLoop;

import java.util.Date;
import java.util.List;

public class Solution {

    private final String origin;
    private final String destination;
    private final Date departureTime;
    private final Date arrivalTime;
    private final String duration;
    private final List<String> trains;

    public Solution(String origin, String destination, Date departureTime, Date arrivalTime, String duration, List<String> trains) {
        this.origin = origin;
        this.destination = destination;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.duration = duration;
        this.trains = trains;
    }

    public String getOrigin() {
        return origin;
    }

    public String getDestination() {
        return destination;
    }

    public Date getDepartureTime() {
        return departureTime;
    }

    public Date getArrivalTime() {
        return arrivalTime;
    }

    public String getDuration() {
        return duration;
    }

    public List<String> getTrains() {
        return trains;
    }

    @Override
    public String toString() {
        return "Solution{" +
                "origin='" + origin + '\'' +
                ", destination='" + destination + '\'' +
                ", departureTime=" + departureTime +
                ", arrivalTime=" + arrivalTime +
                ", duration='" + duration + '\'' +
                ", trains=" + trains +
                '}';
    }
}
