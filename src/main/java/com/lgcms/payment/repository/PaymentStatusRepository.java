package com.lgcms.payment.repository;

import com.lgcms.payment.domain.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentStatusRepository extends JpaRepository<PaymentStatus, Long> {
    PaymentStatus findByMemberId(Long memberId);

    List<PaymentStatus> findAllByMemberId(Long memberId);

    void deleteAllByMemberId(Long memberId);
}
