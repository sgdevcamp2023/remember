
using System.ComponentModel.DataAnnotations;

namespace user_service
{
    namespace user
    {
        namespace dto
        {
            public class PasswordDTO
            {
                [Required(ErrorMessage = "4021")]
                public long UserId { get; set; }

                [Required(ErrorMessage = "4002")]
                public string Password { get; set; } = null!;

                [Required(ErrorMessage = "4002")]
                [RegularExpression(@"^(?=.*[a-z])(?=.*\d)(?=.*[^\da-zA-Z]).{8,20}$", ErrorMessage = "4003")]
                public string NewPassword { get; set; } = null!;
            }
        }
    }
}