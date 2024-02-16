
using System.Data;
using Microsoft.Data.SqlClient;
using user_service.logger;

namespace user_service
{
    namespace common
    {
        public class DbConnectionManager
        {
            private IConfiguration _config;
            private IBaseLogger _logger;
            private string? _connectionString;
            public DbConnectionManager(IConfiguration config, IBaseLogger logger)
            {
                _config = config;
                _logger = logger;
                _connectionString = _config.GetConnectionString("MSSQLConnection");
            }

            ~DbConnectionManager()
            {
                // Close();
            }

            public void Connect(SqlConnection connection)
            {
                try
                {
                    if (connection.State != System.Data.ConnectionState.Open)
                        connection.Open();
                }
                catch (Exception e)
                {
                    throw new user_service.common.exception.SqlException(e.Message);
                }
            }

            public void Close(SqlConnection connection)
            {
                try
                {
                    connection.Close();
                }
                catch (Exception e)
                {
                    throw new user_service.common.exception.SqlException(e.Message);
                }
            }

            public void ExecuteNonQuery(string query)
            {
                try
                {
                    using (SqlConnection connection = new SqlConnection(_connectionString))
                    {
                        connection.Open();

                        using (SqlCommand command = new SqlCommand(query, connection))
                            command.ExecuteNonQuery();
                    }
                }
                catch (Exception e)
                {
                    throw new user_service.common.exception.SqlException(e.Message);
                }
            }

            public List<T> ExecuteReader<T>(string query, Func<IDataReader, T> createInstance)
            {
                try
                {
                    using (SqlConnection connection = new SqlConnection(_connectionString))
                    {
                        connection.Open();

                        List<T> datas = new List<T>();

                        using (SqlCommand command = new SqlCommand(query, connection))
                        using (var reader = command.ExecuteReader())
                        {
                            while (reader.Read())
                            {
                                datas.Add(createInstance(reader));
                            }
                        }

                        return datas;
                    }
                }
                catch (Exception e)
                {
                    throw new user_service.common.exception.SqlException(e.Message);
                }
            }
        }
    }
}