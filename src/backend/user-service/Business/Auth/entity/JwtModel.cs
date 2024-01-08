
namespace user_service
{
    namespace auth
    {
        namespace entity
        {
            public class JwtModel
            {
                public JwtModel(long Id, string Token)
                {
                    this.Id = Id;
                    this.Token = Token;
                }

                public long Id { get; set; }
                public string Token { get; set; } = null!;
            }
        }
    }
}