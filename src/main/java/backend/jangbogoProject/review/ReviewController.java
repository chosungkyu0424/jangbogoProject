package backend.jangbogoProject.review;

import backend.jangbogoProject.member.service.MemberService;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;
    private final MemberService memberService;

    // http://localhost:8080/review/market
    @GetMapping("/review/market")
    public ModelAndView market(){
        ModelAndView mav = new ModelAndView("review/market");
        return mav;
    }

    // http://localhost:8080/review/user
    @GetMapping("/review/user")
    public ModelAndView userReview(){
        ModelAndView mav = new ModelAndView("review/user");
        return mav;
    }

    // http://localhost:8080/review/write
    @GetMapping("/review/write")
    public ModelAndView write(){
        ModelAndView mav = new ModelAndView("review/write");
        return mav;
    }

    @PostMapping("/writeReview")
    public String writeReview(@RequestBody ReviewDTO reviewDTO){
        reviewDTO.setMember_id(memberService.findEmail(reviewDTO.getEmail()).get().getId().intValue());

        Review saveReview = reviewService.save(reviewDTO.toEntity());
        JsonObject data = new JsonObject();

        if(saveReview == null){
            data.addProperty("res", "이미 등록되어있는 리뷰 Id");
        }else{
            data.addProperty("res", "리뷰 작성 성공");
        }
        return data.toString();
    }

    @GetMapping("/getReviewListByMemberId")
    public String getReviewListByMemberId(@RequestParam String email){
        List<Review> reviewList = reviewService.findAllById(memberService.findEmail(email).get().getId().intValue());

        Gson gson = new Gson();
        String listJson = gson.toJson(reviewService.switchToResponseDTO(reviewList), List.class).toString();
        System.out.println(listJson);

        return listJson;
    }

    @GetMapping("/getReviewListByMarket")
    public String getReviewListByMarket(@RequestParam int marketSerialNum){
        List<Review> reviewList = reviewService.findAllByMarketSerialNum(marketSerialNum);

        Gson gson = new Gson();
        String listJson = gson.toJson(reviewService.switchToResponseDTO(reviewList), List.class).toString();
        System.out.println(listJson);

        return listJson;
    }
}
