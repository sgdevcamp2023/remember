namespace user_service
{
    namespace exception
    {
        public class CustomException : Exception
        {
            int codeCount;
            public CustomException(int message, int codeCount = 1) : base(message.ToString())
            {
                this.codeCount = codeCount;
            }

            public override string ToString()
            {
                string str = $"Exception {codeCount} : {Message}";
                return str;
            }        
        }

    }
}