
using System.Data.Common;
using Microsoft.AspNetCore.Diagnostics;
using Microsoft.AspNetCore.Mvc;
using user_service.auth.exception;
using user_service.common.exception;

namespace user_service
{
    namespace error
    {
        public static class CustomErrorHandler
        {
            public static RequestDelegate MyRequestDelegate = async context =>
            {
                System.Console.WriteLine("MyRequestDelegate");
                
                var exceptionHandlerPathFeature = context.Features.Get<IExceptionHandlerPathFeature>();
                if (exceptionHandlerPathFeature != null)
                {
                    var exception = exceptionHandlerPathFeature.Error;
                    if (exception is TokenException tokenException)
                    {
                        if(context.Response.Headers.TryGetValue("Authorization", out var value))
                            context.Response.Headers.Remove("Authorization");
                        context.Response.Headers.Add("Authorization", $"Bearer {tokenException.tokenDTO.AccessToken}");
                        HttpBadRequest(context, tokenException.ErrorCode);
                    }
                    else if (exception is ServiceException serviceException)
                    {
                        HttpBadRequest(context, serviceException.ErrorCode);
                    }
                    else if (exception is RedisException redisException)
                    {
                        HttpInterealServer(context, exceptionHandlerPathFeature.Error.Message);
                    }
                    else if (exception is DbException dbException)
                    {
                        HttpInterealServer(context, exceptionHandlerPathFeature.Error.Message);
                    }
                    else
                    {
                        HttpInterealServer(context, exceptionHandlerPathFeature.Error.Message);
                    }

                    await Task.CompletedTask;
                }
            };
            public static IActionResult ModelStateErrorHandler(ActionContext context)
            {
                System.Console.WriteLine("InvalidModelStateResponseFactory");
                // 있는지 체크?
                var errorList = context.ModelState.Values.SelectMany(x => x.Errors.Select(m => m.ErrorMessage)).ToArray();
                if(errorList[0].Contains("accessToken"))
                    return ErrorManager.GetErrorCodeResult(4106);
                int.TryParse(errorList[0], out int errorCode);
                if (errorCode != 0)
                    return ErrorManager.GetErrorCodeResult(errorCode);

                return new BadRequestObjectResult(context.ModelState);
            }

            private static async void HttpBadRequest(HttpContext context, int errorCode)
            {
                context.Response.StatusCode = 400;
                context.Response.ContentType = "application/json";
                var error = ErrorManager.GetErrorCodeString(errorCode);
                await context.Response.WriteAsync(error);
            }
            private static async void HttpInterealServer(HttpContext context, string message)
            {
                context.Response.StatusCode = 500;
                context.Response.ContentType = "application/json";
                await context.Response.WriteAsync(message);
            }
        }
    }
}