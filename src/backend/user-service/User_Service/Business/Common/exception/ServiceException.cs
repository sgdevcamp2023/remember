namespace user_service
{
    namespace common
    {
        namespace exception
        {
            public class ServiceException : Exception
            {
                
                public int codeCount;
                public int statusCode;
                public ServiceException(int statusCode, int codeCount = 1) : base("Service Exception")
                {
                    this.statusCode = statusCode;
                    this.codeCount = codeCount;
                }

                public override string ToString()
                {
                    string str = $"Exception {statusCode} : {Message}";
                    return str;
                }
            }
        }
    }
}