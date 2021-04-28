package EventLoop;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.ext.web.client.WebClient;

public class Client extends AbstractVerticle {

    private final WebClient client;

    public Client(Vertx vertx) {
        this.client = WebClient.create(vertx);
    }

    public void getTrainSolutions(SolutionDetails details, SolutionsHandler handler) {
        client
                .get(443, "www.lefrecce.it", "/msite/api/solutions")
                .ssl(true)
                .addQueryParam("origin", details.getDepartureStation())
                .addQueryParam("destination", details.getArrivalStation())
                .addQueryParam("arflag", details.getAR())
                .addQueryParam("adate", details.getDepartureDate())
                .addQueryParam("atime", details.getDepartureTime())
                .addQueryParam("adultno", details.getAdultsNumber())
                .addQueryParam("childno", details.getChildrenNumber())
                .addQueryParam("frecce", details.onlyFrecce())
                .addQueryParam("onlyRegional", details.onlyRegionals())
                .send()
                .onSuccess(res -> {
                    System.out.println(res.bodyAsString());
                })
                .onFailure(err -> {
                    System.out.println(err.getMessage());
                });
    }

}
