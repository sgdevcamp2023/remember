namespace user_service
{
    namespace error
    {
        public class ErrorCode
        {
            public int code { get; set; }
            public string message { get; set; } = null!;
            public string description { get; set; } = null!;
        }
    }
}