package harmony.communityservice.board.board.application.port.in;

public interface LoadBoardsQuery {

    LoadBoardsResponse loadList(LoadBoardsCommand loadBoardsCommand);
}
