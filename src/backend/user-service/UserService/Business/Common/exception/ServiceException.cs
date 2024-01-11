namespace user_service
{
    namespace common
    {
        namespace exception
        {
            public class ServiceException : Exception
            {
                public int errorCode;
                public ServiceException(int errorCode) : base("Service Exception")
                {
                    this.errorCode = errorCode;
                }

                public override string ToString()
                {
                    string str = $"Exception {errorCode} : {Message}";
                    return str;
                }
            }
        }
    }
}