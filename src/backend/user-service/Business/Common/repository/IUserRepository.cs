
using user_service.auth.dto;

namespace user_service
{
    namespace common
    {
        public interface IUserRepository
        {
            bool InsertUser(RegisterDTO user);
            bool UpdateUser(UserModel user);
            bool DeleteUser(long id);
            bool DeleteUser(string email);
            bool IsEmailExist(string email);
            UserModel? GetUserByEmail(string email);
            UserModel? GetUserById(long id);

            List<UserModel> GetFriends(long id);
        }
    }
}