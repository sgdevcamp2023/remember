
namespace user_service
{
    namespace auth
    {
        namespace entity
        {
            public class JwtModel
            {
                public JwtModel(string Email, string Token)
                {
                    this.Email = Email;
                    this.Token = Token;
                }

                public string Email { get; set; }
                public string Token { get; set; } = null!;
            }
        }
    }
}