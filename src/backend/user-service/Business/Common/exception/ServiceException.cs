namespace user_service
{
    namespace common
    {
        namespace exception
        {
            public class ServiceException : Exception
            {
                public int ErrorCode { get; set; }
                public ServiceException(int errorCode) : base("Service Exception")
                {
                    ErrorCode = errorCode;
                }

                public override string ToString()
                {
                    string str = $"Exception {ErrorCode} : {Message}";
                    return str;
                }
            }
        }
    }
}