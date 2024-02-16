using user_service.auth.dto;

namespace user_service.Business.Auth.service;

public interface IAuthService
{
    public Task RegisterAsync(RegisterDTO registerDTO, string traceId);
    public void SendEmailChecksum(string email);
    public void SendMailResetPassword(string email);
    public ClaimDTO Login(LoginDTO loginDTO);
}