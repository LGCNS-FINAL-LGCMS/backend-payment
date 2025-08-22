package com.lgcms.payment.service.internal;

import com.lgcms.payment.dto.internal.request.JoinLectureRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "RemoteLectureService" , url="/internal/lecture")
public interface LectureService {

    @PostMapping("/join")
    void joinStudent(@RequestBody JoinLectureRequest joinLectureRequest);

    @GetMapping("/verify")
    public Boolean isExist(@RequestParam("memberId") Long memberId, @RequestParam("lectureId") String lectureId);
}
