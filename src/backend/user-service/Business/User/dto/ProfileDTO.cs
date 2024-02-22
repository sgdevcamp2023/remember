

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
                public long UserId { get; set; }
                
                [Required(ErrorMessage = "4023")]
                public IFormFile NewProfile { get; set; } = null!;
            }

            public class ProfileResponseDTO
            {
                public string Profile { get; set; } = null!;
            }
        }
    }
}