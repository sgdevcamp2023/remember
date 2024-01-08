using System.ComponentModel.DataAnnotations;

namespace user_service
{
    namespace user
    {
        namespace dto
        {
            public class ChangePasswrodRequestDto
            {
                [Required(ErrorMessage = "OldPassword is required")]
                [MinLength(8, ErrorMessage = "Password must be at least 8 characters")]
                [MaxLength(50)]
                [DataType(DataType.Password)]
                public string OldPassword { get; set; } = null!;

                [Required(ErrorMessage = "NewPassword is required")]
                [MinLength(8, ErrorMessage = "Password must be at least 8 characters")]
                [MaxLength(50)]
                [DataType(DataType.Password)]
                [RegularExpression(@"^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[^\da-zA-Z]).{8,}$", ErrorMessage = "비밀번호는 대소문자, 숫자, 특수문자를 포함해야 합니다.")]
                public string NewPassword { get; set; } = null!;
            }

            public class ChangeNameRequestDto
            {
                [Required(ErrorMessage = "Name is required")]
                public string Name { get; set; } = null!;
            }
        }
    }
}