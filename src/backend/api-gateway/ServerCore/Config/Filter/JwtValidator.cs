namespace ServerCore
{
    public class JwtValidator
    {
        public string ClusterName { get; set; } = null!;
        public string Url { get; set; } = null!;
        public string? HeaderName { get; set; } = "Authorization";
        public string? Prefix { get; set; } = "Bearer";
        public List<int> SuccessStatus { get; set; } = null!;
    }
}