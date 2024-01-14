using user_service.common;
using user_service.user.dto;

namespace user_service
{
    namespace friend
    {
        namespace repository
        {
            public class FriendRepository : IFriendRepository
            {
                private RedisConnectionManager _redisConnectionManager;
                private DbConnectionManager _dbConnectionManager;

                public FriendRepository(RedisConnectionManager redisConnectionManager,
                                        DbConnectionManager dbConnectionManager)
                {
                    _redisConnectionManager = redisConnectionManager;
                    _dbConnectionManager = dbConnectionManager;
                }

                public List<UserDTO>? GetFriendList(long id)
                {
                    string query = "SELECT * FROM users WHERE id IN (SELECT first_friend_id FROM  friend WHERE second_user_id = 1 UNION SELECT second_friend_id FROM friend WHERE first_user_id = 1);";
                    throw new NotImplementedException();
                }

                public List<UserDTO>? ShowAllFriendRequestList(long id)
                {
                    throw new NotImplementedException();
                }

                public bool SendFriendRequest(long id, string email)
                {
                    throw new NotImplementedException();
                }

                public bool AcceptFriendRequest(long id, string email)
                {
                    throw new NotImplementedException();
                }

                public bool RefuseFriendRequest(long id, string email)
                {
                    throw new NotImplementedException();
                }

                public bool DeleteFriend(long id, string email)
                {
                    throw new NotImplementedException();
                }
            }
        }
    }
}