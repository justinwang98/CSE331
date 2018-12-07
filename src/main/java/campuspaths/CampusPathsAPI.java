package campuspaths;

import hw3.Graph;
import hw6.MarvelParser;
import hw8.CampusPathModel;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/*
 * The @RestController annotation lets Spring know that this file manages the REST API of the project. Your API does not
 * need to be RESTful.
 * The @CrossOrigin annotation is used to let the server know that it is okay for localhost:3000 to make requests to this
 * API. If this line does not exist, all requests from localhost:3000 will fail
 */
@RestController
@CrossOrigin("http://localhost:3000")
public class CampusPathsAPI {

    /*
     * The @Autowired annotation tells Spring to look at resources (@Bean, @Service, @Repository, etc) and to find the
     * resource that has the same type as the given parameter (ie EmailSubscriberService). In this case, that would be
     * the service which becomes a resources because of the @Service annotation. This essentially creates a singleton
     * of the Service which is managed by Spring
     */
    @Autowired
    private CampusPathModel campusPathModel;


    /*
     * The @GetMapping annotation tells Spring that this method should be run when a GET request is made to the "/subscribersByZip"
     * endpoint. The data returned by the method is returned by the API. The @RequestParam collects the data that is
     * passed in as a parameter to the GET request: localhost:8080/subscribersByZip?zipcode=98105
     */
    @GetMapping("/paths") // "http://localhost:8080/path?src=CSE&dest=PAB"
    public @Nullable List<String> getPaths(@RequestParam(value="src") String src,
                                                    @RequestParam(value="dest") String dest) throws MarvelParser.MalformedDataException {
        return campusPathModel.findShortestPath2(src, dest);
    }

    @GetMapping("/building")
    public List<String> getBuilding(@RequestParam(value="name") String name) throws MarvelParser.MalformedDataException {
        return campusPathModel.findBuilding(name);
    }
}