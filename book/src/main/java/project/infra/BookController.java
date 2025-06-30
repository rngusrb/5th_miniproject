package project.infra;

import java.time.LocalDateTime;
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
@RequestMapping(value="/api/v1/books")
@Transactional
public class BookController {

    @Autowired
    BookRepository bookRepository;

    // 전체 도서 목록 조회
    @GetMapping(
        produces = "application/json;charset=UTF-8"
    )
    public List<BookDTO.Response> getAllBooks() {

        List<Book> books = bookRepository.findAll();

        return books.stream().map(BookDTO::toResponse).toList();
    }

    // 구독자 도서 목록 조회 -> 구독 테이블 필요
    @GetMapping( 
        value = "/user/{userId}",
        produces = "application/json;charset=UTF-8"
    )
    public String getBooksByUser(@PathVariable Long userId) {
        // List<Book> books = bookRepository.findAll().stream().filter(book -> book.get).toList();
        
        return "구독 테이블 필요";
    }
    
    // 작가 도서 목록 조회 -> 작가 id로 구함
    @GetMapping( 
        value = "/author/{authorId}",
        produces = "application/json;charset=UTF-8"
    )
    public List<BookDTO.Response> getBooksByAuthor(@PathVariable Long authorId) {
        List<Book> books = bookRepository.findAll().stream().filter(book -> book.getAuthorId().equals(authorId)).toList();
        
        return books.stream().map(BookDTO::toResponse).toList();
    }

    // 도서 등록
    @PostMapping( 
        produces = "application/json;charset=UTF-8"
    )
    public BookDTO.Response registBook(@RequestBody BookDTO.Post book) {

        Book newBook = new Book();

        newBook.setBookId(book.getBookId());
        newBook.setAuthorId(book.getAuthorId());
        newBook.setBookTitle(book.getBookTitle());
        newBook.setCategory(book.getCategory());
        newBook.setCreateDate(LocalDateTime.now());
        newBook.setBookSummary(book.getBookSummary());
        newBook.setBookCoverImage(book.getBookCoverImage());
        newBook.setBookContent(book.getBookContent());
        newBook.setViewCount(0);
        newBook.setLikeCount(0);
        newBook.setPrice(1000);

        Book savedBook = bookRepository.save(newBook);

        return BookDTO.toResponse(savedBook);
    }

    // 도서 열람 -> subscribe에 요청
    @PostMapping( 
        value = "/view",
        produces = "application/json;charset=UTF-8"
    )
    public BookDTO.Response viewBook(@RequestBody BookDTO.ViewBookRequest request) {
        Book book = bookRepository.findById(request.getBookId())
            .orElseThrow(() -> new RuntimeException("Book not found"));
        
        book.viewBook();

        return BookDTO.toResponse(book);
    }

    // 관심 도서 등록 -> subscribe에 요청
    @PostMapping( 
        value = "/favorite",
        produces = "application/json;charset=UTF-8"
    )
    public BookDTO.Response favoriteBook(@RequestBody BookDTO.FavoriteBookRequest request) {
        Book book = bookRepository.findById(request.getBookId())
            .orElseThrow(() -> new RuntimeException("Book not found"));
        
        book.addWishlist();

        return BookDTO.toResponse(book);
    }

    // 도서 정보 수정
    @PatchMapping( 
        produces = "application/json;charset=UTF-8"
    )
    public BookDTO.Response editBookInfo(@RequestBody BookDTO.Patch book) {
        Book editedBook = bookRepository.findById(book.getBookId())
            .orElseThrow(() -> new RuntimeException("Book not found"));
        editedBook.setBookTitle(book.getBookTitle());
        editedBook.setBookContent(book.getBookContent());
        editedBook.setCategory(book.getCategory());
        editedBook.setBookSummary(book.getBookSummary());
        editedBook.setModifyDate(LocalDateTime.now());

        return BookDTO.toResponse(editedBook);
    }
    
    // 조회수 집계
    @GetMapping( 
        value = "/viewcount/{bookId}",
        produces = "application/json;charset=UTF-8"
    )
    public BookDTO.Response getViewCount(@PathVariable Long bookId) {
        Book book = bookRepository.findById(bookId)
            .orElseThrow(() -> new RuntimeException("Book not found"));

        return BookDTO.toResponse(book);
    }

    // 좋아요 수 집계
    @GetMapping( 
        value = "/likecount/{bookId}",
        produces = "application/json;charset=UTF-8"
    )
    public BookDTO.Response getLikeCount(@PathVariable Long bookId) {
        Book book = bookRepository.findById(bookId)
            .orElseThrow(() -> new RuntimeException("Book not found"));

        return BookDTO.toResponse(book);
    }


    /////////////////////////////////////////////////////
    // @RequestMapping(
    //     value = "/{id}/viewbook",
    //     method = RequestMethod.PUT,
    //     produces = "application/json;charset=UTF-8"
    // )
    // public Book viewBook(
    //     @PathVariable(value = "id") Long id,
    //     HttpServletRequest request,
    //     HttpServletResponse response
    // ) throws Exception {
    //     System.out.println("##### /book/viewBook  called #####");
    //     Optional<Book> optionalBook = bookRepository.findById(id);

    //     optionalBook.orElseThrow(() -> new Exception("No Entity Found"));
    //     Book book = optionalBook.get();
    //     book.viewBook();

    //     bookRepository.save(book);
    //     return book;
    // }
}
//>>> Clean Arch / Inbound Adaptor
