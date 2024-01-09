namespace user_service
{
    namespace auth
    {
        namespace dto
        {
            public class JwtDTO
            {
                public JwtDTO(string accessToken, string refreshToken)
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