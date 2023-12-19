package cz.cvut.fel.ear.library.rest;

import cz.cvut.fel.ear.library.model.*;
import cz.cvut.fel.ear.library.rest.utils.RestUtils;
import cz.cvut.fel.ear.library.security.model.UserDetails;
import cz.cvut.fel.ear.library.service.BookCoverService;
import cz.cvut.fel.ear.library.service.BookService;
import cz.cvut.fel.ear.library.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rest/v1/pictures")
@PreAuthorize("permitAll()")
public class PictureController {

    private final BookCoverService coverService;
    private final UserService userService;

//    private final ProfilePicture profileService;

    @Autowired
    public PictureController(BookService bookService, BookCoverService coverService, UserService userService) {
        this.userService = userService;
        this.coverService = coverService;
    }

    @GetMapping(value = "/covers/{id}")
    public byte[] getBookCover(@PathVariable Integer id)  {
        final BookCover cover = coverService.find(id);
        if (cover == null) {
            throw RestUtils.newNotFoundEx("BookCover", id);
        }
        return cover.getPicture();
    }

    @PostMapping(value = "/covers/")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<Void> newBookCover(@RequestBody BookCover cover)  {
        coverService.persist(cover);
        HttpHeaders headers = RestUtils.createLocationHeaderFromUri("/covers/{id}", cover.getId());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/covers/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public void deleteBookCover(@PathVariable Integer id)  {
        final BookCover cover = coverService.find(id);
        if (cover == null) {
            throw RestUtils.newNotFoundEx("BookCover", id);
        }

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        assert auth.getPrincipal() instanceof UserDetails;
        final User user = ((UserDetails) auth.getPrincipal()).getUser();

        if (!userService.isUserAdmin(user) && cover.getBook().getUser().getId() != user.getId()) {
            throw new AccessDeniedException("Cannot delete book cover of other users book!");
        }

        coverService.delete(cover);
    }

}
