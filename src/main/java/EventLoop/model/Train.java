package EventLoop.model;

import java.util.Date;

public class Train {

    private final int number;
    private final String category;
    private String origin;
    private String destination;
    private String platformArrival;
    private String platformDeparture;
    private Date arrivalTime;
    private Date departureTime;
    private int delay;

    public static class Builder {

        private final int number;
        private final String category;
        private String origin;
        private String destination;
        private String platformArrival;
        private String platformDeparture;
        private Date arrivalTime;
        private Date departureTime;
        private int delay;

        public Builder(int number, String category) {
            this.number = number;
            this.category = category;
        }

        public Builder origin(String origin) {
            this.origin = origin;
            return this;
        }

        public Builder destination(String destination) {
            this.destination = destination;
            return this;
        }

        public Builder platformArrival(String platform) {
            this.platformArrival = platform;
            return this;
        }

        public Builder platformDeparture(String platform) {
            this.platformDeparture = platform;
            return this;
        }

        public Builder arrivalTime(Date time) {
            this.arrivalTime = time;
            return this;
        }

        public Builder departureTime(Date time) {
            this.departureTime = time;
            return this;
        }

        public Builder delay(int delay) {
            this.delay = delay;
            return this;
        }

        public Train build() {
            return new Train(this);
        }

    }

    private Train(Builder builder) {
        this.number = builder.number;
        this.category = builder.category;
        this.origin = builder.origin;
        this.destination = builder.destination;
        this.platformArrival = builder.platformArrival;
        this.platformDeparture = builder.platformDeparture;
        this.arrivalTime = builder.arrivalTime;
        this.departureTime = builder.departureTime;
        this.delay = builder.delay;
    }

    public int getNumber() {
        return number;
    }

    public String getCategory() {
        return category;
    }

    public String getOrigin() {
        return origin;
    }

    public String getDestination() {
        return destination;
    }

    public String getPlatformArrival() {
        return platformArrival;
    }

    public String getPlatformDeparture() {
        return platformDeparture;
    }

    public Date getArrivalTime() {
        return arrivalTime;
    }

    public Date getDepartureTime() {
        return departureTime;
    }

    public int getDelay() {
        return delay;
    }
}
