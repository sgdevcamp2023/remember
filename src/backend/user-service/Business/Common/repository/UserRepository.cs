

using System.Data;
using user_service.auth.dto;
using user_service.logger;

namespace user_service
{
    namespace common
    {
        public class UserRepository : IUserRepository
        {
            private DbConnectionManager _dbConnectionManager = null!;
            public UserRepository(DbConnectionManager dbConnectionManager)
            {
                _dbConnectionManager = dbConnectionManager;
            }
            public bool IsEmailExist(string email)
            {
                string query = $"SELECT * FROM users WHERE email = '{email}'";
                using (var reader = _dbConnectionManager.ExecuteReader(query))
                {
                    return reader.Read();
                }
            }

            public UserModel? GetUserByEmail(string email)
            {
                string query = $"SELECT * FROM users WHERE email = {email}";
                return GetUserModel(query);
            }

            public UserModel? GetUserById(long id)
            {
                string query = $"SELECT * FROM users WHERE id = {id}";
                return GetUserModel(query);
            }

            private UserModel? GetUserModel(string query)
            {
                try
                {
                    UserModel? user = null;
                    using (var reader = _dbConnectionManager.ExecuteReader(query))
                    {
                        if (reader.Read())
                        {
                            user = GetUserModel(reader);
                        }
                    }

                    return user;
                }
                catch
                {
                    return null;
                }
            }

            public bool InsertUser(RegisterDTO user)
            {
                string query = @$"INSERT INTO users (email, password, name, profile) 
                                VALUES ('{user.Email}', '{user.Password}', '{user.Username}', '{user.ProfileUrl}'))";
                _dbConnectionManager.ExecuteNonQuery(query);

                return true;
            }
            public bool UpdateUser(UserModel user)
            {
                string query = @$"UPDATE users 
                                SET email = '{user.Email}', 
                                    password = '{user.Password}', 
                                    name = '{user.Name}', 
                                    updated_at = '{user.UpdatedAt}' 
                                WHERE id = {user.Id}";
                _dbConnectionManager.ExecuteNonQuery(query);

                return true;
            }

            public bool DeleteUser(string email)
            {
                string query = @$"DELETE 
                                FROM users 
                                WHERE email = '{email}'";
                _dbConnectionManager.ExecuteNonQuery(query);

                return true;
            }

            public bool DeleteUser(long id)
            {
                string query = $"DELETE FROM users WHERE id = {id}";
                _dbConnectionManager.ExecuteNonQuery(query);

                return true;
            }

            public List<UserModel> GetFriends(long id)
            {
                string query = @$"SELECT *
                                FROM test
                                WHERE id IN (
                                    SELECT first_user_id
                                    FROM friend
                                    WHERE second_user_id = {id}
                                    UNION
                                    SELECT second_user_id
                                    FROM friend
                                    WHERE first_user_id = {id}
                                )";

                List<UserModel> friends = new List<UserModel>();
                using (var reader = _dbConnectionManager.ExecuteReader(query))
                {
                    while (reader.Read())
                    {
                        UserModel user = GetUserModel(reader);

                        friends.Add(user);
                    }
                }

                return friends;
            }

            private UserModel GetUserModel(IDataReader reader)
            {
                return new UserModel(
                    reader.GetInt64(0),
                    reader.GetString(reader.GetOrdinal("email")),
                    reader.GetString(reader.GetOrdinal("password")),
                    reader.GetString(reader.GetOrdinal("name")),
                    reader.GetDateTime(reader.GetOrdinal("created_at")),
                    reader.GetDateTime(reader.GetOrdinal("updated_at")),
                    reader.IsDBNull(reader.GetOrdinal("profile")) ? null : reader.GetString(reader.GetOrdinal("profile")),
                    reader.GetBoolean(reader.GetOrdinal("is_deleted"))
                );
            }
        }
    }
}