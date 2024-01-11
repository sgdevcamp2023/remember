using System.Security.Cryptography;
using System.Text;

namespace user_service
{
    namespace common
    {
        public static class Utils
        {
            private static string chars = "abcdefghijklmnopqrstuvwxyz";
            private static string nums = "0123456789";
            private static string specialChars = "!@#$%^&*()";
            
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

            public static string GenerateRandomPassword()
            {
                Random random = new Random();

                // 각각의 요구 사항을 만족하는 문자를 랜덤하게 선택합니다.
                char randomLowercaseLetter = chars[random.Next(chars.Length)];
                char randomDigit = nums[random.Next(nums.Length)];
                char randomSpecialChar = specialChars[random.Next(specialChars.Length)];

                // 나머지 문자를 랜덤하게 선택하여 문자열의 길이를 8에서 20 사이로 만듭니다.
                int remainingLength = random.Next(5, 17);
                string remainingChars = new string(Enumerable.Repeat(chars + nums + specialChars, remainingLength)
                  .Select(s => s[random.Next(s.Length)]).ToArray());

                // 선택한 문자들을 조합하여 최종 문자열을 생성합니다.
                string finalString = new string((randomLowercaseLetter.ToString() + randomDigit.ToString() + randomSpecialChar.ToString() + remainingChars)
                  .ToCharArray().OrderBy(x => random.Next()).ToArray());

                return finalString;
            }
        }
    }
}