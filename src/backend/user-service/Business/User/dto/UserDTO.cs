namespace user_service.user.dto;

public class UserDTO
{
    public long Id { get; set; }
    public string Email { get; set; } = null!;
    public string Name { get; set; } = null!;
    public string Profile { get; set; } = null!;
}