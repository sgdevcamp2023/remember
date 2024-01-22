using System.ComponentModel.DataAnnotations;

namespace user_service
{
    namespace auth
    {
        namespace dto
        {
            public class RegisterDTO
            {
                [Required(ErrorMessage = "4000")]
                [EmailAddress(ErrorMessage = "4001")]
                [DataType(DataType.EmailAddress)]
                public string Email { get; set; } = null!;

                [Required(ErrorMessage = "4005")]
                [RegularExpression(@"^[a-zA-Z_-]+$", ErrorMessage = "4006")]
                public string Username { get; set; } = null!;

                [Required(ErrorMessage = "4002")]
                [RegularExpression(@"^(?=.*[a-z])(?=.*\d)(?=.*[^\da-zA-Z]).{8,20}$", ErrorMessage = "4003")]
                [DataType(DataType.Password)]
                public string Password { get; set; } = null!;

                [Required(ErrorMessage = "4004")]
                public string EmailChecksum { get; set; } = null!;
            }
        }
    }
}
