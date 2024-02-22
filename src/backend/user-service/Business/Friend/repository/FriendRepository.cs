using System.Data;
using user_service.common;
using user_service.Controllers.dto.friend;

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
                private string SendRequestKey = "my send request ";
                private string ReceiveRequestKey = "my receive request ";
                public FriendRepository(RedisConnectionManager redisConnectionManager,
                                        DbConnectionManager dbConnectionManager)
                {
                    _redis = redisConnectionManager;
                    _db = dbConnectionManager;
                }

                public long GetUserIdtoEmail(string email)
                {
                    string query = $"SELECT * FROM users WHERE email = '{email}'";
                    List<long> ids = _db.ExecuteReader<long>(query, MakeUserId);
                    if (ids.Count == 1)
                        return ids[0];

                    return -1;
                }

                public string GetUserEmailToId(long id)
                {
                    string query = $"SELECT * FROM users WHERE id = '{id}'";
                    List<string> ids = _db.ExecuteReader<string>(query, MakeEmail);
                    if (ids.Count == 1)
                        return ids[0];

                    return "";
                }

                public IdAndProfileDTO? GetIdAndProfile(string email)
                {
                    string query = $"SELECT * FROM users WHERE email = '{email}'";
                    List<IdAndProfileDTO> ids = _db.ExecuteReader<IdAndProfileDTO>(query, MakeIdAndProfile);
                    if (ids.Count == 1)
                        return ids[0];

                    return null;
                }

                public bool CheckAlreadyFriend(long id, long friendId)
                {
                    string query = $"SELECT * FROM friends WHERE (first_user_id = {id} AND second_user_id = {friendId}) OR (first_user_id = {friendId} AND second_user_id = {id});";
                    List<long> ids = _db.ExecuteReader<long>(query, MakeUserId);
                    if (ids.Count > 0)
                        return true;

                    return false;
                }

                public List<FriendInfoDTO> GetFriendList(long id)
                {
                    string query = $"SELECT * FROM users WHERE id IN (SELECT first_user_id FROM friends WHERE second_user_id = {id} UNION SELECT second_user_id FROM friends WHERE first_user_id = {id});";
                    List<FriendInfoDTO> friends = _db.ExecuteReader<FriendInfoDTO>(query, MakeFriendInfoDTO);

                    return friends;
                }

                public List<FriendInfoDTO> ShowAllSendRequestList(long id)
                {
                    List<string> sendRequestList = _redis.GetListByKey(SendRequestKey + id);
                    if (sendRequestList.Count == 0)
                        return new List<FriendInfoDTO>();

                    return GetUserDTOList(sendRequestList);
                }

                public List<FriendInfoDTO> ShowAllReceiveRequesttList(long id)
                {
                    List<string> receiveRequestList = _redis.GetListByKey(ReceiveRequestKey + id);
                    if (receiveRequestList.Count == 0)
                        return new List<FriendInfoDTO>();

                    return GetUserDTOList(receiveRequestList);
                }

                private List<FriendInfoDTO> GetUserDTOList(List<string> uers)
                {
                    string query = $"SELECT * FROM users WHERE id IN ({string.Join(' ', uers)})";
                    List<FriendInfoDTO> friends = _db.ExecuteReader<FriendInfoDTO>(query, MakeFriendInfoDTO);

                    return friends;
                }

                public bool SendFriendRequest(long id, long friendId)
                {
                    // 존재하는 유저인지 체크
                    if (true == _redis.GetListByKey(SendRequestKey + id).Contains(friendId.ToString()))
                        return false;

                    _redis.InsertList(ReceiveRequestKey + friendId, id.ToString());
                    _redis.InsertList(SendRequestKey + id, friendId.ToString());

                    return true;
                }

                public bool AcceptFriendRequest(long id, long friendId)
                {
                    // 레디스에서 체크
                    if (!_redis.GetListByKey(ReceiveRequestKey + id).Contains(friendId.ToString()))
                        return false;

                    System.Console.WriteLine(id + " " + friendId);
                    string query = $"INSERT INTO friends (first_user_id, second_user_id) VALUES ({id}, {friendId})";

                    _db.ExecuteNonQuery(query);

                    if (!DeleteRequest(friendId, id))
                        return false;

                    return true;
                }

                public bool CancleFriendRequest(long id, long friendId)
                {
                    if (!_redis.GetListByKey(SendRequestKey + id).Contains(friendId.ToString()))
                        return false;

                    if (!DeleteRequest(id, friendId))
                        return false;

                    return true;
                }
                public bool RefuseFriendRequest(long id, long friendId)
                {
                    if (!_redis.GetListByKey(ReceiveRequestKey + id).Contains(friendId.ToString()))
                        return false;

                    if (!DeleteRequest(friendId, id))
                        return false;

                    return true;
                }

                public bool DeleteFriend(long id, long friendId)
                {
                    string query = $"DELETE FROM friends WHERE (first_user_id = {id} AND second_user_id = {friendId}) OR (first_user_id = {friendId} AND second_user_id = {id})";
                    _db.ExecuteNonQuery(query);

                    return true;
                }

                private long MakeUserId(IDataReader reader)
                {
                    return reader.GetInt64(reader.GetOrdinal("id"));
                }

                private string MakeEmail(IDataReader reader)
                {
                    return reader.GetString(reader.GetOrdinal("email"));
                }

                private IdAndProfileDTO MakeIdAndProfile(IDataReader reader)
                {
                    return new IdAndProfileDTO()
                    {
                        Id = reader.GetInt64(reader.GetOrdinal("id")),
                        Profile = reader.GetString(reader.GetOrdinal("profile"))
                    };
                }

                private FriendInfoDTO MakeFriendInfoDTO(IDataReader reader)
                {
                    return new FriendInfoDTO()
                    {
                        Id = reader.GetInt64(reader.GetOrdinal("id")),
                        Email = reader.GetString(reader.GetOrdinal("email")),
                        Name = reader.GetString(reader.GetOrdinal("name")),
                        Profile = reader.GetString(reader.GetOrdinal("profile")),
                        IsOnline = false
                    };
                }

                private bool DeleteRequest(long sendId, long receiveId)
                {
                    _redis.DeleteList(SendRequestKey + sendId, receiveId.ToString());
                    _redis.DeleteList(ReceiveRequestKey + receiveId, sendId.ToString());

                    return true;
                }
            }
        }
    }
}