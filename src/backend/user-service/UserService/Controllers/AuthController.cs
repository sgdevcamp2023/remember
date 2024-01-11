
using System.ComponentModel.DataAnnotations;
using Microsoft.AspNetCore.Mvc;
using user_service.auth.dto;
using user_service.auth.service;

namespace user_service
{
    namespace auth
    {
        [Route("api/user/[controller]")]
        [ApiController]
        public class AuthController : ControllerBase
        {
            private AuthService _authService;
            private JwtService _jwtService;
            public AuthController(AuthService authService, JwtService jwtService)
            {
                _authService = authService;
                _jwtService = jwtService;
            }

            [HttpPost("register")]
            public IActionResult Register([FromBody] RegisterDTO register)
            {
                _authService.Register(register);

                return Ok();
            }

            [HttpPost("login")]
            public IActionResult Login([FromBody] LoginDTO login)
            {
                TokenDTO tokens = _jwtService.CreateToken(login);
                Response.Headers["Authorization"] = $"Bearer {tokens.AccessToken}";
                Response.Headers["Set-Cookie"] = $"refreshToken={tokens.RefreshToken};Path=/api/user/auth;Domain=localhost;HttpOnly;";
                return Ok();
            }

            [HttpPost("send-email")]
            public IActionResult SendEmailCheck(
                [FromBody] EmailRequestDTO email)
            {
                _authService.SendEmailChecksum(email.Email);

                return Ok();
            }

            [HttpPost("check-email")]
            public IActionResult CheckEmail(
                [FromBody] EmailDTO email)
            {
                _authService.CheckEmailChecksum(email.Email, email.EmailChecksum);

                return Ok();
            }

            [HttpPost("logout")]
            public IActionResult Logout()
            {
                

                return Ok();
            }

            [HttpPost("validation-token")]
            public IActionResult ValidationToken(
                [FromHeader(Name = "Authorization")] string accessToken)
            {
                string? refreshToken = HttpContext.Request.Cookies["refreshToken"];
                _jwtService.ValidationToken(new TokenDTO(accessToken, refreshToken));

                return Ok();
            }

            [HttpPost("reset-password")]
            public IActionResult ResetPassword(
                [FromBody] EmailRequestDTO email)
            {
                _authService.SendMailResetPassword(email.Email);

                return Ok();
            }
        }
    }
}