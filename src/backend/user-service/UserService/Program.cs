using user_service.auth.repository;
using user_service.auth.service;
using user_service.common;
using user_service.error;
using user_service.friend.repository;
using user_service.friend.service;
using user_service.logger;
using user_service.user.service;

ErrorManager.Init();

var builder = WebApplication.CreateBuilder(args);
ConfigurationManager configurationBinder = builder.Configuration;

// secretconfig
builder.Configuration.AddJsonFile("secretconfig.json", optional: true, reloadOnChange: true);

// 로거 의존성 주입
builder.Services.AddSingleton<IBaseLogger, FileLogger>();

// DB 의존성 주입
builder.Services.AddSingleton<DbConnectionManager>();
builder.Services.AddSingleton<RedisConnectionManager>();

// Repository 의존성 주입
builder.Services.AddScoped<IUserRepository, UserRepository>();
builder.Services.AddScoped<IAuthRedisRepository, AuthRedisRepository>();
builder.Services.AddScoped<IFriendRepository, FriendRepository>();

// Service 의존성 주입
builder.Services.AddScoped<AuthService>();
builder.Services.AddScoped<JwtService>();
builder.Services.AddScoped<UserService>();
builder.Services.AddScoped<FriendService>();

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

app.UseExceptionHandler(exceptionHandlerApp =>
{
    exceptionHandlerApp.Run(CustomErrorHandler.MyRequestDelegate); 
});

app.MapControllers();

app.Run();