namespace user_service.Business.User.dto;

public class LogoutDTO
{
    public long UserId { get; set; }
    public string Email { get; set; } = null!;
}