namespace user_service
{
    namespace common
    {
        namespace exception
        {
            public class RedisException : CommonException
            {
                public RedisException(string message) : base(message)
                {

                }

                public override string ToString()
                {
                    string str = $"Redis Exception : {Message}";
                    return str;
                }
            }
        }
    }
}