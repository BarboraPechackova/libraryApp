package cz.cvut.fel.ear.library.rest.utils;

import cz.cvut.fel.ear.library.exceptions.NotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

public final class RestUtils {

    /**
     * Creates a new NotFoundException for the resource name and specified identifier.
     * @param resourceName - name of the resource
     * @param identifier   - identifier
     * @return NotFoundException
     */
    public static NotFoundException newNotFoundEx(String resourceName, Object identifier) {
        return new NotFoundException(resourceName + " identified by " + identifier + " not found.");
    }

    /**
     * "Borrowed" from Eshop :-)
     * Creates HTTP headers object with a location header with the specified path appended to the current request URI.
     * The {@code uriVariableValues} are used to fill in possible variables specified in the {@code path}.
     *
     * @param path              Path to add to the current request URI in order to construct a resource location
     * @param uriVariableValues Values used to replace possible variables in the path
     * @return HttpHeaders with location headers set
     */
    public static HttpHeaders createLocationHeaderFromUri(String path, Object... uriVariableValues) {
        assert path != null;

        final java.net.URI location = ServletUriComponentsBuilder.fromCurrentRequestUri().path(path).buildAndExpand(uriVariableValues).toUri();
        final HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.LOCATION, location.toASCIIString());
        return headers;
    }

    public static HttpHeaders createHttpHeaders() {
        return new HttpHeaders();
    }

}
