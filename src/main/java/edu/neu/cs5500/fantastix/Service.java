package edu.neu.cs5500.fantastix;

import tests.*;
import edu.neu.cs5500.fantastix.core.*;
import edu.neu.cs5500.fantastix.data.*;
import edu.neu.cs5500.fantastix.resources.*;
import io.dropwizard.Application;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.migrations.MigrationsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.federecio.dropwizard.swagger.SwaggerBundle;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Service extends Application<ServiceConfiguration> {
	private static final Logger LOGGER = LoggerFactory.getLogger(ServiceConfiguration.class);
	
    public static void main(String[] args) throws Exception {
        new Service().run(args);
    }

    @Override
    public String getName() {
        return "Fantastix";
    }

    private final HibernateBundle<ServiceConfiguration> hibernateBundle = new HibernateBundle<ServiceConfiguration>(User.class, Renter.class, Manager.class, Booking.class, Feedback.class, Property.class, PropertyImage.class) {
        @Override
        public DataSourceFactory getDataSourceFactory(ServiceConfiguration configuration) {
            return configuration.getDataSourceFactory();
        }
    };

    @Override
    public void initialize(Bootstrap<ServiceConfiguration> bootstrap) {
    	LOGGER.info("Initializing configuration");
        bootstrap.addBundle(new MigrationsBundle<ServiceConfiguration>() {
            @Override
            public DataSourceFactory getDataSourceFactory(ServiceConfiguration configuration) {
                return configuration.getDataSourceFactory();
            }
        });
        bootstrap.addBundle(hibernateBundle);
        bootstrap.addBundle(new SwaggerBundle<ServiceConfiguration>() {
            @Override
            protected SwaggerBundleConfiguration getSwaggerBundleConfiguration(ServiceConfiguration configuration) {
                return configuration.swaggerBundleConfiguration;
            }
        });
    }

    @Override
    public void run(ServiceConfiguration configuration, Environment environment) throws Exception {
    	LOGGER.info("Starting the App");
        final UserDAO userDAO = new UserDAO(hibernateBundle.getSessionFactory());
        final FeedbackDAO feedbackDAO = new FeedbackDAO(hibernateBundle.getSessionFactory());
        final BookingDAO bookingDAO = new BookingDAO(hibernateBundle.getSessionFactory());
        final PropertyDAO propertyDAO = new PropertyDAO(hibernateBundle.getSessionFactory());
        final PropertyImageDAO propertyImageDAO = new PropertyImageDAO(hibernateBundle.getSessionFactory());
        final ManagerDAO managerDAO = new ManagerDAO(hibernateBundle.getSessionFactory());
        final RenterDAO renterDAO = new RenterDAO(hibernateBundle.getSessionFactory());


        final UserResource userResource = new UserResource(userDAO);
        final FeedbackResource feedbackResource = new FeedbackResource(feedbackDAO);
        final BookingResource bookingResource = new BookingResource(bookingDAO);
        final PropertyResource propertyResource = new PropertyResource(propertyDAO, propertyImageDAO);
		final RenterResource renterResource = new RenterResource(renterDAO, bookingDAO, feedbackDAO);
        final ManagerResource managerResource = new ManagerResource(managerDAO, propertyDAO);


        environment.jersey().register(userResource);
        environment.jersey().register(feedbackResource);
        environment.jersey().register(bookingResource);
        environment.jersey().register(propertyResource);
    	environment.jersey().register(renterResource);
        environment.jersey().register(managerResource);

        /*

        LOGGER.info("Running the tests");
        final UserResourceTest urt = new UserResourceTest(userResource);
        urt.testCreate();
        urt.testFindOne();
        urt.testFindAll();
        urt.testUpdate();
        urt.testDelete();


        final PropertyResourceTest prt = new PropertyResourceTest(propertyResource);
        prt.testCreate();
        prt.testFindOne();
        prt.testFindAll();
        prt.testUpdate();
        prt.testDelete();

        final BookingResourceTest brt = new BookingResourceTest(bookingResource);
        brt.testCreate();
        brt.testFindOne();
        brt.testFindAll();
        brt.testUpdate();
        brt.testDelete();


        final FeedbackResourceTest frt = new FeedbackResourceTest(feedbackResource);
        frt.testCreate();
        frt.testFindOne();
        frt.testFindAll();
        frt.testUpdate();
        frt.testDelete();


        final PropertyImageResourceTest pirt = new PropertyImageResourceTest(propertyResource);
        pirt.testCreate();
        pirt.testFindOne();
        pirt.testFindAll();
        pirt.testUpdate();
        pirt.testDelete();
        */
        
    }
}
