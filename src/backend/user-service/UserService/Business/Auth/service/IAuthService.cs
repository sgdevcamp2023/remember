using user_service.auth.dto;

namespace user_service.Business.Auth.service;

public interface IAuthService
{
    public bool Register(RegisterDTO registerDTO);
    public void SendEmailChecksum(string email);
    public void SendMailResetPassword(string email);
    public ClaimDTO Login(LoginDTO loginDTO);
}