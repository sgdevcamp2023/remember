
using System.ComponentModel.DataAnnotations;

namespace user_service
{
    namespace friend
    {
        namespace dto
        {
            public class RequestEmailDto
            {
                [Required(ErrorMessage = "Email is required")]
                [EmailAddress]
                public string Email { get; set; } = null!;
            }
        }
    }
}