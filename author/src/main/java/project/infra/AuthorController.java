package project.infra;

import java.util.Date;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import project.domain.*;
import project.util.JwtUtil;

//<<< Clean Arch / Inbound Adaptor

@RestController
@RequestMapping(value="/authors")
@Transactional
public class AuthorController {

    @Autowired
    AuthorRepository authorRepository;

    @Autowired
    private JwtUtil jwtUtil; // JWT 유틸리티 주입

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
    // AuthorController.java

    public Author updateAuthor(@PathVariable("id") Long id, @RequestBody Author updatedAuthor) throws Exception {
        System.out.println("##### /authors/{id} PUT called #####");

        // 데이터베이스에서 기존 작가 정보를 가져옵니다.
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new Exception("Author not found with id: " + id));

        // 🔽 요청으로 받은 데이터의 필드가 null이 아닐 경우에만 값을 변경합니다.
        if (updatedAuthor.getAuthorName() != null) {
            author.setAuthorName(updatedAuthor.getAuthorName());
        }
        if (updatedAuthor.getAuthorInfo() != null) {
            author.setAuthorInfo(updatedAuthor.getAuthorInfo());
        }
        if (updatedAuthor.getAuthorPortfolio() != null) {
            author.setAuthorPortfolio(updatedAuthor.getAuthorPortfolio());
        }
        if (updatedAuthor.getAuthorPw() != null) {
            author.setAuthorPw(updatedAuthor.getAuthorPw());
        }
        if (updatedAuthor.getIsActive() != null) {
            author.setIsActive(updatedAuthor.getIsActive());
        }

        // 변경된 내용만 저장합니다.
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
    public ResponseEntity<Author> requestRegistration(
            @RequestBody RequestAuthorRegistrationCommand cmd
    ) {
        // ① 아이디로 기존 작가가 있는지 먼저 조회합니다.
        if (authorRepository.findByAuthorLoginId(cmd.getAuthorLoginId()).isPresent()) {
            // ② 만약 존재한다면, 409 Conflict 에러를 반환합니다.
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        // ③ 존재하지 않을 경우에만 새로 생성하고 저장합니다.
        Author author = new Author();
        author.setAuthorLoginId(cmd.getAuthorLoginId());
        author.setAuthorPw(cmd.getAuthorPw());
        author.setCreateDate(new Date());
        author.setIsActive(false);

        author.register();
        Author savedAuthor = authorRepository.save(author);

        return new ResponseEntity<>(savedAuthor, HttpStatus.OK);
    }

    // 2) 로그인 요청 (Command 받아서 Aggregate 호출)
    @PostMapping("/login")
    public AuthorLoginResponseDTO login(
            @RequestBody RequestAuthorLoginCommand cmd
    ) throws Exception {
        System.out.println("##### /authors/login called #####");

        Author author = authorRepository
                .findByAuthorLoginId(cmd.getAuthorLoginId())
                .orElseThrow(() -> new Exception("Invalid credentials"));

        if (!author.getAuthorPw().equals(cmd.getAuthorPw())) {
            throw new Exception("Invalid credentials");
        }

        author.login();
        authorRepository.save(author);

        String token = jwtUtil.generateToken(author.getAuthorId());

        // 별도의 파일로 생성된 DTO 객체를 생성하여 반환
        return new AuthorLoginResponseDTO(
                author.getAuthorId(),
                author.getAuthorLoginId(),
                token
        );
    }




}
//>>> Clean Arch / Inbound Adaptor
