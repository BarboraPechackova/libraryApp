package cz.cvut.fel.ear.library.rest;

import cz.cvut.fel.ear.library.model.Rating;
import cz.cvut.fel.ear.library.rest.utils.RestUtils;
import cz.cvut.fel.ear.library.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/v1/ratings")
public class RatingController {
    private final RatingService service;

    @Autowired
    public RatingController(RatingService service) {
        this.service = service;
    }


    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Rating> getRatings() {
        final List<Rating> ratings = service.findAll();
        if (ratings == null) {
            throw RestUtils.newNotFoundEx("Ratings",0);
        }
        return ratings;
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Rating getRating(@PathVariable Integer id) {
        final Rating rating = service.find(id);
        if (rating == null) {
            throw RestUtils.newNotFoundEx("Ratings", id);
        }
        return rating;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public ResponseEntity<Void> createRating(@RequestBody(required = false) Rating rating) {
        service.persist(rating);
        final HttpHeaders headers = RestUtils.createLocationHeaderFromUri("/{id}", rating.getId());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public ResponseEntity<Void> removeRating(@PathVariable int id) {
        final Rating rating = service.find(id);
        if (rating == null) {
            throw RestUtils.newNotFoundEx("Ratings", id);
        }
        service.remove(rating);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
