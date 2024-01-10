using Microsoft.AspNetCore.Diagnostics;
using Microsoft.AspNetCore.Mvc;
using user_service.common;
using user_service.common.exception;
using user_service.config;
using user_service.logger;

ErrorManager.Init();

var builder = WebApplication.CreateBuilder(args);
ConfigurationManager configurationBinder = builder.Configuration;

// 로거 의존성 주입
builder.Services.AddSingleton<IBaseLogger, FileLogger>();

// DB 의존성 주입
builder.Services.AddSingleton<DbConnectionManager>();
builder.Services.AddSingleton<RedisConnectionManager>();

// Repository 의존성 주입
builder.Services.AddScoped<IUserRepository, UserRepository>();

// Service 의존성 주입


// 에러 설정
builder.Services.AddControllers().ConfigureApiBehaviorOptions(options =>
{
    options.InvalidModelStateResponseFactory = actionContext =>
    {
        // 있는지 체크?
        var errorList = actionContext.ModelState.Values.SelectMany(x => x.Errors.Select(m => m.ErrorMessage)).ToArray();
        int.TryParse(errorList[0], out int errorCode);
        if (errorCode != 0)
            return ErrorManager.GetErrorCodeResult(errorCode);

        return new BadRequestObjectResult(actionContext.ModelState);
    };
});

builder.Services.AddControllers();
// Learn more about configuring Swagger/OpenAPI at https://aka.ms/aspnetcore/swashbuckle
builder.Services.AddEndpointsApiExplorer();
builder.Services.AddSwaggerGen();

var app = builder.Build();

// Configure the HTTP request pipeline.
if (app.Environment.IsDevelopment())
{
    app.UseSwagger();
    app.UseSwaggerUI();
    app.UseDeveloperExceptionPage();
}

app.UseExceptionHandler(exceptionHandlerApp =>
{
    exceptionHandlerApp.Run(async context =>
    {
        var exceptionHandlerPathFeature = context.Features.Get<IExceptionHandlerPathFeature>();
        if (exceptionHandlerPathFeature != null)
        {
            var exception = exceptionHandlerPathFeature.Error;

            if (exception is ServiceException serviceException)
            {       
                context.Response.StatusCode = 400;
                context.Response.ContentType = "application/json";
                if (exceptionHandlerPathFeature != null)
                {
                    var result = ErrorManager.GetErrorCodeResult(serviceException.statusCode);
                    string? json = result.ToString();
                    if(json != null)
                        await context.Response.WriteAsync(json);
                }
            }
            else if(exception is RedisException)
            {
                context.Response.StatusCode = 500;
                context.Response.ContentType = "text/plain";
                if (exceptionHandlerPathFeature != null)
                {
                    await context.Response.WriteAsync(exceptionHandlerPathFeature.Error.Message);
                }
            }
        }
    });
});

app.MapControllers();

app.Run();