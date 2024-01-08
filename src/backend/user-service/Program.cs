using user_service.common;

var builder = WebApplication.CreateBuilder(args);
ConfigurationManager configurationBinder = builder.Configuration;

// DB 의존성 주입
builder.Services.AddSingleton<DbConnectionManager>(new DbConnectionManager(configurationBinder.GetConnectionString("DefaultConnection")));
builder.Services.AddSingleton<RedisConnectionManager>(new RedisConnectionManager(configurationBinder.GetConnectionString("RedisConnection")));

// Repository 의존성 주입
builder.Services.AddScoped<IUserRepository, UserRepository>();

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

app.MapControllers();

app.Run();