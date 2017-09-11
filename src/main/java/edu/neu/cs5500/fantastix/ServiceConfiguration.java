package edu.neu.cs5500.fantastix;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.db.DatabaseConfiguration;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class ServiceConfiguration extends Configuration {
    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceConfiguration.class);

    @JsonProperty("swagger")
    public SwaggerBundleConfiguration swaggerBundleConfiguration;

    @Valid
    @NotNull
    private DataSourceFactory database = new DataSourceFactory();

    @JsonProperty("database")
    public DataSourceFactory getDataSourceFactory() {
        LOGGER.info("Dropwizard dummy DB URL (will be overridden)=" + database.getUrl());
        DatabaseConfiguration databaseConfiguration = DatabaseConfig.create("postgres://inkvdhymdiiuou:6pjMuhKgxk48RYYwcT1MPAJpqd@ec2-54-225-112-119.compute-1.amazonaws.com:5432/d37dd1easku8v4");
        database = (DataSourceFactory) databaseConfiguration.getDataSourceFactory(null);
        LOGGER.info("Heroku DB URL=" + database.getUrl());
        return database;
    }

    @JsonProperty("database")
    public void setDataSourceFactory(DataSourceFactory dataSourceFactory) {
        this.database = dataSourceFactory;
    }
}
