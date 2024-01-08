
using System.ComponentModel.DataAnnotations;

namespace user_service
{
    namespace auth
    {
        namespace dto
        {
            public class AuthRegisterRequestDto
            {
                [Required(ErrorMessage = "Email is required")]
                [EmailAddress]
                [DataType(DataType.EmailAddress)]
                [MaxLength(50)]
                public string Email { get; set; } = null!;

                [Required(ErrorMessage = "Username is required")]
                [MaxLength(50)]
                public string Username { get; set; } = null!;

                [Required(ErrorMessage = "Password is required")]
                [MinLength(8, ErrorMessage = "Password must be at least 8 characters")]
                [MaxLength(50)]
                [DataType(DataType.Password)]
                [RegularExpression(@"^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[^\da-zA-Z]).{8,}$", ErrorMessage = "비밀번호는 대소문자, 숫자, 특수문자를 포함해야 합니다.")]
                public string Password { get; set; } = null!;
            }

            public class AuthLoginRequestDto
            {
                [Required(ErrorMessage = "Email is required")]
                [EmailAddress]
                [DataType(DataType.EmailAddress)]
                [MaxLength(50)]
                public string Email { get; set; } = null!;

                [Required(ErrorMessage = "Password is required")]
                [MinLength(8, ErrorMessage = "Password must be at least 8 characters")]
                [MaxLength(50)]
                [DataType(DataType.Password)]
                [RegularExpression(@"^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[^\da-zA-Z]).{8,}$", ErrorMessage = "비밀번호는 대소문자, 숫자, 특수문자를 포함해야 합니다.")]
                public string Password { get; set; } = null!;
            }
        }
    }
}