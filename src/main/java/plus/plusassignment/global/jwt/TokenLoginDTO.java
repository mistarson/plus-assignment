package plus.plusassignment.global.jwt;

public record TokenLoginDTO(
        String accessToken,
        String refreshToken
) {
  public static TokenLoginDTO of(String accessToken, String refreshToken) {

    return new TokenLoginDTO(accessToken, refreshToken);
  }
}
