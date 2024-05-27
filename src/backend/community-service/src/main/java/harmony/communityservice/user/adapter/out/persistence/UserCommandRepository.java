package harmony.communityservice.user.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

interface UserCommandRepository extends JpaRepository<UserEntity, UserIdJpaVO> {

    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query("update UserEntity u set u.userInfo.commonUserInfo.userProfile = :profile where u.userId = :userId")
    void updateProfile(@Param("profile") String profile, @Param("userId") UserIdJpaVO userId);

    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query("update UserEntity u set u.userInfo.commonUserInfo.nickname = :nickname where u.userId = :userId")
    void updateNickname(@Param("nickname") String nickname, @Param("userId") UserIdJpaVO userId);
}
