package com.example.forrmbackend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;

import com.example.forrmbackend.repositories.ProcessRequestRepository;
import com.example.forrmbackend.repositories.RegisterUserRepository;

@SpringBootApplication
public class ForrmbackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(ForrmbackendApplication.class, args);
	}

	private final RegisterUserRepository registerUserRepository;

	private final ProcessRequestRepository processRequestRepository;

	@Autowired
	public ForrmbackendApplication(RegisterUserRepository registerUserRepository, ProcessRequestRepository processRequestRepository) {
		this.registerUserRepository = registerUserRepository;
		this.processRequestRepository = processRequestRepository;
	}

	// @Override
	// public void run(String... args) throws Exception {

	// 	if (registerUserRepository.findAll().isEmpty()) {

	// 		// userRepository.save(new User("Anurag", "Ghosh", 19, "BTech", "Mumbai", "India"));
	// 		// userRepository.save(new User("Harsh", "Jain", 20, "BTech", "Mumbai", "India"));
	// 		// userRepository.save(new User("Larry", "Page", 45, "MSc", "New York", "USA"));
	// 		// userRepository.save(new User("Tim", "Lee", 65, "Phd", "London", "UK"));
	// 		// userRepository.save(new User("Ryan", "Renolyds", 42, "MSc", "Los Angeles", "USA"));
	// 		// userRepository.save(new User("Ellen", "Page", 55, "BA", "Los Angeles", "USA"));
	// 		// userRepository.save(new User("Navin", "Ready", 39, "MTech", "Hyderabad", "India"));
	// 		// userRepository.save(new User("Larry", "Page", 45, "MSc", "New York", "USA"));
	// 		// userRepository.save(new User("Arvind", "Singh", 45, "MSc", "Pune", "India"));

	// 	}

	// }


}
