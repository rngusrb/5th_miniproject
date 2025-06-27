package project.infra;

import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.domain.*;

//<<< Clean Arch / Inbound Adaptor

@RestController
// @RequestMapping(value="/books")
@Transactional
public class BookController {

    @Autowired
    BookRepository bookRepository;

    @RequestMapping(
        value = "/books/{id}/viewbook",
        method = RequestMethod.PUT,
        produces = "application/json;charset=UTF-8"
    )
    public Book viewBook(
        @PathVariable(value = "id") Long id,
        HttpServletRequest request,
        HttpServletResponse response
    ) throws Exception {
        System.out.println("##### /book/viewBook  called #####");
        Optional<Book> optionalBook = bookRepository.findById(id);

        optionalBook.orElseThrow(() -> new Exception("No Entity Found"));
        Book book = optionalBook.get();
        book.viewBook();

        bookRepository.save(book);
        return book;
    }
}
//>>> Clean Arch / Inbound Adaptor
