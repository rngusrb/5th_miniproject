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
@RequestMapping(value="/authors")
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

     // 1) 회원가입 요청 (Command 받아서 Aggregate 호출)
    @PostMapping("/requestRegistration")
    public Author requestRegistration(
            @RequestBody RequestAuthorRegistrationCommand cmd
    ) {
        System.out.println("##### /authors/requestRegistration called #####");

        // ① Aggregate 생성 및 상태 세팅
        Author author = new Author();
        author.setAuthorLoginId(cmd.getAuthorLoginId());
        author.setAuthorPw(cmd.getAuthorPw());
        author.setCreateDate(new Date());
        author.setIsActive(true);

        // ② 도메인 메서드 호출 (이 안에서 AuthorRegistered 이벤트 publish)
        author.register();

        // ③ 저장
        return authorRepository.save(author);
    }

    // 2) 로그인 요청 (Command 받아서 Aggregate 호출)
    @PostMapping("/login")
    public Author login(
            @RequestBody RequestAuthorLoginCommand cmd
    ) throws Exception {
        System.out.println("##### /authors/login called #####");

        // ① 아이디로 조회
        Author author = authorRepository
            .findByAuthorLoginId(cmd.getAuthorLoginId())
            .orElseThrow(() -> new Exception("Invalid credentials"));

        // ② 비밀번호 검증
        if (!author.getAuthorPw().equals(cmd.getAuthorPw())) {
            throw new Exception("Invalid credentials");
        }

        // ③ 도메인 메서드 호출 (이 안에서 AuthorLoggedIn 이벤트 publish)
        author.login();

        // ④ 저장 (이벤트 발행을 위해 반드시 save)
        return authorRepository.save(author);
    }




}
//>>> Clean Arch / Inbound Adaptor
