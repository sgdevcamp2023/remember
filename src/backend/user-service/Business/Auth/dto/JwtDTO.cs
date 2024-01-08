namespace user_service
{
    namespace auth
    {
        namespace dto
        {
            public class JwtDTO
            {
                public JwtDTO(string refreshToken, string accessToken)
                {
                    this.RefreshToken = refreshToken;
                    this.AccessToken = accessToken;
                }
                
                public string RefreshToken { get; set; } = null!;
                public string AccessToken { get; set; } = null!;
            }
        }
    }
}