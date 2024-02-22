
using user_service.auth.dto;

namespace user_service
{
    namespace common
    {
        public interface IUserRepository
        {
            bool InsertUser(RegisterDTO user);
            bool UpdateUser(UserModel user);
            bool UpdateName(long id, string name);
            bool UpdatePassword(string email, string password);
            bool UpdatePassword(long id, string password);
            bool UpdateProfile(long id, string profile);
            bool DeleteUser(long id);
            bool DeleteUser(string email);
            bool IsEmailExist(string email);
            UserModel? GetUserByEmail(string email);
            UserModel? GetUserById(long id);

            List<UserModel> GetFriends(long id);
        }
    }
}