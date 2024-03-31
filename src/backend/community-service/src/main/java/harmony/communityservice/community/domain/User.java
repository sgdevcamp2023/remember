package harmony.communityservice.community.domain;

import harmony.communityservice.community.mapper.ToRoomResponseDtoMapper;
import harmony.communityservice.community.query.dto.SearchRoomResponse;
import harmony.communityservice.community.query.dto.SearchRoomsResponse;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "user")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @Column(name = "user_id")
    private Long userId;

    @NotBlank
    private String email;

    @NotBlank
    private String nickname;

    @NotBlank
    private String profile;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<GuildUser> guildUsers = new ArrayList<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<RoomUser> roomUsers = new ArrayList<>();

    @Builder
    public User(Long userId, String email, String nickname, String profile) {
        this.userId = userId;
        this.email = email;
        this.nickname = nickname;
        this.profile = profile;
    }

    public void modifyProfile(String profile) {
        this.profile = profile;
    }

    public void modifyNickname(String nickname) {
        this.nickname = nickname;
    }

    public List<Long> getRoomIds() {
        return roomUsers
                .stream()
                .map(user -> user.getRoom().getRoomId())
                .toList();
    }

    public SearchRoomsResponse makeSearchRoomsResponse() {
        List<SearchRoomResponse> searchRoomResponses = roomUsers
                .stream()
                .map(RoomUser::getRoom)
                .map(ToRoomResponseDtoMapper::convert)
                .collect(Collectors.toList());
        return new SearchRoomsResponse(searchRoomResponses);
    }
}
