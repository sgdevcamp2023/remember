using Castle.DynamicProxy;
using Microsoft.AspNetCore.Mvc;
using user_service.auth.dto;
using user_service.Business.Auth.service;
using user_service.intercepter;
using user_service.logger;

namespace user_service
{
    namespace auth
    {
        [Route("api/[controller]")]
        [ApiController]
        public class AuthController : ControllerBase
        {
            private IAuthService _authService;
            public AuthController(IAuthService authService, IBaseLogger logger, IHttpContextAccessor accessor, LogInterceptor interceptor)
            {
                var generator = new ProxyGenerator();
                _authService = generator.CreateInterfaceProxyWithTarget<IAuthService>(authService, interceptor);
            }

            [HttpPost("register")]
            public IActionResult Register([FromBody] RegisterDTO register)
            {
                if (_authService.Register(register))
                {
                    return Ok();
                }

                return BadRequest();
            }

            [HttpPost("login")]
            public ClaimDTO Login([FromBody] LoginDTO login)
            {
                System.Console.WriteLine(login.Email + " " + login.Password);
                ClaimDTO claim = _authService.Login(login);

                return claim;
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