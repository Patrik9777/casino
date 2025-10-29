package com.casino;

// import org.springframework.boot.SpringApplication;
// import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main Application Class
 * Spring Boot Entry Point
 * 
 * @SpringBootApplication annotation tartalmazza:
 * - @Configuration
 * - @EnableAutoConfiguration
 * - @ComponentScan
 */
// @SpringBootApplication
public class CasinoApplication {

    public static void main(String[] args) {
        // Spring Boot indítás:
        // SpringApplication.run(CasinoApplication.class, args);
        
        // Jelenleg Swing UI indítás:
        javax.swing.SwingUtilities.invokeLater(() -> {
            // new com.casino.view.LoginView();
            System.out.println("Casino Application Started!");
            System.out.println("Spring Boot MVC struktúra elkészült!");
            System.out.println("Használd: mvn spring-boot:run");
        });
    }
}
