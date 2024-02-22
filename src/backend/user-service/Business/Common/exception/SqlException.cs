namespace user_service
{
    namespace common
    {
        namespace exception
        {
            public class SqlException : CommonException
            {
                public SqlException(string message) : base(message)
                {

                }

                public override string ToString()
                {
                    string str = $"Sql Exception : {Message}";
                    return str;
                }
            }
        }
    }
}