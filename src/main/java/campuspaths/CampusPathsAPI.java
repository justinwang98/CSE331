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

/**
 * API that campuspath application uses to connect with campus path model
 */
@RestController
@CrossOrigin("http://localhost:3000")
public class CampusPathsAPI {

    /**
     * Singleton for campuspathmodel
     */
    @Autowired
    private CampusPathModel campusPathModel;


    /**
     * Gets the paths from the model and passes it to app
     * @param src is source string
     * @param dest is destination string
     * @return list of strings containing coordinates
     * @throws MarvelParser.MalformedDataException is data is malformed
     */
    @GetMapping("/paths") // "http://localhost:8080/path?src=CSE&dest=PAB"
    public @Nullable List<String> getPaths(@RequestParam(value="src") String src,
                                                    @RequestParam(value="dest") String dest) throws MarvelParser.MalformedDataException {
        return campusPathModel.findShortestPath2(src, dest);
    }

    /**
     * gets the building coords from model and passes it to app
     * @param name is the name of the building
     * @return a list of strings containing the building coords
     * @throws MarvelParser.MalformedDataException if data is malformed
     */
    @GetMapping("/building")
    public List<String> getBuilding(@RequestParam(value="name") String name) throws MarvelParser.MalformedDataException {
        return campusPathModel.findBuilding(name);
    }
}