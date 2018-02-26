package com.zeroexception.oauth2implementation.repositories;

import com.zeroexception.oauth2implementation.model.SocialId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * @author Viet Quoc Tran
 * on 9/7/17.
 * www.zeroexception.com
 */
public interface SocialIdRepository extends JpaRepository<SocialId, String> {
    SocialId findById(String id);
    SocialId queryById(String id);

    @Query("SELECT sd FROM SocialId sd JOIN FETCH sd.user WHERE sd.id = ?1")
    SocialId findBySocialIdFetch(String id);
}
