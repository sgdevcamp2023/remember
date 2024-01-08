
namespace user_service
{
    namespace common
    {
        public interface IUserRepository
        {
            bool InsertUser(UserModel user);
            bool UpdateUser(UserModel user);
            UserModel? GetUserByEmail(string email);
            UserModel? GetUserById(int id);
        }
    }
}