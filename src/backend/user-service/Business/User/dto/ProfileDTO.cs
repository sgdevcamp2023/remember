
using System.ComponentModel.DataAnnotations;

namespace user_service
{
    namespace user
    {
        namespace dto
        {
            public class ProfileDTO
            {
                [Required(ErrorMessage = "4021")]
                public string UserId { get; set; } = null!;
                
                [Required(ErrorMessage = "4023")]
                public IFormFile NewProfile { get; set; } = null!;
            }

            public class ProfileResponseDTO
            {
                public string ProfileUrl { get; set; } = null!;
            }
        }
    }
}