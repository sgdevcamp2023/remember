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
        [filter.DefaultHeaderFilter]
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
            public async Task<IActionResult> RegisterAsync(
                [FromHeader(Name = "trace-id")] string traceId,
                [FromBody] RegisterDTO register)
            {
                await _authService.RegisterAsync(register, traceId);

                return Ok();
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

            [HttpPost("send-email")]
            public IActionResult SendEmail(
                [FromBody] EmailRequestDTO email)
            {
                _authService.SendEmailChecksum(email.Email);

                return Ok();
            }
        }
    }
}