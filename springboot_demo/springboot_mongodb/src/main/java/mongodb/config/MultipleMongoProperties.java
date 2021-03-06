package mongodb.config;

import lombok.Data;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Author: Johnny
 * Date: 2017/9/15
 * Time: 23:07
 */
@Data
@ConfigurationProperties(prefix = "mongodb")
public class MultipleMongoProperties {

    private MongoProperties primary = new MongoProperties();

    private MongoProperties secondary = new MongoProperties();

}
