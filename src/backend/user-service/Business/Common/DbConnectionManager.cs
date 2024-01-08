using Microsoft.Data.SqlClient;

namespace user_service
{
    namespace common
    {
        public class DbConnectionManager
        {
            private string? _connectionString;
            private SqlConnection? _connection = null;
            public DbConnectionManager(string connectionString)
            {
                _connectionString = connectionString;
                _connection = new SqlConnection(_connectionString);
            }

            public SqlConnection GetConnection()
            {
                if(_connection == null)
                    throw new System.Exception("DB connection is null");
                return _connection;
            }
        }
    }
}