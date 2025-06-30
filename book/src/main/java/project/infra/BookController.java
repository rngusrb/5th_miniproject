package project.infra;

import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import project.domain.*;

//<<< Clean Arch / Inbound Adaptor

@RestController
@RequestMapping(value="/books")
@Transactional
public class BookController {

    @Autowired
    BookRepository bookRepository;
    
    @GetMapping(path = "/aaa")
    public String booksAaa() {
        Book book = new Book();

        book.PublishBook();
        
        return "test";
    }


    // @GetMapping(path = "/author/{authorId}")
    // public List<BookDTO.Response> booksByAuthor(@PathVariable("authorId") Long authorId) {
    //     List<Book> books = bookRepository.findByAuthorId(authorId);

    //     return books.stream()
    //             .map(BookDTO::toResponse)
    //             .toList();
    // }
    
    // @PatchMapping(path = "/{bookId}/view")
    // public BookDTO.Response viewBook(@PathVariable("bookId") Long bookId) {
    //     Book book = bookRepository.findById(bookId)
    //             .orElseThrow(() -> new RuntimeException("도서를 찾을 수 없습니다."));

    //     book.setViewCount(book.getViewCount() + 1);
    //     bookRepository.save(book);

    //     return BookDTO.toResponse(book);
    // }

    @PatchMapping(
        value = "/{bookId}/viewbook",
        produces = "application/json;charset=UTF-8"
    )
    public Book viewBook(
        @PathVariable(value = "bookId") Long bookId,
        HttpServletRequest request,
        HttpServletResponse response
    ) throws Exception {
        System.out.println("##### /book/viewBook  called #####");
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("도서를 찾을 수 없습니다."));

        book.setViewCount(book.getViewCount() + 1);
        bookRepository.save(book);

        return book;
    }
    
    @PatchMapping(
        value = "/{bookId}/likebook",
        produces = "application/json;charset=UTF-8"
    )
    public Book likeBook(
        @PathVariable(value = "bookId") Long bookId,
        HttpServletRequest request,
        HttpServletResponse response
    ) throws Exception {
        System.out.println("##### /book/likeBook  called #####");
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("도서를 찾을 수 없습니다."));

        book.setLikeCount(book.getLikeCount() + 1);
        bookRepository.save(book);

        return book;
    }

}
//>>> Clean Arch / Inbound Adaptor
