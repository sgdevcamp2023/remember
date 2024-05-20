package harmony.communityservice.board.board.adapter.out.persistence;

import harmony.communityservice.board.board.domain.Board;
import harmony.communityservice.board.board.domain.Board.BoardId;
import harmony.communityservice.board.board.domain.Image;
import harmony.communityservice.board.board.domain.ModifiedType;
import harmony.communityservice.guild.channel.domain.Channel.ChannelId;
import java.util.List;

class BoardMapper {

    static Board convert(BoardEntity boardEntity) {

        List<Image> images = boardEntity.getImages()
                .stream()
                .map(imageEntity -> Image.make(imageEntity.getImageId().getId(), imageEntity.getImageUrl()))
                .toList();
        return Board.builder()
                .profile(boardEntity.getWriterInfo().getCommonUserInfo().getProfile())
                .username(boardEntity.getWriterInfo().getCommonUserInfo().getNickname())
                .boardId(BoardId.make(boardEntity.getBoardId().getId()))
                .title(boardEntity.getContent().getTitle())
                .content(boardEntity.getContent().getContent())
                .createdAt(boardEntity.getCreatedAt())
                .type(ModifiedType.valueOf(boardEntity.getType().name()))
                .writerId(boardEntity.getWriterInfo().getWriterId())
                .images(images)
                .channelId(ChannelId.make(boardEntity.getChannelId().getId()))
                .build();
    }
}
