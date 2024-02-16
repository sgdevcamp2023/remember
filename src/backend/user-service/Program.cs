using user_service.auth.repository;
using user_service.auth.service;
using user_service.Business.Auth.service;
using user_service.common;
using user_service.Common;
using user_service.error;
using user_service.friend.repository;
using user_service.friend.service;
using user_service.intercepter;
using user_service.logger;
using user_service.middleware;
using user_service.user.service;

ErrorManager.Init();

var builder = WebApplication.CreateBuilder(args);
ConfigurationManager configurationBinder = builder.Configuration;

// secretconfig
builder.Configuration.AddJsonFile("./SecretZuZu/secretconfig.json", optional: true, reloadOnChange: true);

// 로거 의존성 주입
builder.Services.AddSingleton<IBaseLogger, FileLogger>();

// 서비스 인터셉터
builder.Services.AddHttpContextAccessor();
builder.Services.AddScoped<LogInterceptor>();

// DB 의존성 주입
builder.Services.AddSingleton<DbConnectionManager>();
builder.Services.AddSingleton<RedisConnectionManager>();

// Repository 의존성 주입
builder.Services.AddScoped<IUserRepository, UserRepository>();
builder.Services.AddScoped<IAuthRedisRepository, AuthRedisRepository>();
builder.Services.AddScoped<IFriendRepository, FriendRepository>();

// Service 의존성 주입
builder.Services.AddScoped<IAuthService, AuthService>();
builder.Services.AddScoped<IUserService, UserService>();
builder.Services.AddScoped<IFriendService, FriendService>();

// OutService 의존성 주입
builder.Services.AddScoped<ICommunityClient, CommunityClient>();
builder.Services.AddScoped<IStateClient, StateClient>();

// Controller 소문자로 변경
builder.Services.AddRouting(options => options.LowercaseUrls = true);

// CORS 설정
builder.Services.AddCors(options =>
{
    options.AddPolicy("AllowReact", builder =>
    {
        builder.WithOrigins("http://localhost:3000")    // 리액트 앱의 주소
                .WithOrigins("http://10.99.29.133:3000") // 리액트 앱의 주소
                .WithOrigins("http://127.0.0.1:3000") // 리액트 앱의 주소
                .WithOrigins("https://127.0.0.1:3000") // 리액트 앱의 주소
                .WithOrigins("https://l1ocalhost:3000")
                .AllowAnyHeader()
                .AllowAnyMethod()
                .AllowCredentials();
    });

    options.AddDefaultPolicy(builder =>
    {
        builder.AllowAnyOrigin()
                .AllowAnyHeader()
                .AllowAnyMethod();
    });
});

// 에러 설정
builder.Services.AddControllers().ConfigureApiBehaviorOptions(options =>
{
    options.InvalidModelStateResponseFactory = CustomErrorHandler.ModelStateErrorHandler;
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
}

// CORS 설정
app.UseCors("AllowReact");

app.UseExceptionHandler(exceptionHandlerApp =>
{
    exceptionHandlerApp.Run(CustomErrorHandler.MyRequestDelegate);
});

app.UseMiddleware<LoggerMiddleware>();

app.MapControllers();

app.Run();