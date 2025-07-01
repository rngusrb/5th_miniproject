package project.infra;

import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import project.domain.*;

//<<< Clean Arch / Inbound Adaptor

@RestController
// @RequestMapping(value="/authors")
@Transactional
public class AuthorController {

    @Autowired
    AuthorRepository authorRepository;

     // ✅ 작가 등록
    @PostMapping("/register")
    public Author registerAuthor(@RequestBody Author author, HttpServletRequest request, HttpServletResponse response) throws Exception {
        System.out.println("##### /authors/register called #####");
        author.setCreateDate(new java.util.Date());
        author.setIsActive(true);  // 기본값으로 활성화
        author.register();
        return authorRepository.save(author);
    }

    // ✅ 작가 수정
    @PutMapping("/{id}")
    public Author updateAuthor(@PathVariable("id") Long id, @RequestBody Author updatedAuthor) throws Exception {
        System.out.println("##### /authors/{id} PUT called #####");
        Optional<Author> optionalAuthor = authorRepository.findById(id);
        if (!optionalAuthor.isPresent()) {
            throw new Exception("Author not found with id: " + id);
        }

        Author author = optionalAuthor.get();
        author.setAuthorName(updatedAuthor.getAuthorName());
        author.setAuthorInfo(updatedAuthor.getAuthorInfo());
        author.setAuthorPortfolio(updatedAuthor.getAuthorPortfolio());
        author.setAuthorPw(updatedAuthor.getAuthorPw());
        author.setIsActive(updatedAuthor.getIsActive());

        return authorRepository.save(author);
    }

    // ✅ 작가 삭제
    @DeleteMapping("/{id}")
    public void deleteAuthor(@PathVariable("id") Long id) throws Exception {
        System.out.println("##### /authors/{id} DELETE called #####");
        Optional<Author> optionalAuthor = authorRepository.findById(id);
        if (!optionalAuthor.isPresent()) {
            throw new Exception("Author not found with id: " + id);
        }

        authorRepository.delete(optionalAuthor.get());
    }

    // ✅ 단일 작가 조회
    @GetMapping("/{id}")
    public Author getAuthorById(@PathVariable("id") Long id) throws Exception {
        return authorRepository.findById(id)
            .orElseThrow(() -> new Exception("Author not found with id: " + id));
    }

    // ✅ 작가 전체 목록 조회
    @GetMapping("")
    public Iterable<Author> getAllAuthors() {
        return authorRepository.findAll();
    }
}
//>>> Clean Arch / Inbound Adaptor
