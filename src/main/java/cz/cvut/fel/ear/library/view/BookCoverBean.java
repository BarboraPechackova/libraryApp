package cz.cvut.fel.ear.library.view;

import cz.cvut.fel.ear.library.model.Book;
import cz.cvut.fel.ear.library.model.BookCover;
import cz.cvut.fel.ear.library.rest.BookController;
import cz.cvut.fel.ear.library.rest.PictureController;
import cz.cvut.fel.ear.library.util.URLUtils;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import org.primefaces.event.FilesUploadEvent;
import org.primefaces.model.file.UploadedFile;
import org.primefaces.model.file.UploadedFiles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import java.util.ArrayList;
import java.util.List;

@Component
@RequestScope
public class BookCoverBean {
    private final BookController bookController;
    private final PictureController pictureController;

    private UploadedFiles files;


    @Autowired
    public BookCoverBean(BookController bookController, PictureController pictureController) {
        this.bookController = bookController;
        this.pictureController = pictureController;
    }

    public String getBookCoverURL(int bookID) {
        Book book = bookController.getBook(bookID);
        List<BookCover> covers = book.getBookCovers();
        if (covers.isEmpty())
            return URLUtils.getBoodCoverImageUrl(null);
        else {
            return URLUtils.getBoodCoverImageUrl(covers.get(0));
        }
    }

    public List<String> getCoverImagesOfBookById(int bookID) {
        Book book = bookController.getBook(bookID);
        List<String> result = new ArrayList<>();
        for (BookCover cover: book.getBookCovers()) {
            result.add(URLUtils.getBoodCoverImageUrl(cover));
        }
        return result;
    }

    // File upload ---------------------------------------------------------------------------------------------------

    public void uploadMultiple() {
        if (files != null) {
            for (UploadedFile f : files.getFiles()) {
                FacesMessage message = new FacesMessage("Successful", f.getFileName() + " is uploaded.");
                FacesContext.getCurrentInstance().addMessage(null, message);
            }
        }
    }

    public void handleFilesUpload(FilesUploadEvent event) {
        for (UploadedFile f : event.getFiles().getFiles()) {
            FacesMessage message = new FacesMessage("Successful", f.getFileName() + " is uploaded.");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
    }

    public UploadedFiles getFiles() {
        return files;
    }

    public void setFiles(UploadedFiles files) {
        this.files = files;
    }
}
