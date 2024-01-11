namespace user_service
{
    namespace auth
    {
        namespace dto
        {
            public class TokenDTO
            {
                public TokenDTO(string accessToken, string? refreshToken)
                {
                    AccessToken = accessToken;
                    RefreshToken = refreshToken;
                }
                
                public string AccessToken { get; set; } = null!;
                public string? RefreshToken { get; set; } = null;
            }
        }
    }
}