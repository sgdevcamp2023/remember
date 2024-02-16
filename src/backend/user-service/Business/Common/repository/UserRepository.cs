using System.Data;
using user_service.auth.dto;

namespace user_service
{
    namespace common
    {
        public class UserRepository : IUserRepository
        {   
            private string _defaultImage;
            private DbConnectionManager _db = null!;
            public UserRepository(IConfiguration configuration, DbConnectionManager dbConnectionManager)
            {
                _defaultImage = configuration["DefaultProfileImage"];
                _db = dbConnectionManager;
            }
            public bool IsEmailExist(string email)
            {
                string query = $"SELECT * FROM users WHERE email = '{email}'";
                var readers = _db.ExecuteReader<UserModel>(query, GetUserModel);
                
                return readers.Count > 0;
            }

            public UserModel? GetUserByEmail(string email)
            {
                string query = $"SELECT * FROM users WHERE email = '{email}'";
                return GetUserModel(query);
            }

            public UserModel? GetUserById(long id)
            {
                string query = $"SELECT * FROM users WHERE id = {id}";
                return GetUserModel(query);
            }

            private UserModel? GetUserModel(string query)
            {
                UserModel? user = null;
                List<UserModel> datas = _db.ExecuteReader<UserModel>(query, GetUserModel);
                if (datas.Count == 1)
                {
                    user = datas[0];
                }
                
                return user;
            }

            public bool InsertUser(RegisterDTO user)
            {
                string query = $"INSERT INTO users (email, password, name, profile) VALUES ('{user.Email}', '{user.Password}', '{user.Username}', '{_defaultImage}')";
                _db.ExecuteNonQuery(query);

                return true;
            }
            public bool UpdateUser(UserModel user)
            {
                string query = $"UPDATE users SET email = '{user.Email}', password = '{user.Password}', name = '{user.Name}', updated_at = '{user.UpdatedAt}' WHERE id = {user.Id}";
                _db.ExecuteNonQuery(query);

                return true;
            }

            public bool DeleteUser(string email)
            {
                string query = $"DELETE FROM users WHERE email = '{email}'";
                _db.ExecuteNonQuery(query);

                return true;
            }

            public bool DeleteUser(long id)
            {
                string query = $"DELETE FROM users WHERE id = {id}";
                _db.ExecuteNonQuery(query);

                return true;
            }

            public List<UserModel> GetFriends(long id)
            {
                string query = $"SELECT * FROM test WHERE id IN (SELECT first_user_id FROM friend WHERE second_user_id = {id} UNION SELECT second_user_id FROM friend WHERE first_user_id = {id})";

                List<UserModel> friends = _db.ExecuteReader<UserModel>(query, GetUserModel);
                return friends;
            }

            private UserModel GetUserModel(IDataReader reader)
            {
                return new UserModel(
                    reader.GetInt64(reader.GetOrdinal("id")),
                    reader.GetString(reader.GetOrdinal("email")),
                    reader.GetString(reader.GetOrdinal("password")),
                    reader.GetString(reader.GetOrdinal("name")),
                    reader.GetString(reader.GetOrdinal("profile")),
                    reader.GetDateTime(reader.GetOrdinal("created_at")),
                    reader.GetDateTime(reader.GetOrdinal("updated_at")),
                    reader.GetBoolean(reader.GetOrdinal("is_deleted"))
                );
            }

            public bool UpdateName(long id, string name)
            {
                string query = $"UPDATE users SET name = '{name}' WHERE id = {id}";
                _db.ExecuteNonQuery(query);

                return true;
            }

            public bool UpdatePassword(string email, string password)
            {
                string query = $"UPDATE users SET password ='{password}' WHERE email = '{email}'";
                _db.ExecuteNonQuery(query);

                return true;
            }

            public bool UpdateProfile(long id, string profileUrl)
            {
                string query = $"UPDATE users SET profile = '{profileUrl}' WHERE id = {id}";
                _db.ExecuteNonQuery(query);

                return true;
            }

            public bool UpdatePassword(long id, string password)
            {
                string query = $"UPDATE users SET password ='{password}' WHERE id = {id}";
                _db.ExecuteNonQuery(query);

                return true;
            }


        }
    }
}