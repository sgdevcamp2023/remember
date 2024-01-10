using System.ComponentModel.DataAnnotations;

namespace user_service
{
    namespace auth
    {
        namespace dto
        {
            public class EmailDTO
            {
                [Required(ErrorMessage = "4000")]
                [EmailAddress(ErrorMessage = "4001")]
                [DataType(DataType.EmailAddress)]
                public string Email { get; set; } = null!;

                [Required(ErrorMessage = "4004")]
                public string EmailChecksum { get; set; } = null!;
            }
        }
    }
}