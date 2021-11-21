package siru.springwebflux;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@SpringBootApplication
public class SpringWebfluxApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringWebfluxApplication.class, args);
    }

    @RestController
    public static class TestController {

        private List<String> userList = Arrays.asList("kim", "choi", "lee");

        @GetMapping("/test")
        public String test() throws InterruptedException {
            Thread.sleep((long) (Math.random() * 1000));
            return "test is tasty";
        }

        @GetMapping("/users")
        public ResponseEntity<List<Integer>> getUsers() throws InterruptedException {
            Thread.sleep((long) (Math.random() * 1000));
            return ResponseEntity.ok(
                    IntStream.range(0, userList.size()).boxed().collect(Collectors.toList())
            );
        }

        @GetMapping("/users/{userId}")
        public ResponseEntity<String> getUser(@PathVariable int userId) throws InterruptedException {
            Thread.sleep((long) (Math.random() * 1000));
            if(userId < 0 || userId >= userList.size()) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(userList.get(userId));
        }
    }
}
