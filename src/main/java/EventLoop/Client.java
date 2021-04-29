package EventLoop;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.ext.web.client.WebClient;

import java.util.List;
import java.util.Map;

public class Client extends AbstractVerticle {

    private final WebClient client;
    private static final String URI = "/msite/api/solutions?arflag=A&adultno=1&childno=0&direction=A&frecce=false&onlyRegional=false";
    private static final String SERVER = "www.lefrecce.it";

    public Client(Vertx vertx) {
        this.client = WebClient.create(vertx);
    }

    public Future<List<Solution>> getTrainSolutions(SolutionDetails details) {
        Promise<List<Solution>> promise = Promise.promise();
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
                        promise.complete(Parsing.getSolutions(res.bodyAsJsonArray()));
                    }
                })
                .onFailure(err -> {
                    promise.fail(err.getMessage());
                });
        return promise.future();
    }

    public Future<Map<String, String>> getRealTimeTrainInfo(String trainID) {
        Promise<Map<String, String>> promise = Promise.promise();

        client
                .get(443,"www.viaggiatreno.it", "/vt_pax_internet/mobile/scheda")
                .ssl(true)
                .addQueryParam("numeroTreno", trainID)
                .send()
                .onSuccess(res -> {
                    promise.complete(Parsing.getDetails(res.bodyAsString(), trainID));
                })
                .onFailure(err -> {
                    promise.fail(err.getMessage());
                });

        return promise.future();
    }

}
