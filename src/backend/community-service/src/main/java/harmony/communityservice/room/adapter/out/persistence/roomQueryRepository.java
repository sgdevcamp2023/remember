package harmony.communityservice.room.adapter.out.persistence;

import harmony.communityservice.user.adapter.out.persistence.UserIdJpaVO;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

interface roomQueryRepository extends JpaRepository<RoomEntity, RoomIdJpaVO> {
    @Query("select distinct r from RoomEntity r join fetch r.roomUserEntities ru where ru.userId = :userId")
    List<RoomEntity> findRoomsByUserIdsContains(@Param("userId") UserIdJpaVO userId);

    @Query("select r.roomIdJpaVO from RoomEntity r join r.roomUserEntities ru where ru.userId = :userId")
    List<RoomIdJpaVO> findRoomIdsByUserIDsContains(@Param("userId") UserIdJpaVO userId);
}
