package EventLoop;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonArray;
import io.vertx.ext.web.client.WebClient;

public class Client extends AbstractVerticle {

    private final WebClient client;
    private static final String URI = "/msite/api/solutions?arflag=A&adultno=1&childno=0&direction=A&frecce=false&onlyRegional=false";
    private static final String SERVER = "www.lefrecce.it";

    public Client(Vertx vertx) {
        this.client = WebClient.create(vertx);
    }

    public Future<JsonArray> getTrainSolutions(SolutionDetails details) {
        Promise<JsonArray> promise = Promise.promise();
        client
                .get(443, SERVER, URI)
                .ssl(true)
                .addQueryParam("origin", details.getDepartureStation())
                .addQueryParam("destination", details.getArrivalStation())
                .addQueryParam("adate", details.getDepartureDate())
                .addQueryParam("atime", details.getDepartureTime())
                .send()
                .onSuccess(res -> {
                    if (res.statusCode() == 200) {
                        promise.complete(res.bodyAsJsonArray());
                        System.out.println(Parsing.getSolutions(res.bodyAsJsonArray()));
                    }
                })
                .onFailure(err -> {
                    promise.fail(err.getMessage());
                });
        return promise.future();
    }

}
