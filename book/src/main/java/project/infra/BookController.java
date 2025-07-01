package project.infra;

import org.springframework.http.ResponseEntity;
import java.util.Map;
import java.util.HashMap;

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

    @GetMapping("/{bookId}")
    public ResponseEntity<Map<String, Object>> getBookInfo(@PathVariable Long bookId) {
        Book book = bookRepository.findById(bookId)
            .orElseThrow(() -> new RuntimeException("도서를 찾을 수 없습니다."));

        Map<String, Object> result = new HashMap<>();
        result.put("bookId", book.getBookId());
        result.put("bookTitle", book.getBookTitle());
        result.put("bookSummary", book.getBookSummary());
        result.put("bookCoverImage", book.getBookCoverImage());
        result.put("authorId", book.getAuthorId());
        result.put("category", book.getCategory());

        return ResponseEntity.ok(result);
    }

}
//>>> Clean Arch / Inbound Adaptor
