package home.project;


import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

@Configuration
@EnableWebSecurity
public class OpenApiConfig {


    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(sessionManagement ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers("/swagger-ui/**" ).permitAll()
                        .requestMatchers("/api/member/FindByEmail", "/api/loginToken/login").permitAll()
                        .anyRequest().authenticated())
                .formLogin(formLogin -> formLogin
                        .loginPage("/login")
                        .permitAll());

        return http.build();
    }

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("예제 게시판 Swagger")
                .pathsToMatch("/swagger-ui/**")  // Swagger UI 경로 변경
                .build();
    }
}

//    @Bean
//    public WebSecurityCustomizer webSecurityCustomizer() {
//        return (web) -> web.ignoring().requestMatchers("/예외처리하고 싶은 url", "/예외처리하고 싶은 url");
//    }
//
//    protected SecurityFilterChain configure(HttpSecurity http) throws Exception {
//       return http
//                .authorizeRequests()
//                .requestMatchers("/swagger-ui/**").permitAll() // Swagger UI 경로에 대한 인증 무시
//                .anyRequest().authenticated() // 다른 모든 요청은 인증 필요
//                .and()
//                .formLogin()
//                .loginPage("/로그인페이지")
//                .loginProcessingUrl("/실제 로그인이 되는 url")
//                .permitAll()
//                .successHandler(로그인 성공 시 실행할 커스터마이즈드 핸들러)
//                .failureHandler(로그인 실패 시 실행할 커스터마이즈드 핸들러);
//
//        http
//                .sessionManagement()
//                .invalidSessionUrl("/로그인페이지")
//
//                .and()
//                .logout()
//                .logoutRequestMatcher(new AntPathRequestMatcher("/실제 로그아웃이 되는 url"))
//                .invalidateHttpSession(true)
//                .deleteCookies("JSESSIONID")
//                .permitAll();
//
//
//        //CSRF 토큰
//        http.csrf().disable();
//
//        return http.build();
////                .and()
////                .formLogin().loginPage("/login").permitAll(); // 기본 로그인 페이지 설정
//        return http.build();
//    }
//    @Bean
//    public GroupedOpenApi publicApi() {
//        return GroupedOpenApi.builder()
//                .group("예제 게시판 Swagger")
//                .pathsToMatch("/api/**")
//                .build();
//    }
//
//    @Bean
//    public OpenAPI springShopOpenAPI() {
//        return new OpenAPI()
//                .info(new Info().title("예제 게시판 API")
//                        .description("예제 게시판 API 명세서입니다.")
//                        .version("v0.0.1"));
//    }
//    @Bean
//    public OpenAPI springDocOpenAPI() {
//        return new OpenAPI()
//                .info(new Info()
//                        .title("My API")
//                        .version("v1")
//                        .description("This is my API.")
//                );
//    }
//    @Bean
//    public SpringDocUiController springDocUiController() {
//        return new SpringDocUiController(springDocOpenAPI());
//    }