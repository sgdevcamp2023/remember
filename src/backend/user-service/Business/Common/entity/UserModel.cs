
namespace user_service
{
    namespace common
    {
        public class UserModel
        {
            public UserModel(int Id, string Email, string Password, string Name, bool IsDeleted, DateTime CreatedAt, DateTime UpdatedAt)
            {
                this.Id = Id;
                this.Email = Email;
                this.Password = Password;
                this.Name = Name;
                this.IsDeleted = IsDeleted;
                this.CreatedAt = CreatedAt;
                this.UpdatedAt = UpdatedAt;
            }

            public int Id { get; set; }
            public string Email { get; set; } = null!;
            public string Password { get; set; } = null!;
            public string Name { get; set; } = null!;
            public bool IsDeleted { get; set; }
            public DateTime CreatedAt { get; set; }
            public DateTime UpdatedAt { get; set; }
        }
    }
}