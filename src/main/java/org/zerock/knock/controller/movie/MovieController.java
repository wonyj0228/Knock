package org.zerock.knock.controller.movie;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.zerock.knock.dto.dto.controller.USER_AND_MOVIE_ID_DTO;
import org.zerock.knock.dto.dto.movie.MOVIE_DTO;
import org.zerock.knock.service.layerClass.Movie;

import java.util.Map;

/**
 * @author nks
 * @apiNote Movie 요청을 받는 Controller
 */
@RestController
@RequestMapping("/api/movie")
public class MovieController {

    private final Movie movieService;
    public MovieController(Movie movieService) {
        this.movieService = movieService;
    }

    /**
     * 요청 시 MOVIE 인덱스에 저장된 모든 객체 정보를 가져와 반환한다. 
     * 반환 시 openingTime, poster 정보는 변환하여 반환
     * @return ResponseEntity<Iterable<MOVIE_DTO>> : Movie 저장된 모든 영화 정보
     */
    @GetMapping()
    public ResponseEntity<Iterable<MOVIE_DTO>> getMovies() {
        return ResponseEntity.ok(movieService.readMovies());
    }

    /**
     * 요청 시 MOVIE 인덱스에 대상 객체 정보를 가져와 반환한다.
     * 반환 시 openingTime, poster 정보는 변환하여 반환
     * @param movieId : 대상 영화의 ID
     * @return ResponseEntity<Iterable<MOVIE_DTO>> : Movie 저장된 모든 영화 정보
     */
    @GetMapping("/getDetail")
    public ResponseEntity<MOVIE_DTO> getDetail(@RequestParam String movieId) {
        return ResponseEntity.ok(movieService.readMoviesDetail(movieId));
    }

    /**
     * 요청 시 현재 상영 예정작 영화에 있는 모든 LEVEL_TWO CATEGORY 정보를 가져와 반환
     * @return ResponseEntity<Iterable<CATEGORY_LEVEL_TWO_DTO>> : 현재 상영 예정 리스트에 있는 영화들
     */
    @GetMapping("/getCategory")
    public ResponseEntity<Map<String, Object>> getCategory() {

        return ResponseEntity.ok(movieService.getCategory());
    }


    /**
     * 요청 시 해당 영화를 구독한 다른 사람들이 공통으로 구독하고 있는 영화를 리스트로 만들어 반환한다.
     * @param movieId : 확인하고 싶은 영화의 id
     * @return ResponseEntity<Iterable<MOVIE_DTO>> : 대상 영화를 구독한 사람들이 공통적으로 구독한 영화들
     */
    @GetMapping("/recommend")
    public ResponseEntity<Iterable<MOVIE_DTO>> getRecommend(@RequestParam String movieId) {

        return null;
    }

    /**
     * 영화를 구독한다
     * @param requestBody : 구독할 영화의 ID, 대상자 ID
     * @return boolean : 대상 영화 구독 성공 여부
     */
    @CrossOrigin
    @PostMapping("/sub")
    public ResponseEntity<Boolean> subscribe(@RequestBody USER_AND_MOVIE_ID_DTO requestBody)
    {

        String userId = requestBody.getUserId();
        String movieId = requestBody.getMovieId();

        return ResponseEntity.ok(movieService.subscribeMovie(userId, movieId));
    }

    /**
     * 영화를 구독 해지한다.
     * @param requestBody : 구독 해지할 영화의 ID 대상자 Id
     * @return boolean : 대상 영화 구독 해지 성공 여부
     */
    @PostMapping("cancelSub")
    public ResponseEntity<Boolean> subscribeCancel (@RequestBody USER_AND_MOVIE_ID_DTO requestBody) {

        String userId = requestBody.getUserId();
        String movieId = requestBody.getMovieId();

        return ResponseEntity.ok(movieService.subscribeCancelMovie(userId, movieId));
    }

    /**
     * 영화를 구독 해지한다.
     * @param requestBody : 구독 정보를 확인할 영화의 ID 대상자 Id
     * @return boolean : 대상 영화 구독 여부
     */
    @PostMapping("isSubscribe")
    public ResponseEntity<Boolean> subscribeCheck(@RequestBody USER_AND_MOVIE_ID_DTO requestBody) {

        String userId = requestBody.getUserId();
        String movieId = requestBody.getMovieId();

        return ResponseEntity.ok(movieService.subscribeCheck(userId, movieId));

    }

}
