
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
                public string NewProfile { get; set; } = null!;
            }
        }
    }
}