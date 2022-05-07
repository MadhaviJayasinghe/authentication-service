package com.dexlk.authentication.authenticationservice.service;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.dexlk.authentication.authenticationservice.model.UserCredential;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private DynamoDBMapper mapper;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //Logic to get the user form the Database

        UserCredential userCredential = getUser(username);
        return new User(userCredential.getUsername(),userCredential.getPassword(),new ArrayList<>());
    }

    public void saveUser(UserCredential userCredential) {
        mapper.save(userCredential);
    }

    public UserCredential getUser(String username) {
        Map<String, AttributeValue> eav = new HashMap<String, AttributeValue>();
        eav.put(":val1", new AttributeValue().withS(username));

        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression ()
                .withFilterExpression("username = :val1").withExpressionAttributeValues(eav);

        List<UserCredential> userCredentials = mapper.scan(UserCredential.class, scanExpression);

        return userCredentials.get(0);
    }

    public List<UserCredential> getusernames() {
        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression ();

        List<UserCredential> userCredentials = mapper.scan(UserCredential.class, scanExpression);

        return userCredentials;
    }
}
