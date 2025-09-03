package com.lgcms.payment.service.internal;

import com.lgcms.payment.dto.internal.request.JoinLectureRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "RemoteLectureService")
public interface LectureService {

    @PostMapping("/internal/lecture/student/join")
    public void joinStudent(@RequestBody JoinLectureRequest joinLectureRequest);

    @GetMapping("/internal//lecture/student/verify")
    public Boolean isExist(@RequestParam("memberId") Long memberId, @RequestParam("lectureId") String lectureId);
}
