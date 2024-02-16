
using System.ComponentModel.DataAnnotations;

namespace user_service.user.dto;

public class NameDTO
{
    [Required(ErrorMessage = "4021")]
    public long UserId { get; set; }

    [Required(ErrorMessage = "4022")]
    public string NewName { get; set; } = null!;
}