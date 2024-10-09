package com.jpmc.midascore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class MidasCoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(MidasCoreApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    // Method to post to the incentive API
    public double postTransactionToIncentive(String recipientId, double amount) {
        String url = "http://localhost:8080/incentive";
        
        // Create a map to represent the transaction
        Map<String, Object> transaction = new HashMap<>();
        transaction.put("recipientId", recipientId);
        transaction.put("amount", amount);
        
        // Send the transaction and get the incentive response
        @SuppressWarnings("unchecked")
        Map<String, Object> incentiveResponse = restTemplate().postForObject(url, transaction, Map.class);
        
        // Assuming the response has a field "amount"
        return incentiveResponse != null ? (double) incentiveResponse.get("amount") : 0.0;
    }

    // Process the transaction
    public void processTransaction(String recipientId, double amount) {
        if (isTransactionValid(recipientId, amount)) {
            double incentiveAmount = postTransactionToIncentive(recipientId, amount);
            // Update user balances (you'll need to implement this method)
            updateUserBalances(recipientId, amount, incentiveAmount);
        }
    }

    private boolean isTransactionValid(String recipientId, double amount) {
        // Implement your validation logic
        return true; // Placeholder
    }

    private void updateUserBalances(String recipientId, double transactionAmount, double incentiveAmount) {
        // Logic to update user balances
        // For example, add incentiveAmount to recipient's balance
    }
}
