package cz.cvut.fel.ear.library.rest.utils;

import cz.cvut.fel.ear.library.exceptions.NotFoundException;

public class RestUtils {

    public static NotFoundException newNotFoundEx(String resourceName, Object identifier) {
        return new NotFoundException(resourceName + " identified by " + identifier + " not found.");
    }
}
