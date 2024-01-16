using System.Data;
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
                private RedisConnectionManager _redis;
                private DbConnectionManager _db;
                // 
                private string SendRequestKey = "my send request ";
                private string ReceiveReuqsetKey = "my receive request";
                public FriendRepository(RedisConnectionManager redisConnectionManager,
                                        DbConnectionManager dbConnectionManager)
                {
                    _redis = redisConnectionManager;
                    _db = dbConnectionManager;
                }

                public long GetFriendId(string email)
                {
                    string query = "SELECT * FROM users WHERE email = '{email}'";
                    var reader = _db.ExecuteReader(query);
                    if (!reader.Read())
                        return 0;

                    long friendId = reader.GetInt64(reader.GetOrdinal("id"));
                    return friendId;
                }   

                public bool CheckAlreadyFriend(long id, long friendId)
                {
                    string query = $"SELECT * FROM friends WHERE first_user_id = {id} AND second_user_id = {friendId} OR first_user_id = {friendId} AND second_user_id = {id}";
                    var reader = _db.ExecuteReader(query);
                    if(reader.Read())
                        return true;
                    return false;
                }

                public List<UserDTO> GetFriendList(long id)
                {
                    string query = "SELECT * FROM users WHERE id IN (SELECT first_friend_id FROM  friend WHERE second_user_id = 1 UNION SELECT second_friend_id FROM friend WHERE first_user_id = 1);";
                    var reader = _db.ExecuteReader(query);

                    return MakeListUserDTO(reader);
                }

                
                public List<UserDTO>? ShowAllFriendRequestList(long id)
                {
                    string emailGetQuery = $"SELECT email FROM users WHERE id = {id}";
                    var emailReader = _db.ExecuteReader(emailGetQuery);

                    if(!emailReader.Read())
                        return null;

                    string email = emailReader.GetString(emailReader.GetOrdinal("email"));

                    List<string> friendRequestList = _redis.GetListByKey(email);
                    if (friendRequestList.Count == 0)
                        return null;

                    string query = $"SELECT * FROM users WHERE ID IN ({string.Join(' ', friendRequestList)})";
                    var reader = _db.ExecuteReader(query);

                    return MakeListUserDTO(reader);
                }

                public bool SendFriendRequest(long id, long friendId)
                {
                    // 존재하는 유저인지 체크
                    if(_redis.GetListByKey(SendRequestKey + id).Contains(friendId.ToString()))
                        return false;
                        
                    _redis.InsertList(ReceiveReuqsetKey + friendId, id.ToString());
                    _redis.InsertList(SendRequestKey + id, friendId.ToString());
                    
                    return true;
                }

                public bool AcceptFriendRequest(long id, long friendId)
                {
                    // string query = $"INSERT INTO friend "
                    throw new NotImplementedException();
                }

                public bool RefuseFriendRequest(long id, long friendId)
                {
                    throw new NotImplementedException();
                }

                public bool DeleteFriend(long id)
                {
                    string query = $"DELETE FROM friend WHERE first_user_id = {id} OR second_user_id = {id}";
                    _db.ExecuteNonQuery(query);

                    return true;
                }

                public List<UserDTO> MakeListUserDTO(IDataReader reader)
                {
                    List<UserDTO> list = new List<UserDTO>();
                    while (reader.Read())
                    {
                        list.Add(MakeUserDTO(reader));
                    }

                    return list;
                }


                private UserDTO MakeUserDTO(IDataReader reader)
                {
                    return new UserDTO()
                    {
                        Id = reader.GetInt64(reader.GetOrdinal("id")),
                        Email = reader.GetString(reader.GetOrdinal("email")),
                        Name = reader.GetString(reader.GetOrdinal("name")),
                        ProfileUrl = reader.IsDBNull(reader.GetOrdinal("profile")) ? null : reader.GetString(reader.GetOrdinal("profile"))
                    };
                }
            }
        }
    }
}