package com.lgcms.payment.repository;

import com.lgcms.payment.domain.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    List<Cart> findAllByMemberId(Long memberId);

    Long countByMemberId(Long memberId);
}
