package harmony.communityservice.board.board.adapter.out.persistence;

import harmony.communityservice.board.board.domain.Board;
import harmony.communityservice.guild.channel.adapter.out.persistence.ChannelIdJpaVO;
import java.util.List;

class BoardEntityMapper {

    static BoardEntity convert(Board board) {

        List<ImageEntity> imageEntities = board.getImages().stream()
                .map(image -> ImageEntity.make(image.getImageUrl()))
                .toList();
        return BoardEntity.builder()
                .title(board.getContent().getTitle())
                .content(board.getContent().getContent())
                .writerProfile(board.getWriterInfo().getCommonUserInfo().getProfile())
                .writerName(board.getWriterInfo().getCommonUserInfo().getNickname())
                .writerId(board.getWriterInfo().getWriterId())
                .channelId(ChannelIdJpaVO.make(board.getChannelId().getId()))
                .images(imageEntities)
                .build();
    }
}
