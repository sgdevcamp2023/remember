using System.ComponentModel.DataAnnotations;

namespace user_service
{
    namespace auth
    {
        public class EmailRequestDTO
        {
            [Required(ErrorMessage = "4000")]
            [EmailAddress(ErrorMessage = "4001")]
            public string Email { get; set; } = null!;
        }
    }
}