
using System.ComponentModel.DataAnnotations;
using Microsoft.AspNetCore.Mvc;

namespace user_service
{
    namespace auth
    {
        [Route("api/user/auth/[controller]")]
        [ApiController]
        public class AuthController : ControllerBase
        {
            public AuthController()
            {

            }


            [HttpPost("register")]
            public IActionResult Register([FromBody] dto.AuthRegisterRequestDto request)
            {
                return Ok();
            }

            [HttpPost("login")]
            public IActionResult Login([FromBody] dto.AuthLoginRequestDto request)
            {
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