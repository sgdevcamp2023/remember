using Microsoft.AspNetCore.Diagnostics;
using Microsoft.AspNetCore.Mvc;
using user_service.common;
using user_service.config;

ErrorManager.Init();

var builder = WebApplication.CreateBuilder(args);
ConfigurationManager configurationBinder = builder.Configuration;

// DB 의존성 주입
builder.Services.AddSingleton<DbConnectionManager>(new DbConnectionManager(configurationBinder.GetConnectionString("DefaultConnection")));
builder.Services.AddSingleton<RedisConnectionManager>(new RedisConnectionManager(configurationBinder.GetConnectionString("RedisConnection")));

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
        if(errorCode != 0)
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

app.UseExceptionHandler(exceptionHandlerApp => {
    exceptionHandlerApp.Run(async context => {
        context.Response.StatusCode = 400;
        context.Response.ContentType = "application/json";
        var exceptionHandlerPathFeature = context.Features.Get<IExceptionHandlerPathFeature>();
        if(exceptionHandlerPathFeature != null)
        {
            int errorCode = int.Parse(exceptionHandlerPathFeature.Error.Message);
            var result = ErrorManager.ErrorCode[errorCode];
            await context.Response.WriteAsync(result.ToString());
        }
    });
});

app.MapControllers();

app.Run();