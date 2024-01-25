
using System.ComponentModel.DataAnnotations;
using Microsoft.AspNetCore.Mvc;
using Org.BouncyCastle.Ocsp;
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
            public AuthController(AuthService authService)
            {
                _authService = authService;
            }

            [HttpPost("register")]
            public IActionResult Register([FromBody] RegisterDTO register)
            {
                _authService.Register(register);

                return Ok();
            }

            [HttpPost("login")]
            public ClaimDTO Login([FromBody] LoginDTO login)
            {
                ClaimDTO claim = _authService.Login(login);
                
                return claim;
            }

            [HttpPost("send-email")]
            public IActionResult SendEmailCheck(
                [FromBody] EmailRequestDTO email)
            {
                _authService.SendEmailChecksum(email.Email);

                return Ok();
            }

            [filter.TraceIdCheckFilter]
            [HttpPost("logout")]
            public IActionResult Logout(
                [FromHeader(Name = "user-id")] long userId)
            {
                _authService.Logout(userId);

                return Ok();
            }

            // [HttpPost("validation-token")]
            // public IActionResult ValidationToken(
            //     [FromHeader(Name = "Authorization")] string accessToken)
            // {
            //     string? refreshToken = HttpContext.Request.Cookies["refreshToken"];

            //     // API Gateway를 위한 Custom
            //     return Ok(
            //         _jwtService.ValidationToken(
            //             new TokenDTO(accessToken, refreshToken)));
            // }

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