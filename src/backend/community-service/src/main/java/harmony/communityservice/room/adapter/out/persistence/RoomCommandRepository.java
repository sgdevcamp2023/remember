package harmony.communityservice.room.adapter.out.persistence;

import harmony.communityservice.user.adapter.out.persistence.UserIdJpaVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

interface RoomCommandRepository extends JpaRepository<RoomEntity, RoomIdJpaVO> {

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("delete from RoomEntity r "
            + "where :first in (select ru.userId from r.roomUserEntities ru) "
            + "and :second in (select ru.userId from r.roomUserEntities ru)")
    void deleteRoomByUserIds(@Param("first") UserIdJpaVO first, @Param("second") UserIdJpaVO second);
}
