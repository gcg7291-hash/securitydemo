# 로그인 흐름

1. login.html 출력
2. id, pw 입력 후 제출
3. POST / login (security 내장)
4. CustomUserDetailService.loadUserByUsername 호출
5. DB에서 User 조회
6. CustomUserDetails로 감싸서 반환
7. Security가 반환된 값 <=> 입력한 pw 비교
8. 세션에 사용자 인증정보를 저장
9. 로그인 완료