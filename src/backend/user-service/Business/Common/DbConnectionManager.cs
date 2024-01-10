using Microsoft.Data.SqlClient;
using user_service.logger;

namespace user_service
{
    namespace common
    {
        public class DbConnectionManager
        {
            private IConfiguration _config;
            private string? _connectionString;
            private SqlConnection _connection = null!;
            private FileLogger _logger;
            public DbConnectionManager(IConfiguration config, FileLogger logger)
            {
                _config = config;
                _logger = logger;
                _connectionString = _config.GetConnectionString("DefaultConnection");
                _connection = new SqlConnection(_connectionString);
            }

            ~DbConnectionManager()
            {
                Close();
            }

            public void Connect()
            {
                try
                {
                    if(_connection.State == System.Data.ConnectionState.Closed)
                        _connection.Open();
                }
                catch (Exception e)
                {
                    _logger.Log(e.Message);

                    throw new user_service.common.exception.SqlException("Database connection error");
                }
            }
            
            public void Close()
            {
                try
                {
                    _connection.Close();
                }
                catch(Exception e)
                {
                    _logger.Log(e.Message);

                    throw new user_service.common.exception.SqlException("Database connection error");
                }
            }

            public void ExecuteNonQuery(string query)
            {
                try
                {
                    Connect();
                    SqlCommand command = new SqlCommand(query, _connection);
                    command.ExecuteNonQuery();
                }
                catch (Exception e)
                {
                    _logger.Log(e.Message);

                    throw new user_service.common.exception.SqlException("Database SQL Query error");
                }
            }

            public SqlDataReader ExecuteReader(string query)
            {
                try
                {
                    Connect();
                    SqlCommand command = new SqlCommand(query, _connection);
                    return command.ExecuteReader();
                }
                catch (Exception e)
                {
                    _logger.Log(e.Message);

                    throw new user_service.common.exception.SqlException("Database SQL Query error");
                }
            }
        }
    }
}