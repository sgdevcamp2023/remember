namespace user_service
{
    namespace auth
    {
        namespace dto
        {
            public class JwtDTO
            {
                public JwtDTO(string Token, DateTime ExpiresAt)
                {
                    this.Token = Token;
                    this.ExpiresAt = ExpiresAt;
                }

                public string Token { get; set; } = null!;
                public DateTime ExpiresAt { get; set; }
            }
        }
    }
}