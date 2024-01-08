

namespace user_service
{
    namespace common
    {
        public class UserRepository : IUserRepository
        {
            private DbConnectionManager? _dbConnectionManager = null;
            public UserRepository(DbConnectionManager dbConnectionManager)
            {
                _dbConnectionManager = dbConnectionManager;
            }

            public UserModel? GetUserByEmail(string email)
            {

                throw new NotImplementedException();
            }

            public UserModel? GetUserById(int id)
            {
                throw new NotImplementedException();
            }

            public bool InsertUser(UserModel user)
            {
                throw new NotImplementedException();
            }

            public bool UpdateUser(UserModel user)
            {
                throw new NotImplementedException();
            }
        }
    }
}