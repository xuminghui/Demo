package com.example.springDemo;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("remote")
public class RemoteProfile extends BaseProfile {

}
