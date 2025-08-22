package com.lgcms.payment.service;

import com.lgcms.payment.common.config.payment.KakaoConfig;
import com.lgcms.payment.common.dto.exception.BaseException;
import com.lgcms.payment.common.dto.exception.PaymentError;
import com.lgcms.payment.domain.PaymentStatus;
import com.lgcms.payment.dto.internal.request.JoinLectureRequest;
import com.lgcms.payment.dto.kakao.request.KakaoApproveRequest;
import com.lgcms.payment.dto.kakao.request.KakaoCancelRequest;
import com.lgcms.payment.dto.kakao.request.KakaoReadyRequest;
import com.lgcms.payment.dto.kakao.response.KakaoApproveResponse;
import com.lgcms.payment.dto.kakao.response.KakaoCancelResponse;
import com.lgcms.payment.dto.kakao.response.KakaoReadyResponse;
import com.lgcms.payment.dto.request.PaymentApproveRequest;
import com.lgcms.payment.dto.request.PaymentReadyRequest;
import com.lgcms.payment.dto.response.PaymentReadyResponse;
import com.lgcms.payment.repository.CartRepository;
import com.lgcms.payment.repository.PaymentStatusRepository;
import com.lgcms.payment.service.internal.LectureService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class PaymentService {

    private final KakaoConfig kakaoConfig;
    private final WebClient webClient;
    private final LectureService lectureService;
    private final PaymentStatusRepository paymentStatusRepository;
    private final CartRepository cartRepository;

    @Transactional
    public PaymentReadyResponse getReady(PaymentReadyRequest paymentReadyRequest, Long memberId) {
//        if (lectureService.isExist(memberId, paymentReadyRequest.LectureId())) {
//            throw new BaseException(PaymentError.ALREADY_PAYMENT_LECTURE);
//        }

        KakaoReadyRequest kakaoReadyRequest = KakaoReadyRequest.builder()
                .cid(kakaoConfig.getCid())
                .partnerOrderId(kakaoConfig.getPartnerOrderId())
                .partnerUserId(kakaoConfig.getPartnerUserId())
                .itemName(paymentReadyRequest.title())
                .quantity(1)
                .totalAmount(paymentReadyRequest.price())
                .taxFreeAmount(kakaoConfig.getTaxFreeAmount())
                .approvalUrl(kakaoConfig.getApprovalUrl())
                .failUrl(kakaoConfig.getFailUrl())
                .cancelUrl(kakaoConfig.getCancelUrl())
                .build();

        KakaoReadyResponse kakaoReadyResponse = webClient.post()
                .uri("/ready")
                .bodyValue(kakaoReadyRequest)
                .retrieve()
                .bodyToMono(KakaoReadyResponse.class)
                .block();

        PaymentStatus paymentStatus = PaymentStatus.builder()
                .memberId(memberId)
                .lectureId(paymentReadyRequest.LectureId())
                .build();
        paymentStatusRepository.save(paymentStatus);

        return new PaymentReadyResponse(kakaoReadyResponse.getTid(), kakaoReadyResponse.getNext_redirect_pc_url());
    }

    @Transactional
    public void getApprove(PaymentApproveRequest paymentApproveRequest, Long memberId) {
        try {
            KakaoApproveRequest kakaoApproveRequest = KakaoApproveRequest.builder()
                    .tid(paymentApproveRequest.tid())
                    .pgToken(paymentApproveRequest.token())
                    .partnerOrderId(kakaoConfig.getPartnerOrderId())
                    .partnerUserId(kakaoConfig.getPartnerUserId())
                    .cid(kakaoConfig.getCid())
                    .build();

            KakaoApproveResponse kakaoApproveResponse = webClient.post()
                    .uri("/approve")
                    .bodyValue(kakaoApproveRequest)
                    .retrieve()
                    .bodyToMono(KakaoApproveResponse.class)
                    .block();

        }catch (Exception e){
            throw new BaseException(PaymentError.PAYMENT_FAIL);
        }

//        try {
//            List<PaymentStatus> paymentStatusList = paymentStatusRepository.findAllByMemberId(memberId);
//            for (PaymentStatus paymentStatus : paymentStatusList) {
//                lectureService.joinStudent(new JoinLectureRequest(paymentStatus.getLectureId(), memberId));
//            }
//        } catch (Exception e) {
//            //결제 취소
//
//            KakaoCancelRequest kakaoCancelRequest = KakaoCancelRequest.builder()
//                    .cancel_tax_free_amount(kakaoApproveResponse.getAmount().getTax_free())
//                    .cancel_amount(kakaoApproveResponse.getAmount().getTotal())
//                    .cid(kakaoConfig.getCid())
//                    .tid(kakaoApproveResponse.getTid())
//                    .build();
//
//            KakaoCancelResponse kakaoCancelResponse = webClient.post()
//                    .uri("/cancel")
//                    .bodyValue(kakaoCancelRequest)
//                    .retrieve()
//                    .bodyToMono(KakaoCancelResponse.class)
//                    .block();
//
//            paymentStatusRepository.deleteAllByMemberId(memberId);
//
//            throw new BaseException(PaymentError.PAYMENT_FAIL);
//        }
    }

    public PaymentReadyResponse getListReady(List<PaymentReadyRequest> paymentReadyRequests, Long memberId) {
        String itemName = paymentReadyRequests.getFirst().title()+" 외" + (paymentReadyRequests.size()-1)+"건";
        Integer totalAmount = 0;
        List<PaymentStatus> paymentStatusList = new ArrayList<>();
        for(PaymentReadyRequest paymentReadyRequest : paymentReadyRequests){
            totalAmount += paymentReadyRequest.price();
            PaymentStatus paymentStatus = PaymentStatus.builder()
                    .memberId(memberId)
                    .lectureId(paymentReadyRequest.LectureId())
                    .build();
            paymentStatusList.add(paymentStatus);
        }

        KakaoReadyRequest kakaoReadyRequest = KakaoReadyRequest.builder()
                .cid(kakaoConfig.getCid())
                .partnerOrderId(kakaoConfig.getPartnerOrderId())
                .partnerUserId(kakaoConfig.getPartnerUserId())
                .itemName(itemName)
                .quantity(1)
                .totalAmount(totalAmount)
                .taxFreeAmount(kakaoConfig.getTaxFreeAmount())
                .approvalUrl(kakaoConfig.getApprovalUrl())
                .failUrl(kakaoConfig.getFailUrl())
                .cancelUrl(kakaoConfig.getCancelUrl())
                .build();

        KakaoReadyResponse kakaoReadyResponse = webClient.post()
                .uri("/ready")
                .bodyValue(kakaoReadyRequest)
                .retrieve()
                .bodyToMono(KakaoReadyResponse.class)
                .block();

            paymentStatusRepository.saveAll(paymentStatusList);

            return new PaymentReadyResponse(kakaoReadyResponse.getTid(), kakaoReadyResponse.getNext_redirect_pc_url());
    }
}
