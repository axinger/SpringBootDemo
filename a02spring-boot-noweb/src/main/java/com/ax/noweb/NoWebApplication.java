package com.ax.noweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.oas.annotations.EnableOpenApi;

@EnableOpenApi
@SpringBootApplication
public class NoWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(NoWebApplication.class, args);
    }

}

//@SpringBootApplication
//public class NoWebApplication implements CommandLineRunner {
//
//    @Autowired
//    UserService userService;
//
//    @Override
//    public void run(String... args) throws Exception {
//        System.out.println(userService.sayHi());
//
//    }
//
//    public static void main(String[] args) {
//
//    	/*关闭打印logo*/
//		SpringApplication springApplication = new SpringApplication(NoWebApplication.class);
//		springApplication.setBannerMode(Banner.Mode.OFF);
//		springApplication.run(args);
//       /* SpringApplication.run(NoWebApplication.class, args);*/
//
//    }
//
//
///*	public static void main(String[] args) {
////		返回spring容器对象
//		ConfigurableApplicationContext context = SpringApplication.run(NoWebApplication.class, args);
//		UserService userService =	(UserService)context.getBean("userService");
//
//		System.out.println(userService.sayHi());
//	}*/
//
//}
