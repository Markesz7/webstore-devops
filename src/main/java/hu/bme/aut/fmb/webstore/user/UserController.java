package hu.bme.aut.fmb.webstore.user;

import hu.bme.aut.fmb.webstore.security.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.io.FileWriter;
import java.io.IOException;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtTokenUtil;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    public UserController(UserRepository userRepository,BCryptPasswordEncoder passwordEncoder, JwtUtil jwtTokenUtil,
                          UserDetailsServiceImpl userDetailsService,
                          AuthenticationManager authenticationManager){
        this.userRepository=userRepository;
        this.passwordEncoder=passwordEncoder;
        this.jwtTokenUtil=jwtTokenUtil;
        this.userDetailsService=userDetailsService;
        this.authenticationManager= authenticationManager;
    }

    @PostMapping("/authenticate")
    public ResponseEntity<Object> createJWTtoken(@RequestBody Map<String, String> login) throws Exception {
        System.out.println(login.get("password"));

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(login.get("username"), login.get("password")));
        } catch (BadCredentialsException e) {
            throw new Exception("Incorrect username or password", e);
        }

        final String jwt = jwtTokenUtil.generateToken(userDetailsService.loadUserByUsername(login.get("username")));

        Map<String, String> map = new HashMap<>();
        List<String> roles = userRepository.findById(login.get("username")).get().getRoles();
        map.put("jwt", jwt);
        StringBuilder rolesList = new StringBuilder();
        for(int i = 0; i < roles.size(); i++)
            rolesList.append(roles.get(i)).append(i == roles.size() - 1 ? "" : ",");
        map.put("roles", rolesList.toString());
        return ResponseEntity.ok(map);
    }

    @GetMapping("/list")
    @Secured(User.ROLE_ADMIN)
    public List<User> getAll() {
        logger.info("GET request for listing all users, there are " + userRepository.findAll().size() + " user(s) in the system.");
        return userRepository.findAll();
    }

    //Bárki elérheti
    @PostMapping("/register")
    public ResponseEntity<Object> registerUser(@RequestBody User user) {
        Optional<User> u = userRepository.findById(user.getUsername());
        if(u.isPresent()) {
            logger.info("POST request for registering a new user failed, because username \"" + user.getUsername() + "\" is already used.");
            return ResponseEntity.badRequest().build();
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        logger.info("POST request for registering a new user with the username \"" + user.getUsername() + "\".");
        return ResponseEntity.ok().build();
    }

    @PutMapping(path = "{username}")
    @Secured(User.ROLE_USER)
    public ResponseEntity<Object> updateUser(@PathVariable String username, @RequestBody User user, Principal principal) {
        Optional<User> u = userRepository.findById(username);
        if(u.isEmpty())
            return ResponseEntity.notFound().build();
        else if(!u.get().getUsername().equals(principal.getName()))
            return ResponseEntity.status(403).build();

        if(!passwordEncoder.matches(user.getPassword(), u.get().getPassword()))
        {
            user.setPassword(passwordEncoder.encode(user.getPassword()));

            userRepository.save(user);
            logger.info("PUT request for updating the password for username \"" + user.getUsername() + "\".");
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/changerole")
    @Secured(User.ROLE_ADMIN)
    public ResponseEntity<Object> updateUser(@RequestBody User user) {
        Optional<User> u = userRepository.findById(user.getUsername());
        if(u.isEmpty())
            return ResponseEntity.notFound().build();

        User changedUser = u.get();
        changedUser.setRoles(user.getRoles());
        userRepository.save(changedUser);
        logger.info("PUT request for updating the password for username \"" + user.getUsername() + "\".");
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(path = "{username}")
    @Secured(User.ROLE_USER)
    public ResponseEntity<Object> delete(@PathVariable String username, Principal principal) {
        Optional<User> u = userRepository.findById(username);

        if (u.isEmpty())
            return ResponseEntity.notFound().build();
        else if(!u.get().getUsername().equals(principal.getName()))
            return ResponseEntity.status(403).build();
        else {
            userRepository.deleteById(username);
            logger.info("DELETE request for deleting user with the username \"" + username + "\".");
            return ResponseEntity.ok().build();
        }
    }

    @Scheduled(cron = "0 0 0/1 * * *")
    public void exportToCsv( ) throws IOException {
        String filename="user.csv";
        FileWriter csvWriter = new FileWriter(filename);
        csvWriter.append("username");
        csvWriter.append(";");
        csvWriter.append("password");
        csvWriter.append(";");
        csvWriter.append("email");
        csvWriter.append(";");
        csvWriter.append("roles");
        csvWriter.append(";");
        csvWriter.append("enabled");
        csvWriter.append(";");
        csvWriter.append("\n");
        List<User> list = getAll();

        for (User u: list){
            csvWriter.append(u.getUsername());
            csvWriter.append(";");
            csvWriter.append(u.getPassword());
            csvWriter.append(";");
            csvWriter.append(u.getEmail());
            csvWriter.append(";");
            for(int i = 0; i < u.getRoles().size() - 1; i++)
                csvWriter.append(u.getRoles().get(i)).append(",");
            csvWriter.append(u.getRoles().get(u.getRoles().size() - 1));
            csvWriter.append(";");
            csvWriter.append(u.isEnabled() ? "True" : "False");
            csvWriter.append(";");
            csvWriter.append("\n");
        }
        csvWriter.flush();
        csvWriter.close();
        System.out.println("Exported users");
        logger.info("Backup save users");
    }
}