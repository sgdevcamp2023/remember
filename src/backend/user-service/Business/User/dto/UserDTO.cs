using System.Data;

namespace user_service
{
    namespace user
    {
        namespace dto
        {
            public class UserDTO
            {
                public long Id { get; set; }
                public string Email { get; set; } = null!;
                public string Name { get; set; } = null!;
                public string? ProfileUrl { get; set; }
            }
        }
    }
}