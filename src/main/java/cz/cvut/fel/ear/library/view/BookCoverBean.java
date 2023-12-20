package cz.cvut.fel.ear.library.view;

import cz.cvut.fel.ear.library.model.Book;
import cz.cvut.fel.ear.library.model.BookCover;
import cz.cvut.fel.ear.library.rest.BookController;
import cz.cvut.fel.ear.library.rest.PictureController;
import cz.cvut.fel.ear.library.service.BookCoverService;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import lombok.Getter;
import lombok.Setter;
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
    private final BookCoverService bookCoverService;

    @Getter
    @Setter
    private UploadedFiles files;
    private BookBean bookBean;


    @Autowired
    public BookCoverBean(BookController bookController, PictureController pictureController, BookCoverService bookCoverService, BookBean bookBean) {
        this.bookController = bookController;
        this.pictureController = pictureController;
        this.bookCoverService = bookCoverService;
        this.bookBean = bookBean;
    }

    public String getBookCoverURL(Book book) {
        List<BookCover> covers = book.getBookCovers();
        if (covers.isEmpty())
            return ViewUtils.getBookCoverImageUrl(null);
        else {
            return ViewUtils.getBookCoverImageUrl(covers.get(0));
        }
    }

    public String getBookCoverURL(int bookCoverId) {
        BookCover cover = bookCoverService.find(bookCoverId);
        return ViewUtils.getBookCoverImageUrl(cover);
    }

    public List<Integer> getCoverImagesIdOfBookById(int bookID) {
        Book book = bookController.getBook(bookID);
        List<Integer> result = new ArrayList<>();
        for (BookCover cover: book.getBookCovers()) {
            result.add(cover.getId());
        }
        return result;
    }

    public void deleteBookCover(BookCover cover) {
        pictureController.deleteBookCover(cover.getId());
    }

    public String deleteBookCover(int coverId) {
        pictureController.deleteBookCover(coverId);
        return "./editBook.xhtml?faces-redirect=true";
    }


    // File upload ---------------------------------------------------------------------------------------------------

    public void uploadMultiple() {
        if (files != null) {
            for (UploadedFile f : files.getFiles()) {
                FacesMessage message = new FacesMessage("Successful", f.getFileName() + " is uploaded.");
                BookCover cover = new BookCover(bookController.getBook(bookBean.getBookId()), f.getContent());
                String filename = f.getFileName();
                cover.setType(filename.substring(filename.lastIndexOf('.')+1));
                pictureController.newBookCover(cover);
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

    // \File upload ---------------------------------------------------------------------------------------------------


}
