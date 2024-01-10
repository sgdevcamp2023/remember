
namespace user_service
{
    namespace common
    {
        public interface IUserRepository
        {
            bool InsertUser(UserModel user);
            bool UpdateUser(UserModel user);
            bool DeleteUser(int id);
            bool DeleteUser(string email);
            UserModel? GetUserByEmail(string email);
            UserModel? GetUserById(int id);
        }
    }
}