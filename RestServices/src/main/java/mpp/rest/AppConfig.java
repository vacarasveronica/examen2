package mpp.rest;


import mpp.persistance.ConfiguratieRepoInterface;
import mpp.persistance.JocRepoInterface;
import mpp.persistance.PozitieRepoInterface;
import mpp.persistance.UserRepoInterface;
import mpp.persistance.repository.ConfiguratieDbRepo;
import mpp.persistance.repository.JocDbRepo;
import mpp.persistance.repository.PozitieDbRepo;
import mpp.persistance.repository.UserDbRepo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Configuration
public class AppConfig {

    @Bean
    public Properties dbProperties() throws IOException {
        Properties props = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("application.properties")) {
            if (input == null) {
                throw new IOException("application.properties not found in classpath");
            }
            props.load(input);
        }
        return props;
    }

    @Bean
    public JocRepoInterface jocRepo(Properties dbProperties) {
        return new JocDbRepo(dbProperties);
    }

    @Bean
    public ConfiguratieDbRepo configuratieRepo(Properties dbProperties) {
        return new ConfiguratieDbRepo();
    }

}


