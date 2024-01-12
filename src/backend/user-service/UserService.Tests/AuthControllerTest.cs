using Moq;
using Xunit;
using Microsoft.AspNetCore.Mvc;
using user_service.auth.dto;
using user_service.auth;
using user_service.auth.service;

public class AuthControllerTest
{
    private readonly AuthController _authController;
    private readonly Mock<AuthService> _mockAuthService;
    private readonly Mock<JwtService> _mockJwtService;

    public AuthControllerTest()
    {
        _mockAuthService = new Mock<AuthService>();
        _mockJwtService = new Mock<JwtService>();
        _authController = new AuthController(_mockAuthService.Object, _mockJwtService.Object);
    }

    [Fact]
    public void Send_Eail_Checksum_ReturnsOkResult_WhenEmailIsValid()
    {
        string email = "fnvl7855@naver.com";
        // Arrange
        _mockAuthService.Setup(service => service.SendEmailChecksum(email));

        var result = _authController.SendEmailCheck(email);

        var okResult = Assert.IsType<OkObjectResult>(result);
        Assert.Equal("testToken", okResult.Value);
    }
    
    // [Fact]
    // public void Register_ReturnsOkResult_WhenCredentialsAreValid()
    // {
    //     // Arrange
    //     var registerDTO = new RegisterDTO {
    //         Email = "fnvl7855@naver.com",
    //         Username = "test", 
    //         Password = "test",
    //         EmailCheck = "1234" };

    //     _mockAuthService.Setup(service => service.Register(registerDTO));

    //     // Act
    //     var result = _authController.Register(registerDTO);

    //     // Assert
    //     var okResult = Assert.IsType<OkObjectResult>(result);
    //     Assert.Equal("testToken", okResult.Value);
    // }
}