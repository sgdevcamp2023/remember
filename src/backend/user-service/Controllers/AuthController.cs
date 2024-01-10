
using System.ComponentModel.DataAnnotations;
using Microsoft.AspNetCore.Mvc;
using user_service.auth.dto;
using user_service.auth.service;

namespace user_service
{
    namespace auth
    {
        [Route("api/user/auth/[controller]")]
        [ApiController]
        public class AuthController : ControllerBase
        {
            private AuthService _authService;
            private JwtService _jwtService;
            public AuthController(AuthService authService, JwtService jwtService)
            {
                this._authService = authService;
                this._jwtService = jwtService;
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
                Response.Headers["Set-Cookie"] = @$"refreshToken={tokens.RefreshToken};
                                                    Path=/api/user/auth;
                                                    Domain=localhost;
                                                    HttpOnly;";
                return Ok();
            }

            [HttpGet("check-email/{email}")]
            public IActionResult CheckSameEmail([EmailAddress(ErrorMessage = "4001")] string email)
            {
                return Ok();
            }

            [HttpPost("send-email")]
            public IActionResult SendEmailCheck(
                [FromBody]
                [Required(ErrorMessage = "4000")]
                [EmailAddress(ErrorMessage = "4001")]
                 string email)
            {
                return Ok();
            }

            [HttpPost("logout")]
            public IActionResult Logout()
            {
                return Ok();
            }

            [HttpPost("refresh-token")]
            public IActionResult RefreshToken(
                [FromBody] string refreshToken)
            {
                return Ok();
            }

            [HttpPost("validation-token")]
            public IActionResult ValidationToken(
                [FromBody] string accessToken)
            {
                
                return Ok();
            }

            [HttpPost("find-password")]
            public IActionResult FindPassword(
                [FromBody] string email)
            {
                return Ok();
            }
        }
    }
}