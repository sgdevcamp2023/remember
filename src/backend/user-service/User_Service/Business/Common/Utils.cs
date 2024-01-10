using System.Security.Cryptography;
using System.Text;

namespace user_service
{
    namespace common
    {
        public static class Utils
        {

            public static string SHA256Hash(string password)
            {
                SHA256 sha256Hash = SHA256.Create();
                byte[] data = sha256Hash.ComputeHash(Encoding.UTF8.GetBytes(password));
                StringBuilder sBuilder = new StringBuilder();
                for (int i = 0; i < data.Length; i++)
                {
                    sBuilder.Append(data[i].ToString("x2"));
                }
                return sBuilder.ToString();
            }

            public static int GenerateRandomNumber(int min, int max)
            {
                Random random = new Random();
                return random.Next(min, max);
            }
        }
    }
}