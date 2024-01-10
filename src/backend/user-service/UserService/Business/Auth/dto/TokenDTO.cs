namespace user_service
{
    namespace auth
    {
        namespace dto
        {
            public class TokenDTO
            {
                public TokenDTO(string accessToken, string refreshToken)
                {
                    this.AccessToken = accessToken;
                    this.RefreshToken = refreshToken;
                }
                
                public string AccessToken { get; set; } = null!;
                public string RefreshToken { get; set; } = null!;
            }
        }
    }
}