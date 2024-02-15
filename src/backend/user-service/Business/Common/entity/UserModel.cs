
using System.Data;

namespace user_service
{
    namespace common
    {
        public class UserModel
        {
            public UserModel(long Id, string Email, string Password, string Name, string? Profile, DateTime CreatedAt, DateTime UpdatedAt, bool IsDeleted = false)
            {
                this.Id = Id;
                this.Email = Email;
                this.Password = Password;
                this.Name = Name;
                this.CreatedAt = CreatedAt;
                this.UpdatedAt = UpdatedAt;
                this.Profile = Profile;
                this.IsDeleted = IsDeleted;
            }
            
            public long Id { get; set; }
            public string Email { get; set; } = null!;
            public string Password { get; set; } = null!;
            public string Name { get; set; } = null!;
            public DateTime CreatedAt { get; set; }
            public DateTime UpdatedAt { get; set; }
            public string? Profile { get; set; }
            public bool IsDeleted { get; set; }
        }
    }
}